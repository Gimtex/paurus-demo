package com.example.paurusdemo.task2;

import com.example.paurusdemo.repository.EventRepository;
import com.example.paurusdemo.service.event.EventService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class Task2Test {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    @BeforeEach
    void setup() {
        eventRepository.deleteAll();
    }

    @AfterEach
    void clear() {
        eventRepository.deleteAll();
    }

    @Test
    void insertDataSet() throws Exception {
        Duration insertTime = eventService.processFileFromPath();
        log.info("Time between first and last insertion is: {}:{}:{}.{}",
                 insertTime.toHours(),
                 insertTime.toMinutes(),
                 insertTime.toSeconds(),
                 insertTime.toMillis()
        );
    }

}
