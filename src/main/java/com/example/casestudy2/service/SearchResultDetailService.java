package com.example.casestudy2.service;

import com.example.casestudy2.pojo.SearchResult;
import com.example.casestudy2.pojo.SearchResultDetail;
import com.example.casestudy2.repo.SearchResultDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SearchResultDetailService {
    @Autowired
    private SearchResultDetailRepository searchResultDetailRepository;

    public void create(SearchResult searchResult, String imgUrl, String result) {
        // Get current date
        LocalDate currentDate = LocalDate.now();

        // Find the largest runNumber for the current day
        Integer maxRunNumber = searchResultDetailRepository.findMaxRunNumberBySearchDate(currentDate);

        // If there is no runNumber for this day, maxRunNumber will be 0, so the new runNumber will be 1
        Integer newRunNumber = maxRunNumber + 1;

        // Create new  SearchResultDetail
        SearchResultDetail searchResultDetail = new SearchResultDetail();
        searchResultDetail.setSearchResult(searchResult);
        searchResultDetail.setImgUrl(imgUrl);
        searchResultDetail.setResult(result);
        searchResultDetail.setSearchDate(currentDate);
        searchResultDetail.setRunNumber(newRunNumber);

        // Save database
        searchResultDetailRepository.save(searchResultDetail);
    }
}
