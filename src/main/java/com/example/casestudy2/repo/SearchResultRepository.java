package com.example.casestudy2.repo;

import com.example.casestudy2.pojo.SearchResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchResultRepository extends JpaRepository<SearchResult, Integer> {

    @Query(value = "SELECT  d.run_number,s.search_keywords, s.platform, s.matching_pattern, s.match_keywords, s.remarks, d.search_date,d.img_url,d.result " +
            "FROM searchs s " +
            "JOIN search_results sr ON s.id = sr.search_id " +
            "JOIN search_result_detail d ON sr.id = d.search_result_id " +
            "WHERE MONTH(d.search_date) = ?1 AND YEAR(d.search_date) = ?2 " +
            "GROUP BY s.id, d.search_date, d.run_number", nativeQuery = true)
    List<Object[]> findSearchResultsByMonthAndYear(int month, int year);

}
