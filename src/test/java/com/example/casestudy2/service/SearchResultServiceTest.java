package com.example.casestudy2.service;

import com.example.casestudy2.dto.SearchResultDTO;
import com.example.casestudy2.dto.SearchResultDetailDTO;
import com.example.casestudy2.pojo.SearchResult;
import com.example.casestudy2.repo.SearchCount;
import com.example.casestudy2.repo.SearchResultRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SearchResultServiceTest {

    @Mock
    private SearchResultRepository searchResultRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private SearchResultService searchResultService;

    private SearchResult searchResult;

    @BeforeEach
    public void setUp() {
        searchResult = new SearchResult();
        searchResult.setId(1);
    }

    @Test
    public void testCreate() {
        searchResultService.create(searchResult);

        verify(searchResultRepository, times(1)).save(searchResult);
    }

    @Test
    public void testGetResultsByYearAndMonth() {
        int month = 10;
        int year = 2023;

        //  data test
        SearchCount searchCount1 = new SearchCount();
        searchCount1.setRunNumber(1);
        searchCount1.setSearchDate(LocalDate.of(2023, 10, 21));
        searchCount1.setResult("result1");
        searchCount1.setMatchKeywords("keyword1");
        searchCount1.setMatchingPattern("Unanimous");

        SearchCount searchCount2 = new SearchCount();
        searchCount2.setRunNumber(1);
        searchCount2.setSearchDate(LocalDate.of(2023, 10, 22));
        searchCount2.setResult("result2");
        searchCount2.setMatchKeywords("keyword1,keyword2");
        searchCount2.setMatchingPattern("Partial match");

        List<SearchCount> rawResults = Arrays.asList(searchCount1, searchCount2);
        when(searchResultRepository.findSearchResultsByMonthAndYear(month, year)).thenReturn(rawResults);

        SearchResultDetailDTO detailDTO1 = new SearchResultDetailDTO();
        detailDTO1.setResults(Arrays.asList("result1"));
        SearchResultDetailDTO detailDTO2 = new SearchResultDetailDTO();
        detailDTO2.setResults(Arrays.asList("result2"));

        when(modelMapper.map(searchCount1, SearchResultDetailDTO.class)).thenReturn(detailDTO1);
        when(modelMapper.map(searchCount2, SearchResultDetailDTO.class)).thenReturn(detailDTO2);

        SearchResultDTO searchResultDTO1 = new SearchResultDTO();
        searchResultDTO1.setResults(new ArrayList<>(Collections.nCopies(31, null)));

        when(modelMapper.map(searchCount1, SearchResultDTO.class)).thenReturn(searchResultDTO1);

        List<SearchResultDTO> result = searchResultService.getResultsByYearAndMonth(month, year);

        assertNotNull(result);
        assertEquals(1, result.size());

        SearchResultDTO searchResultDTOResult = result.get(0);
        assertNotNull(searchResultDTOResult);

        assertEquals(31, searchResultDTOResult.getResults().size());

        SearchResultDetailDTO day1Detail = searchResultDTOResult.getResults().get(0);
        assertNotNull(day1Detail);
        assertTrue(day1Detail.getResults().contains("result1"));

        SearchResultDetailDTO day2Detail = searchResultDTOResult.getResults().get(1);
        assertNotNull(day2Detail);
        assertTrue(day2Detail.getResults().contains("result2"));
    }
}