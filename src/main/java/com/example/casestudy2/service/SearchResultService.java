package com.example.casestudy2.service;

import com.example.casestudy2.dto.SearchResultDTO;
import com.example.casestudy2.dto.SearchResultDetailDTO;
import com.example.casestudy2.repo.SearchCount;
import com.example.casestudy2.repo.SearchResultRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.casestudy2.pojo.SearchResult;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

@Service
public class SearchResultService {
    @Autowired
    private SearchResultRepository searchResultRepository;
    @Autowired
    private ModelMapper modelMapper;
    public void create(SearchResult searchResult) {
        searchResultRepository.save(searchResult);
    }


    // Get results of each search by date
    public List<SearchResultDTO> getResultsByYearAndMonth(int month, int year) {
        List<SearchCount> rawResults = searchResultRepository.findSearchResultsByMonthAndYear(month, year);
        Map<Integer, SearchResultDTO> resultGroups = new HashMap<>();
        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();

        for (SearchCount searchCount : rawResults) {
            int runNumber = searchCount.getRunNumber();
            LocalDate searchDate = searchCount.getSearchDate();

            // find or create SearchResultDTO
            SearchResultDTO searchResultDTO = resultGroups.computeIfAbsent(runNumber, k -> createSearchResultDTO(searchCount, daysInMonth));

            // Covert from  SearchCount to SearchResultDetailDTO
            SearchResultDetailDTO detailDTO = modelMapper.map(searchCount, SearchResultDetailDTO.class);
            detailDTO.setResults(Arrays.asList(searchCount.getResult().split(", ")));
            //find Index of element has result same keyword
            detailDTO.setIndexFound(findIndexFound(detailDTO.getResults(), searchCount.getMatchKeywords(), searchCount.getMatchingPattern()));

            searchResultDTO.getResults().set(searchDate.getDayOfMonth() - 1, detailDTO);
        }

        return new ArrayList<>(resultGroups.values());
    }

    private SearchResultDTO createSearchResultDTO(SearchCount searchCount, int daysInMonth) {
        SearchResultDTO dto = modelMapper.map(searchCount, SearchResultDTO.class);
        dto.setResults(new ArrayList<>(Collections.nCopies(daysInMonth, null)));
        return dto;
    }

    private Integer findIndexFound(List<String> resultsList, String matchKeywords, String matchingPattern) {
        List<String> matchKeywordsList = Arrays.asList(matchKeywords.split(","));
        Integer indexFound = null;

        for (int i = 0; i < resultsList.size(); i++) {
            String result = resultsList.get(i);
            boolean found = matchingPattern.equals("Unanimous") ?
                    matchKeywordsList.stream().allMatch(keyword -> result.equalsIgnoreCase(keyword)) :
                    matchKeywordsList.stream().anyMatch(keyword -> result.toLowerCase().contains(keyword.toLowerCase()));

            if (found) {
                indexFound = i;
                break;
            }
        }

        return indexFound;
    }

}
