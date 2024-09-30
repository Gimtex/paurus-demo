package com.example.paurusdemo.service.tax;

import com.example.paurusdemo.rest.TaxType;
import com.example.paurusdemo.service.trader.TraderService;
import com.example.paurusdemo.service.dto.BetDto;
import com.example.paurusdemo.service.dto.BetTaxInfoDto;
import com.example.paurusdemo.service.dto.TraderDto;

/**
 * AbstractTaxService serves as a base class for calculating tax-related information
 * for betting transactions. This class implements the TaxService interface and
 * provides common functionality needed to calculate potential returns, tax amounts,
 * and other relevant financial data for bets based on different tax types.
 */
abstract class AbstractTaxService implements TaxService {

    private final TraderService traderService;

    public AbstractTaxService(TraderService traderService) {
        this.traderService = traderService;
    }

    /**
     * Calculates the tax information related to a bet.
     *
     * @param betDto the BetDto containing information about the bet
     * @param taxType the type of tax to be applied (RATE or FIXED)
     * @return BetTaxInfoDto containing the calculated tax amount, tax rate, and potential returns
     */
    public BetTaxInfoDto calculate(BetDto betDto, TaxType taxType) {

        final TraderDto traderDto = traderService.findById(betDto.getTraderId());
        final Double possibleReturnAmountBeforeTax = calculatePossibleReturnAmountBeforeTax(betDto.getPlayedAmount(), betDto.getOdd());
        final Double taxAmount = calculateTaxOfPossibleReturnAmount(possibleReturnAmountBeforeTax, taxType, traderDto);
        final Double possibleReturnAmountAfterTax = calculatePossibleReturnAmountAfterTax(possibleReturnAmountBeforeTax, taxAmount);

        final BetTaxInfoDto betTaxInfoDto = new BetTaxInfoDto();
        betTaxInfoDto.setTaxAmount(taxAmount);
        betTaxInfoDto.setTaxRate(taxType == TaxType.RATE ? traderDto.getTaxRate() : null);
        betTaxInfoDto.setPossibleReturnAmountBefTax(possibleReturnAmountBeforeTax);
        betTaxInfoDto.setPossibleReturnAmount(possibleReturnAmountAfterTax);

        betTaxInfoDto.setPossibleReturnAmountAfterTax(possibleReturnAmountAfterTax);

        return betTaxInfoDto;
    }

    /**
     * Calculates the possible return amount before tax based on the played amount and odds.
     *
     * @param playedAmount the amount of money wagered in the bet
     * @param odd the odds associated with the bet
     * @return the potential return amount before tax
     */
    public Double calculatePossibleReturnAmountBeforeTax(Double playedAmount, Double odd) {
        return playedAmount * odd;
    }

    /**
     * Calculates the possible return amount after tax has been deducted.
     *
     * @param possibleReturnAmountBeforeTax the potential return amount before tax
     * @param taxAmount the amount of tax to be deducted
     * @return the potential return amount after tax
     */
    public Double calculatePossibleReturnAmountAfterTax(Double possibleReturnAmountBeforeTax, Double taxAmount) {
        return possibleReturnAmountBeforeTax - taxAmount;
    }

    /**
     * Abstract method for calculating tax of possible return amount.
     * This method must be implemented by subclasses to provide specific tax calculation logic.
     *
     * @param possibleReturnAmountBeforeTax the potential return amount before tax
     * @param taxType the type of tax to be applied (RATE or FIXED)
     * @param traderDto the trader information needed for tax calculations
     * @return the calculated tax amount based on the given parameters
     */
    public Double calculateTaxOfPossibleReturnAmount(Double possibleReturnAmountBeforeTax, TaxType taxType, TraderDto traderDto) {
        return null;
    }
}