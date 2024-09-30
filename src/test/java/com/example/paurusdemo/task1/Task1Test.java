package com.example.paurusdemo.task1;

import com.example.paurusdemo.rest.TaxType;
import com.example.paurusdemo.service.dto.BetDto;
import com.example.paurusdemo.service.dto.TraderDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class Task1Test {

    @Autowired
    private MockMvc mockMvc;

    private final TraderDto[] traders = new TraderDto[]{
            new TraderDto(1L, 0.10, 2.0, 1.0),
            new TraderDto(2L, 0.15, 3.0, 1.5),
            new TraderDto(3L, 0.20, 4.0, 2.0),
            new TraderDto(4L, 0.25, 5.0, 2.5),
            new TraderDto(5L, 0.30, 6.0, 3.0)
    };

    @BeforeEach
    void setup() throws Exception {
        for (TraderDto trader : traders) {
            mockMvc.perform(put("/rest/v1/trader")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(asJsonString(trader)))
                    .andExpect(status().isOk());
        }
    }

    @AfterEach
    void clear() throws Exception {
        for (TraderDto trader : traders) {
            mockMvc.perform(put("/rest/v1/trader")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(asJsonString(trader)))
                    .andExpect(status().isOk());
        }
    }

    @Test
    void testStoreTraders() throws Exception {
        for (TraderDto trader : traders) {
            mockMvc.perform(get("/rest/v1/trader/" + trader.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(trader.getId().intValue())))
                    .andExpect(jsonPath("$.taxRate", is(trader.getTaxRate())))
                    .andExpect(jsonPath("$.fixedGeneralTax", is(trader.getFixedGeneralTax())))
                    .andExpect(jsonPath("$.fixedWinningsTax", is(trader.getFixedWinningsTax())));
        }
    }

    @Test
    void testWinningsTaxRate_fixedBet() throws Exception {
        // Arrange
        BetDto betDto1 = new BetDto(traders[0].getId(), 5.0, 1.5);
        // Act - Assert
        mockMvc.perform(post("/rest/v1/tax/winnings")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("taxType", TaxType.RATE.name())
                                .content(asJsonString(betDto1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.possibleReturnAmount", is(2.25)))
                .andExpect(jsonPath("$.possibleReturnAmountBefTax", is(2.5)))
                .andExpect(jsonPath("$.possibleReturnAmountAfterTax", is(2.25)))
                .andExpect(jsonPath("$.taxRate", is(0.1)))
                .andExpect(jsonPath("$.taxAmount", is(0.25)));


        // Arrange
        BetDto betDto2 = new BetDto(traders[1].getId(), 5.0, 1.5);
        // Act - Assert
        mockMvc.perform(post("/rest/v1/tax/winnings")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("taxType", TaxType.RATE.name())
                                .content(asJsonString(betDto2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.possibleReturnAmount", is(2.125)))
                .andExpect(jsonPath("$.possibleReturnAmountBefTax", is(2.5)))
                .andExpect(jsonPath("$.possibleReturnAmountAfterTax", is(2.125)))
                .andExpect(jsonPath("$.taxRate", is(0.15)))
                .andExpect(jsonPath("$.taxAmount", is(0.375)));


        // Arrange
        BetDto betDto3 = new BetDto(traders[2].getId(), 5.0, 1.5);
        // Act - Assert
        mockMvc.perform(post("/rest/v1/tax/winnings")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("taxType", TaxType.RATE.name())
                                .content(asJsonString(betDto3)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.possibleReturnAmount", is(2.0)))
                .andExpect(jsonPath("$.possibleReturnAmountBefTax", is(2.5)))
                .andExpect(jsonPath("$.possibleReturnAmountAfterTax", is(2.0)))
                .andExpect(jsonPath("$.taxRate", is(0.20)))
                .andExpect(jsonPath("$.taxAmount", is(0.5)));


        // Arrange
        BetDto betDto4 = new BetDto(traders[3].getId(), 5.0, 1.5);
        // Act - Assert
        mockMvc.perform(post("/rest/v1/tax/winnings")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("taxType", TaxType.RATE.name())
                                .content(asJsonString(betDto4)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.possibleReturnAmount", is(1.875)))
                .andExpect(jsonPath("$.possibleReturnAmountBefTax", is(2.5)))
                .andExpect(jsonPath("$.possibleReturnAmountAfterTax", is(1.875)))
                .andExpect(jsonPath("$.taxRate", is(0.25)))
                .andExpect(jsonPath("$.taxAmount", is(0.625)));


        // Arrange
        BetDto betDto5 = new BetDto(traders[4].getId(), 5.0, 1.5);
        // Act - Assert
        mockMvc.perform(post("/rest/v1/tax/winnings")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("taxType", TaxType.RATE.name())
                                .content(asJsonString(betDto5)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.possibleReturnAmount", is(1.75)))
                .andExpect(jsonPath("$.possibleReturnAmountBefTax", is(2.5)))
                .andExpect(jsonPath("$.possibleReturnAmountAfterTax", is(1.75)))
                .andExpect(jsonPath("$.taxRate", is(0.30)))
                .andExpect(jsonPath("$.taxAmount", is(0.75)));
    }

    @Test
    void testWinningsTaxRate_dynamicBet() throws Exception {
        // Arrange
        BetDto betDto1 = new BetDto(traders[0].getId(), 5.0, 1.5);
        // Act - Assert
        mockMvc.perform(post("/rest/v1/tax/winnings")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("taxType", TaxType.RATE.name())
                                .content(asJsonString(betDto1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.possibleReturnAmount", is(2.25)))
                .andExpect(jsonPath("$.possibleReturnAmountBefTax", is(2.5)))
                .andExpect(jsonPath("$.possibleReturnAmountAfterTax", is(2.25)))
                .andExpect(jsonPath("$.taxRate", is(0.1)))
                .andExpect(jsonPath("$.taxAmount", is(0.25)));


        // Arrange
        BetDto betDto2 = new BetDto(traders[1].getId(), 10.0, 2.0);
        // Act - Assert
        mockMvc.perform(post("/rest/v1/tax/winnings")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("taxType", TaxType.RATE.name())
                                .content(asJsonString(betDto2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.possibleReturnAmount", is(8.5)))
                .andExpect(jsonPath("$.possibleReturnAmountBefTax", is(10.0)))
                .andExpect(jsonPath("$.possibleReturnAmountAfterTax", is(8.5)))
                .andExpect(jsonPath("$.taxRate", is(0.15)))
                .andExpect(jsonPath("$.taxAmount", is(1.5)));

        // Arrange
        BetDto betDto3 = new BetDto(traders[2].getId(), 7.5, 2.5);
        // Act - Assert
        mockMvc.perform(post("/rest/v1/tax/winnings")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("taxType", TaxType.RATE.name())
                                .content(asJsonString(betDto3)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.possibleReturnAmount", is(9.0)))
                .andExpect(jsonPath("$.possibleReturnAmountBefTax", is(11.25)))
                .andExpect(jsonPath("$.possibleReturnAmountAfterTax", is(9.0)))
                .andExpect(jsonPath("$.taxRate", is(0.20)))
                .andExpect(jsonPath("$.taxAmount", is(2.25)));


        // Arrange
        BetDto betDto4 = new BetDto(traders[3].getId(), 15.0, 3.0);
        // Act - Assert
        mockMvc.perform(post("/rest/v1/tax/winnings")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("taxType", TaxType.RATE.name())
                                .content(asJsonString(betDto4)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.possibleReturnAmount", is(22.5)))
                .andExpect(jsonPath("$.possibleReturnAmountBefTax", is(30.0)))
                .andExpect(jsonPath("$.possibleReturnAmountAfterTax", is(22.5)))
                .andExpect(jsonPath("$.taxRate", is(0.25)))
                .andExpect(jsonPath("$.taxAmount", is(7.5)));


        // Arrange
        BetDto betDto5 = new BetDto(traders[4].getId(), 8.0, 1.8);
        // Act - Assert
        mockMvc.perform(post("/rest/v1/tax/winnings")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("taxType", TaxType.RATE.name())
                                .content(asJsonString(betDto5)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.possibleReturnAmount", is(4.48)))
                .andExpect(jsonPath("$.possibleReturnAmountBefTax", is(6.4)))
                .andExpect(jsonPath("$.possibleReturnAmountAfterTax", is(4.48)))
                .andExpect(jsonPath("$.taxRate", is(0.30)))
                .andExpect(jsonPath("$.taxAmount", is(1.92)));
    }

    @Test
    void testWinningsTaxFixed() throws Exception {
        // Arrange
        BetDto betDto1 = new BetDto(traders[0].getId(), 5.0, 1.5);
        // Act - Assert
        mockMvc.perform(post("/rest/v1/tax/winnings")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("taxType", TaxType.FIXED.name())
                                .content(asJsonString(betDto1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.possibleReturnAmount", is(1.5)))
                .andExpect(jsonPath("$.possibleReturnAmountBefTax", is(2.5)))
                .andExpect(jsonPath("$.possibleReturnAmountAfterTax", is(1.5)))
                .andExpect(jsonPath("$.taxAmount", is(1.0)));


        // Arrange
        BetDto betDto2 = new BetDto(traders[1].getId(), 10.0, 2.0);
        // Act - Assert
        mockMvc.perform(post("/rest/v1/tax/winnings")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("taxType", TaxType.FIXED.name())
                                .content(asJsonString(betDto2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.possibleReturnAmount", is(8.5)))
                .andExpect(jsonPath("$.possibleReturnAmountBefTax", is(10.0)))
                .andExpect(jsonPath("$.possibleReturnAmountAfterTax", is(8.5)))
                .andExpect(jsonPath("$.taxAmount", is(1.5)));

        // Arrange
        BetDto betDto3 = new BetDto(traders[2].getId(), 7.5, 2.5);
        // Act - Assert
        mockMvc.perform(post("/rest/v1/tax/winnings")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("taxType", TaxType.FIXED.name())
                                .content(asJsonString(betDto3)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.possibleReturnAmount", is(9.25)))
                .andExpect(jsonPath("$.possibleReturnAmountBefTax", is(11.25)))
                .andExpect(jsonPath("$.possibleReturnAmountAfterTax", is(9.25)))
                .andExpect(jsonPath("$.taxAmount", is(2.0)));


        // Arrange
        BetDto betDto4 = new BetDto(traders[3].getId(), 15.0, 3.0);
        // Act - Assert
        mockMvc.perform(post("/rest/v1/tax/winnings")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("taxType", TaxType.FIXED.name())
                                .content(asJsonString(betDto4)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.possibleReturnAmount", is(27.5)))
                .andExpect(jsonPath("$.possibleReturnAmountBefTax", is(30.0)))
                .andExpect(jsonPath("$.possibleReturnAmountAfterTax", is(27.5)))
                .andExpect(jsonPath("$.taxAmount", is(2.5)));


        // Arrange
        BetDto betDto5 = new BetDto(traders[4].getId(), 8.0, 1.8);
        // Act - Assert
        mockMvc.perform(post("/rest/v1/tax/winnings")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("taxType", TaxType.FIXED.name())
                                .content(asJsonString(betDto5)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.possibleReturnAmount", is(3.4000000000000004)))
                .andExpect(jsonPath("$.possibleReturnAmountBefTax", is(6.4)))
                .andExpect(jsonPath("$.possibleReturnAmountAfterTax", is(3.4000000000000004)))
                .andExpect(jsonPath("$.taxAmount", is(3.0)));
    }

    @Test
    void testGeneralTaxRate_fixedBet() throws Exception {
        // Arrange
        BetDto betDto1 = new BetDto(traders[0].getId(), 5.0, 1.5);
        // Act - Assert
        mockMvc.perform(post("/rest/v1/tax/general")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("taxType", TaxType.RATE.name())
                                .content(asJsonString(betDto1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.possibleReturnAmount", is(6.75)))
                .andExpect(jsonPath("$.possibleReturnAmountBefTax", is(7.5)))
                .andExpect(jsonPath("$.possibleReturnAmountAfterTax", is(6.75)))
                .andExpect(jsonPath("$.taxRate", is(0.1)))
                .andExpect(jsonPath("$.taxAmount", is(0.75)));

        // Arrange
        BetDto betDto2 = new BetDto(traders[1].getId(), 5.0, 1.5);
        // Act - Assert
        mockMvc.perform(post("/rest/v1/tax/general")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("taxType", TaxType.RATE.name())
                                .content(asJsonString(betDto2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.possibleReturnAmount", is(6.375)))
                .andExpect(jsonPath("$.possibleReturnAmountBefTax", is(7.5)))
                .andExpect(jsonPath("$.possibleReturnAmountAfterTax", is(6.375)))
                .andExpect(jsonPath("$.taxRate", is(0.15)))
                .andExpect(jsonPath("$.taxAmount", is(1.125)));


        // Arrange
        BetDto betDto3 = new BetDto(traders[2].getId(), 5.0, 1.5);
        // Act - Assert
        mockMvc.perform(post("/rest/v1/tax/general")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("taxType", TaxType.RATE.name())
                                .content(asJsonString(betDto3)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.possibleReturnAmount", is(6.0)))
                .andExpect(jsonPath("$.possibleReturnAmountBefTax", is(7.5)))
                .andExpect(jsonPath("$.possibleReturnAmountAfterTax", is(6.0)))
                .andExpect(jsonPath("$.taxRate", is(0.20)))
                .andExpect(jsonPath("$.taxAmount", is(1.5)));


        // Arrange
        BetDto betDto4 = new BetDto(traders[3].getId(), 5.0, 1.5);
        // Act - Assert
        mockMvc.perform(post("/rest/v1/tax/general")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("taxType", TaxType.RATE.name())
                                .content(asJsonString(betDto4)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.possibleReturnAmount", is(5.625)))
                .andExpect(jsonPath("$.possibleReturnAmountBefTax", is(7.5)))
                .andExpect(jsonPath("$.possibleReturnAmountAfterTax", is(5.625)))
                .andExpect(jsonPath("$.taxRate", is(0.25)))
                .andExpect(jsonPath("$.taxAmount", is(1.875)));


        // Arrange
        BetDto betDto5 = new BetDto(traders[4].getId(), 5.0, 1.5);
        // Act - Assert
        mockMvc.perform(post("/rest/v1/tax/general")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("taxType", TaxType.RATE.name())
                                .content(asJsonString(betDto5)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.possibleReturnAmount", is(5.25)))
                .andExpect(jsonPath("$.possibleReturnAmountBefTax", is(7.5)))
                .andExpect(jsonPath("$.possibleReturnAmountAfterTax", is(5.25)))
                .andExpect(jsonPath("$.taxRate", is(0.30)))
                .andExpect(jsonPath("$.taxAmount", is(2.25)));
    }

    @Test
    public void testGeneralTaxRate_dynamicBet() throws Exception {
        // Arrange
        BetDto betDto1 = new BetDto(traders[0].getId(), 5.0, 1.5);
        // Act - Assert
        mockMvc.perform(post("/rest/v1/tax/general")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("taxType", TaxType.RATE.name())
                                .content(asJsonString(betDto1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.possibleReturnAmount", is(6.75)))
                .andExpect(jsonPath("$.possibleReturnAmountBefTax", is(7.5)))
                .andExpect(jsonPath("$.possibleReturnAmountAfterTax", is(6.75)))
                .andExpect(jsonPath("$.taxRate", is(0.1)))
                .andExpect(jsonPath("$.taxAmount", is(0.75)));

        // Arrange
        BetDto betDto2 = new BetDto(traders[1].getId(), 10.0, 2.0);
        // Act - Assert
        mockMvc.perform(post("/rest/v1/tax/general")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("taxType", TaxType.RATE.name())
                                .content(asJsonString(betDto2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.possibleReturnAmount", is(17.0)))
                .andExpect(jsonPath("$.possibleReturnAmountBefTax", is(20.0)))
                .andExpect(jsonPath("$.possibleReturnAmountAfterTax", is(17.0)))
                .andExpect(jsonPath("$.taxRate", is(0.15)))
                .andExpect(jsonPath("$.taxAmount", is(3.0)));

        // Arrange
        BetDto betDto3 = new BetDto(traders[2].getId(), 7.5, 2.5);
        // Act - Assert
        mockMvc.perform(post("/rest/v1/tax/general")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("taxType", TaxType.RATE.name())
                                .content(asJsonString(betDto3)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.possibleReturnAmount", is(15.0)))
                .andExpect(jsonPath("$.possibleReturnAmountBefTax", is(18.75)))
                .andExpect(jsonPath("$.possibleReturnAmountAfterTax", is(15.0)))
                .andExpect(jsonPath("$.taxRate", is(0.20)))
                .andExpect(jsonPath("$.taxAmount", is(3.75)));

        // Arrange
        BetDto betDto4 = new BetDto(traders[3].getId(), 15.0, 3.0);
        // Act - Assert
        mockMvc.perform(post("/rest/v1/tax/general")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("taxType", TaxType.RATE.name())
                                .content(asJsonString(betDto4)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.possibleReturnAmount", is(33.75)))
                .andExpect(jsonPath("$.possibleReturnAmountBefTax", is(45.0)))
                .andExpect(jsonPath("$.possibleReturnAmountAfterTax", is(33.75)))
                .andExpect(jsonPath("$.taxRate", is(0.25)))
                .andExpect(jsonPath("$.taxAmount", is(11.25)));

        // Arrange
        BetDto betDto5 = new BetDto(traders[4].getId(), 8.0, 1.8);
        // Act - Assert
        mockMvc.perform(post("/rest/v1/tax/general")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("taxType", TaxType.RATE.name())
                                .content(asJsonString(betDto5)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.possibleReturnAmount", is(10.08)))
                .andExpect(jsonPath("$.possibleReturnAmountBefTax", is(14.4)))
                .andExpect(jsonPath("$.possibleReturnAmountAfterTax", is(10.08)))
                .andExpect(jsonPath("$.taxRate", is(0.30)))
                .andExpect(jsonPath("$.taxAmount", is(4.32)));
    }

    @Test
    public void testGeneralTaxFixed() throws Exception {
        // Arrange
        BetDto betDto1 = new BetDto(traders[0].getId(), 5.0, 1.5);
        // Act - Assert
        mockMvc.perform(post("/rest/v1/tax/general")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("taxType", TaxType.FIXED.name())
                                .content(asJsonString(betDto1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.possibleReturnAmount", is(5.5)))
                .andExpect(jsonPath("$.possibleReturnAmountBefTax", is(7.5)))
                .andExpect(jsonPath("$.possibleReturnAmountAfterTax", is(5.5)))
                .andExpect(jsonPath("$.taxAmount", is(2.0)));

        // Arrange
        BetDto betDto2 = new BetDto(traders[1].getId(), 10.0, 2.0);
        mockMvc.perform(post("/rest/v1/tax/general")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("taxType", TaxType.FIXED.name())
                                .content(asJsonString(betDto2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.possibleReturnAmount", is(17.0)))
                .andExpect(jsonPath("$.possibleReturnAmountBefTax", is(20.0)))
                .andExpect(jsonPath("$.possibleReturnAmountAfterTax", is(17.0)))
                .andExpect(jsonPath("$.taxAmount", is(3.0)));

        // Arrange 3
        BetDto betDto3 = new BetDto(traders[2].getId(), 7.5, 2.5);
        // Act - Assert
        mockMvc.perform(post("/rest/v1/tax/general")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("taxType", TaxType.FIXED.name())
                                .content(asJsonString(betDto3)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.possibleReturnAmount", is(14.75)))
                .andExpect(jsonPath("$.possibleReturnAmountBefTax", is(18.75)))
                .andExpect(jsonPath("$.possibleReturnAmountAfterTax", is(14.75)))
                .andExpect(jsonPath("$.taxAmount", is(4.0)));

        // Arrange
        BetDto betDto4 = new BetDto(traders[3].getId(), 15.0, 3.0);
        // Act - Assert
        mockMvc.perform(post("/rest/v1/tax/general")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("taxType", TaxType.FIXED.name())
                                .content(asJsonString(betDto4)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.possibleReturnAmount", is(40.0)))
                .andExpect(jsonPath("$.possibleReturnAmountBefTax", is(45.0)))
                .andExpect(jsonPath("$.possibleReturnAmountAfterTax", is(40.0)))
                .andExpect(jsonPath("$.taxAmount", is(5.0)));

        // Arrange
        BetDto betDto5 = new BetDto(traders[4].getId(), 8.0, 1.8);
        // Act - Assert
        mockMvc.perform(post("/rest/v1/tax/general")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("taxType", TaxType.FIXED.name())
                                .content(asJsonString(betDto5)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.possibleReturnAmount", is(8.4)))
                .andExpect(jsonPath("$.possibleReturnAmountBefTax", is(14.4)))
                .andExpect(jsonPath("$.possibleReturnAmountAfterTax", is(8.4)))
                .andExpect(jsonPath("$.taxAmount", is(6.0)));
    }


    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
