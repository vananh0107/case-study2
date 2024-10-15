package com.example.casestudy2.repo;

import com.example.casestudy2.pojo.SearchResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SearchResultRepository extends JpaRepository<SearchResult, Integer> {
    @Query("SELECT sr FROM SearchResult sr WHERE YEAR(sr.searchDate) = :year AND MONTH(sr.searchDate) = :month")
    List<SearchResult> findByYearAndMonth(int year, int month);
}
