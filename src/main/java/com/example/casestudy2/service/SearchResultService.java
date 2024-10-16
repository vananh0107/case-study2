package com.example.casestudy2.service;

import com.example.casestudy2.dto.SearchResultDTO;
import com.example.casestudy2.dto.SearchResultDetailDTO;
import com.example.casestudy2.repo.SearchResultRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SearchResultService {
    @Autowired
    private SearchResultRepository searchResultRepository;
    public List<SearchResultDTO> getResultsByYearAndMonth(int month, int year) {
        List<Object[]> rawResults = searchResultRepository.findSearchResultsByMonthAndYear(month, year);
        Map<Integer, SearchResultDTO> resultGroups = new HashMap<>();

        for (Object[] arr : rawResults) {
            int runNumber = (Integer) arr[0];
            String searchKeywords = (String) arr[1];
            String platform = (String) arr[2];
            String matchingPattern = (String) arr[3];
            String matchKeywords = (String) arr[4];
            String remarks = (String) arr[5];
            LocalDate searchDate = ((Date) arr[6]).toLocalDate();
            String imgUrl = String.valueOf(arr[7]);
            List<String> resultsList = Arrays.asList(((String) arr[8]).split(","));

            SearchResultDTO searchResultDTO = resultGroups.computeIfAbsent(runNumber, k -> {
                SearchResultDTO dto = new SearchResultDTO();
                dto.setRunNumber(runNumber);
                dto.setSearchKeywords(searchKeywords);
                dto.setPlatform(platform);
                dto.setMatchingPattern(matchingPattern);
                dto.setMatchKeywords(matchKeywords);
                dto.setRemarks(remarks);
                dto.setResults(new ArrayList<>());
                return dto;
            });

            SearchResultDetailDTO detailDTO = new SearchResultDetailDTO();
            detailDTO.setSearchDate(searchDate.toString());
            detailDTO.setImgUrl(imgUrl);
            detailDTO.setResults(resultsList);

            searchResultDTO.getResults().add(detailDTO);
        }

        return new ArrayList<>(resultGroups.values());
    }

}
