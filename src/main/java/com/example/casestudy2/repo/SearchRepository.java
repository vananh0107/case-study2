package com.example.casestudy2.repo;

import com.example.casestudy2.pojo.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchRepository extends JpaRepository<Search, Integer> {
}
