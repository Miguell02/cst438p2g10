package org.example.dailytier.repository;

import org.example.dailytier.model.Sport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SportRepository extends JpaRepository<Sport, Long> {
    Sport findBySportName(String sportName);
}