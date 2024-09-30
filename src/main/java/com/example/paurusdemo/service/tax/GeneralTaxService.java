package com.example.paurusdemo.service.tax;

import com.example.paurusdemo.rest.TaxType;
import com.example.paurusdemo.service.dto.TraderDto;
import com.example.paurusdemo.service.trader.TraderService;
import org.springframework.stereotype.Service;

/**
 * GeneralTaxService is a concrete implementation of the AbstractTaxService
 * responsible for calculating general tax on betting returns based on the tax type.
 * It uses the TraderService to access trader-specific tax information.
 */
@Service
public class GeneralTaxService extends AbstractTaxService {

    public GeneralTaxService(TraderService traderService) {
        super(traderService);
    }

    /**
     * Calculates the tax of the possible return amount based on the specified tax type.
     *
     * If the tax type is FIXED, it retrieves the fixed tax amount from the trader's information.
     * If the tax type is RATE, it calculates the tax by multiplying the possible return
     * amount before tax by the trader's tax rate.
     *
     * @param possibleReturnAmountBeforeTax the potential return amount before tax
     * @param taxType the type of tax to be applied (FIXED or RATE)
     * @param traderDto the trader information needed for tax calculations
     * @return the calculated tax amount based on the given parameters
     */
    @Override
    public Double calculateTaxOfPossibleReturnAmount(Double possibleReturnAmountBeforeTax, TaxType taxType, TraderDto traderDto) {
        if (taxType == TaxType.FIXED) {
            return traderDto.getFixedGeneralTax();
        }

        return possibleReturnAmountBeforeTax * traderDto.getTaxRate();
    }
}