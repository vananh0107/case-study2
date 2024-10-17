package com.example.casestudy2.pojo;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Entity
@Data
@Table(name = "search_results")
public class SearchResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "search_id", nullable = false)
    private Search search;

    @OneToMany(mappedBy = "searchResult", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SearchResultDetail> searchResultDetails;

}
