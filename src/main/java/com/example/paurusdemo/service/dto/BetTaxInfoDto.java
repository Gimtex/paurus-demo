package com.example.paurusdemo.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BetTaxInfoDto implements Serializable {

    @JsonProperty
    private Double possibleReturnAmount;

    @JsonProperty
    private Double possibleReturnAmountBefTax;

    @JsonProperty
    private Double possibleReturnAmountAfterTax;

    @JsonProperty
    private Double taxRate;

    @JsonProperty
    private Double taxAmount;

}
