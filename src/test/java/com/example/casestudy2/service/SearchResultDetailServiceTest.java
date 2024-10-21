package com.example.casestudy2.service;
import com.example.casestudy2.pojo.SearchResult;
import com.example.casestudy2.pojo.SearchResultDetail;
import com.example.casestudy2.repo.SearchResultDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SearchResultDetailServiceTest {

    @Mock
    private SearchResultDetailRepository searchResultDetailRepository;

    @InjectMocks
    private SearchResultDetailService searchResultDetailService;

    private SearchResult searchResult;
    private String imgUrl;
    private String result;

    @BeforeEach
    public void setUp() {
        searchResult = new SearchResult();
        searchResult.setId(1);

        imgUrl = "http://example.com/image.png";
        result = "Search result text";
    }

    @Test
    public void testCreate() {
        when(searchResultDetailRepository.findMaxRunNumberBySearchDate(LocalDate.now())).thenReturn(0);

        searchResultDetailService.create(searchResult, imgUrl, result);

        verify(searchResultDetailRepository, times(1)).save(any(SearchResultDetail.class));

        verify(searchResultDetailRepository, times(1)).save(argThat(searchResultDetail ->
                searchResultDetail.getRunNumber() == 1 &&
                        searchResultDetail.getSearchResult().getId() == 1 &&
                        searchResultDetail.getImgUrl().equals(imgUrl) &&
                        searchResultDetail.getResult().equals(result)
        ));
    }
}