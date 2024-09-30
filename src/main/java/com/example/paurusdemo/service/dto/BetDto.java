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
public class BetDto implements Serializable {

    @JsonProperty
    private Long traderId;

    @JsonProperty
    private Double playedAmount;

    @JsonProperty
    private Double odd;
}
