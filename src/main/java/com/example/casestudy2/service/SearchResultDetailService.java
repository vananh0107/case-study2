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

    public void createSearchResultDetail(SearchResult searchResult, String imgUrl, String result) {
        // Lấy ngày hiện tại
        LocalDate currentDate = LocalDate.now();

        // Tìm runNumber lớn nhất cho ngày hiện tại
        Integer maxRunNumber = searchResultDetailRepository.findMaxRunNumberBySearchDate(currentDate);

        // Nếu chưa có runNumber cho ngày này, maxRunNumber sẽ là 0, do đó runNumber mới sẽ là 1
        Integer newRunNumber = maxRunNumber + 1;

        // Tạo mới SearchResultDetail
        SearchResultDetail searchResultDetail = new SearchResultDetail();
        searchResultDetail.setSearchResult(searchResult);
        searchResultDetail.setImgUrl(imgUrl);
        searchResultDetail.setResult(result);
        searchResultDetail.setSearchDate(currentDate);
        searchResultDetail.setRunNumber(newRunNumber);

        // Lưu vào database
        searchResultDetailRepository.save(searchResultDetail);
    }
}
