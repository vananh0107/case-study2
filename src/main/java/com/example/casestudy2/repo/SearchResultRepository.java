package com.example.casestudy2.repo;

import com.example.casestudy2.pojo.SearchResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchResultRepository extends JpaRepository<SearchResult, Integer> {

    @Query(value = "SELECT new com.example.casestudy2.repo.SearchCount(" +
            "sr.runNumber, s.searchKeywords, s.platform, s.matchingPattern, s.matchKeywords, " +
            "s.remarks, sr.searchDate, sr.imgUrl, sr.result) " +
            "FROM Search s " +
            "JOIN s.searchResults d " +
            "JOIN d.searchResultDetails sr " +
            "WHERE MONTH(sr.searchDate) = ?1 AND YEAR(sr.searchDate) = ?2 " +
            "GROUP BY s.id, sr.searchDate, sr.runNumber")
    List<SearchCount> findSearchResultsByMonthAndYear(int month, int year);

}
