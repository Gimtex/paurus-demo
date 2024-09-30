package com.example.paurusdemo.rest;

import com.example.paurusdemo.service.dto.TraderDto;
import com.example.paurusdemo.service.trader.TraderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/v1/trader")
public class TraderController {

    private final TraderService traderService;

    public TraderController(TraderService traderService) {
        this.traderService = traderService;
    }


    @GetMapping("/{traderId}")
    @Operation(
            operationId = "GetTrader",
            tags = {"Trader"},
            summary = "Get a trader by ID.",
            description = "Fetches the details of a trader identified by the given trader ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved the trader.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TraderDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Trader not found with the provided ID."
                    )
            }
    )
    public ResponseEntity<TraderDto> findById(@Parameter(description = "Trader ID.") @PathVariable("traderId") Long traderId) {
        TraderDto traderDto = traderService.findById(traderId);

        if (traderDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(traderDto);
    }


    @PutMapping
    @Operation(
            operationId = "storeTrader",
            tags = {"Trader"},
            summary = "Create or update a trader.",
            description = "Registers a new trader or updates an existing trader's details.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully created or updated the trader.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TraderDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request - Invalid trader data provided."
                    )
            }
    )
    public ResponseEntity<TraderDto> store(@Parameter(description = "Trader definition.") @RequestBody TraderDto traderDto) {
        return ResponseEntity.ok(traderService.store(traderDto));
    }

    @DeleteMapping("/{traderId}")
    @Operation(
            operationId = "DeleteTrader",
            tags = {"Trader"},
            summary = "Delete a trader.",
            description = "Deletes the trader identified by the given trader ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully deleted the trader.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TraderDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Trader not found with the provided ID."
                    )
            }
    )
    public ResponseEntity<TraderDto> delete(@Parameter(description = "Trader ID.") @PathVariable("traderId") Long traderId) {
        TraderDto traderDto = traderService.delete(traderId);

        if (traderDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(traderDto);
    }
}