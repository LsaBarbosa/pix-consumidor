package com.santanna.pixconsumidor.repository;

import com.santanna.pixconsumidor.domain.model.Key;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyRepository extends JpaRepository<Key, Integer> {
    Key findByChave(String key);
}
