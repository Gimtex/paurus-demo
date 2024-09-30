package com.example.paurusdemo.rest;

import com.example.paurusdemo.service.dto.BetDto;
import com.example.paurusdemo.service.dto.BetTaxInfoDto;
import com.example.paurusdemo.service.tax.GeneralTaxService;
import com.example.paurusdemo.service.tax.WinningsTaxService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/v1/tax")
public class TaxController {
    public static final String TAX_TYPE = "taxType";

    private final GeneralTaxService generalTaxService;
    private final WinningsTaxService winningsTaxService;

    public TaxController(GeneralTaxService generalTaxService, WinningsTaxService winningsTaxService) {
        this.generalTaxService = generalTaxService;
        this.winningsTaxService = winningsTaxService;
    }


    @PostMapping("/general")
    @Operation(
            operationId = "calculateGeneralTax",
            tags = {"Tax"},
            summary = "Calculate general tax.",
            description = "Calculates the general tax for a given bet for a specific trader based on the provided bet information and tax type.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully calculated general tax information returned in the response body.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = BetTaxInfoDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request - Invalid input data."
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error - An error occurred while processing the request."
                    )
            }
    )
    public ResponseEntity<BetTaxInfoDto> calculateGeneralTax(
            @Parameter(description = "Bet information including amount and odds.") @RequestBody BetDto betDto,
            @Parameter(description = "Type of tax to apply to the possible return amount. Default: RATE.")
            @RequestParam(name = TAX_TYPE, defaultValue = "RATE") TaxType taxType
    ) {
        return ResponseEntity.ok(generalTaxService.calculate(betDto, taxType));
    }

    @PostMapping("/winnings")
    @Operation(
            operationId = "calculateWinningsTax",
            tags = {"Tax"},
            summary = "Calculate winnings tax.",
            description = "Calculates the winnings tax for a given bet for a specific trader based on the provided bet information and tax type.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully calculated winnings tax information returned in the response body.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = BetTaxInfoDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request - Invalid input data."
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error - An error occurred while processing the request."
                    )
            }
    )
    public ResponseEntity<BetTaxInfoDto> calculateWinningsTax(
            @Parameter(description = "Bet information including amount and odds.") @RequestBody BetDto betDto,
            @Parameter(description = "Type of tax to apply to the possible winnings. Default: RATE.")
            @RequestParam(name = TAX_TYPE, defaultValue = "RATE") TaxType taxType
    ) {
        return ResponseEntity.ok(winningsTaxService.calculate(betDto, taxType));
    }

}
