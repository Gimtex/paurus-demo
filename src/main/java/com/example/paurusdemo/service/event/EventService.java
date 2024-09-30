package com.example.paurusdemo.service.event;

import com.example.paurusdemo.repository.EventRepository;
import com.example.paurusdemo.repository.dao.Event;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    private static final int BATCH_SIZE = 2000;
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void clearDatabaseBeforeInsertion() {
        eventRepository.deleteAll();
    }

    public Duration processFileFromPath() throws IOException {
        final String filePath = System.getProperty("user.dir") + "/src/main/resources/fo_random.txt";

        List<Event> events = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            bufferedReader.readLine(); // Skip the header line

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Event event = getEvent(line);
                events.add(event);

                if (events.size() >= BATCH_SIZE) {
                    flushBuffer(events);
                }
            }
        }

        if (!events.isEmpty()) {
            flushBuffer(events);
        }

        return getTimeDifferenceBetweenFirstAndLastInsert();
    }

    private static Event getEvent(String line) {
        String[] column = line.split("\\|(?=(?:[^']*'[^']*')*[^']*$)");
        ;
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

    private void flushBuffer(List<Event> events) {
        eventRepository.saveAll(events);
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