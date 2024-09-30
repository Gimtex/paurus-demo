package com.example.paurusdemo.repository.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = Trader.TABLE_NAME)
public class Trader {

    public static final String TABLE_NAME = "trader";

    @Id
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @Column(name = "tax_rate")
    private Double taxRate;

    @Getter
    @Setter
    @Column(name = "fixed_general_tax")
    private Double fixedGeneralTax;

    @Getter
    @Setter
    @Column(name = "fixed_winningsl_tax")
    private Double fixedWinningsTax;

}
