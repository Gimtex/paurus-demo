package com.example.paurusdemo.service.tax;

import com.example.paurusdemo.rest.TaxType;
import com.example.paurusdemo.service.dto.BetDto;
import com.example.paurusdemo.service.dto.BetTaxInfoDto;
import com.example.paurusdemo.service.dto.TraderDto;

public interface TaxService {

    BetTaxInfoDto calculate(BetDto betDto, TaxType taxType);

    Double calculatePossibleReturnAmountBeforeTax(Double playedAmount, Double odd);

    Double calculatePossibleReturnAmountAfterTax(Double possibleReturnAmountBeforeTax, Double taxAmount);

    Double calculateTaxOfPossibleReturnAmount(Double possibleReturnAmountBeforeTax, TaxType taxType, TraderDto traderDto);

}
