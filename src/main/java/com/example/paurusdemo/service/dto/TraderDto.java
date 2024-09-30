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
public class TraderDto implements Serializable {

    @JsonProperty
    private Long id;

    @JsonProperty
    private Double taxRate;

    @JsonProperty
    private Double fixedGeneralTax;

    @JsonProperty
    private Double fixedWinningsTax;

}
