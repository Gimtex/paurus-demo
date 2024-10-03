package com.example.paurusdemo.repository.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = Event.TABLE_NAME)
public class Event {

    public static final String TABLE_NAME = "event";

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name = "match_id")
    private String matchId;

    @Getter
    @Setter
    @Column(name = "market_id")
    private String marketId;

    @Getter
    @Setter
    @Column(name = "outcome_id")
    private String outcomeId;

    @Getter
    @Setter
    @Column(name = "specifiers")
    private String specifiers;

    @Getter
    @Setter
    @Column(name = "date_insert", columnDefinition = "TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateInsert;

}
