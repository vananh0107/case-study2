package com.example.casestudy2.repo;

import com.example.casestudy2.pojo.SearchResultDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface SearchResultDetailRepository extends JpaRepository<SearchResultDetail, Integer> {
    @Query("SELECT COALESCE(MAX(s.runNumber), 0) FROM SearchResultDetail s WHERE s.searchDate = :searchDate")
    Integer findMaxRunNumberBySearchDate(@Param("searchDate") LocalDate searchDate);
}
