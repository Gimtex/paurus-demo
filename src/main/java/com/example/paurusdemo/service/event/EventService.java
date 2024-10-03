package com.example.paurusdemo.service.event;

import com.example.paurusdemo.repository.EventRepository;
import com.example.paurusdemo.repository.dao.Event;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class EventService {

    private static final int BATCH_SIZE = 2000;

    private final EventRepository eventRepository;
    private final DataSource dataSource;

    public EventService(EventRepository eventRepository, DataSource dataSource) {
        this.eventRepository = eventRepository;
        this.dataSource = dataSource;
    }

    public void clearDatabaseBeforeInsertion() {
        eventRepository.deleteAll();
    }

    @Transactional
    public Duration processFileFromPath() throws IOException {
        final String filePath = System.getProperty("user.dir") + "/src/main/resources/fo_random.txt";

        List<Event> events = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            bufferedReader.readLine(); // Skip the header line

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Event event = getEvent(line);
                events.add(event);
            }
        }

        events.sort(Comparator.comparing(Event::getMatchId)
                            .thenComparing(Event::getMarketId)
                            .thenComparing(Event::getOutcomeId)
                            .thenComparing(Event::getSpecifiers)
        );

        if (!events.isEmpty()) {
            flushBuffer(events);
        }

        return getTimeDifferenceBetweenFirstAndLastInsert();
    }

    private static Event getEvent(String line) {
        String[] column = line.split("\\|(?=(?:[^']*'[^']*')*[^']*$)");
        String matchId = column[0].replace("'", "");
        String marketId = column[1].replace("'", "");
        String outcomeId = column[2].replace("'", "");
        String specifiers = column.length > 3 ? column[3].replace("'", "") : "";

        Event event = new Event();
        event.setMatchId(matchId);
        event.setMarketId(marketId);
        event.setOutcomeId(outcomeId);
        event.setSpecifiers(specifiers);
        return event;
    }

    protected void flushBuffer(List<Event> events) {
        String sql = "INSERT INTO event (match_id, market_id, outcome_id, specifiers) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                int count = 0;

                for (Event event : events) {
                    statement.setString(1, event.getMatchId());
                    statement.setString(2, event.getMarketId());
                    statement.setString(3, event.getOutcomeId());
                    statement.setString(4, event.getSpecifiers());
                    statement.addBatch();

                    if (++count % BATCH_SIZE == 0) {
                        statement.executeBatch();
                    }
                }

                statement.executeBatch();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error executing batch insert", e);
        }

        events.clear();
    }

    private Duration getTimeDifferenceBetweenFirstAndLastInsert() {
        List<Object[]> result = eventRepository.findFirstAndLastDateInsert();
        Object[] row = result.getFirst();
        LocalDateTime firstDate = ((Instant)row[0]).atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime lastDate = ((Instant)row[1]).atZone(ZoneId.systemDefault()).toLocalDateTime();
        return Duration.between(firstDate, lastDate);
    }
}