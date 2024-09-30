package com.example.paurusdemo.service.tax;

import com.example.paurusdemo.rest.TaxType;
import com.example.paurusdemo.service.dto.TraderDto;
import com.example.paurusdemo.service.trader.TraderService;
import org.springframework.stereotype.Service;

/**
 * WinningsTaxService is a concrete implementation of the AbstractTaxService
 * responsible for calculating the winnings tax on betting returns.
 * This service uses the TraderService to access trader-specific tax information
 * and calculates the possible return amounts and tax based on different tax types.
 */
@Service
public class WinningsTaxService extends AbstractTaxService {

    public WinningsTaxService(TraderService traderService) {
        super(traderService);
    }

    /**
     * Calculates the possible return amount before tax by adjusting the
     * calculated amount from the superclass. Specifically, this method
     * subtracts the original played amount from the calculated possible return
     * amount, effectively returning the net winnings before tax.
     *
     * @param playedAmount the amount that was played in the bet
     * @param odd the odds associated with the bet
     * @return the calculated possible return amount before tax, adjusted to reflect
     *         net winnings
     */
    @Override
    public Double calculatePossibleReturnAmountBeforeTax(Double playedAmount, Double odd) {
        return super.calculatePossibleReturnAmountBeforeTax(playedAmount, odd) - playedAmount;
    }

    /**
     * Calculates the tax of the possible return amount based on the specified tax type.
     *
     * If the tax type is FIXED, it retrieves the fixed winnings tax from the trader's
     * information. If the tax type is RATE, it calculates the tax by multiplying the
     * possible return amount before tax by the trader's tax rate.
     *
     * @param possibleReturnAmountBeforeTax the potential return amount before tax
     * @param taxType the type of tax to be applied (FIXED or RATE)
     * @param traderDto the trader information needed for tax calculations
     * @return the calculated tax amount based on the given parameters
     */
    @Override
    public Double calculateTaxOfPossibleReturnAmount(Double possibleReturnAmountBeforeTax, TaxType taxType, TraderDto traderDto) {
        if (taxType == TaxType.FIXED) {
            return traderDto.getFixedWinningsTax();
        }

        return possibleReturnAmountBeforeTax * traderDto.getTaxRate();
    }

}
