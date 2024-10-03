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

@Setter
@Getter
@Entity
@Table(name = Event.TABLE_NAME)
public class Event {

    public static final String TABLE_NAME = "event";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "match_id")
    private String matchId;

    @Column(name = "market_id")
    private String marketId;

    @Column(name = "outcome_id")
    private String outcomeId;

    @Column(name = "specifiers")
    private String specifiers;

    @Column(name = "date_insert", columnDefinition = "TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateInsert;

}
