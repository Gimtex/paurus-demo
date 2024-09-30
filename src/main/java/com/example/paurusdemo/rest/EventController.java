package com.example.paurusdemo.rest;

import com.example.paurusdemo.service.event.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.Duration;

@RestController
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }


    @GetMapping("/trigger")
    @Operation(
            operationId = "triggerInsert",
            tags = {"Event"},
            summary = "Trigger data_set database insert.",
            description = "Initiates the insertion of dataset into the database and returns the time taken for the operation.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully triggered data insert and returned time taken."
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error occurred while processing the file."
                    )
            }
    )
    public ResponseEntity<String> trigger() {
        try {
            eventService.clearDatabaseBeforeInsertion();
            Duration insertTime = eventService.processFileFromPath();
            String response = insertTime.toHours() + ":" + insertTime.toMinutes() + ":" + insertTime.toSeconds() + ":" + insertTime.toMillis();
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing file: " + e.getMessage());
        }
    }
}