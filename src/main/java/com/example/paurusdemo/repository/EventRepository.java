package com.example.paurusdemo.repository;

import com.example.paurusdemo.repository.dao.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "SELECT MIN(date_insert) AS first_date, MAX(date_insert) AS last_date FROM event", nativeQuery = true)
    List<Object[]> findFirstAndLastDateInsert();
}
