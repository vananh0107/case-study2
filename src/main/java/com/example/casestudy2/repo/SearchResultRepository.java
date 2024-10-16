package com.example.casestudy2.repo;

import com.example.casestudy2.dto.SearchResultDTO;
import com.example.casestudy2.dto.SearchResultDetailDTO;
import com.example.casestudy2.pojo.SearchResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SearchResultRepository extends JpaRepository<SearchResult, Integer> {
    @Query(value = "SELECT s.search_keywords, s.platform, s.matching_pattern, s.match_keywords, s.remarks, d.search_date, d.run_number,srd.img_url,GROUP_CONCAT(rd.result SEPARATOR ', ') AS results " +
            "FROM searchs s " +
            "JOIN search_results sr ON s.id = sr.search_id " +
            "JOIN search_result_detail srd d ON sr.id = d.search_result_id " +
            "JOIN result_details rd ON d.id = rd.search_result_detail_id " +
            "WHERE MONTH(d.search_date) = ?1 AND YEAR(d.search_date) = ?2 " +
            "GROUP BY s.id, d.search_date, d.run_number", nativeQuery = true)
    List<Object[]> findSearchResultsByMonthAndYear(int month, int year);

}
