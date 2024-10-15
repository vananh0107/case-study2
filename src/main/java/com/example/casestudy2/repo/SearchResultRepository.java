package com.example.casestudy2.repo;

import com.example.casestudy2.pojo.SearchResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchResultRepository extends JpaRepository<SearchResult, Integer> {
}
