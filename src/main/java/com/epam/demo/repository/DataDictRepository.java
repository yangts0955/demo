package com.epam.demo.repository;

import com.epam.demo.Entity.DataDictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataDictRepository extends JpaRepository<DataDictionary, Long> {

}
