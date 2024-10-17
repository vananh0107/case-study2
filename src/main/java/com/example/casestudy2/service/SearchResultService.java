package com.example.casestudy2.service;

import com.example.casestudy2.dto.SearchResultDTO;
import com.example.casestudy2.dto.SearchResultDetailDTO;
import com.example.casestudy2.pojo.SearchResult;
import com.example.casestudy2.repo.SearchResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

@Service
public class SearchResultService {
    @Autowired
    private SearchResultRepository searchResultRepository;
    public void saveSearchResult(SearchResult searchResult){
        searchResultRepository.save(searchResult);
    }
    public List<SearchResultDTO> getResultsByYearAndMonth(int month, int year) {
        List<Object[]> rawResults = searchResultRepository.findSearchResultsByMonthAndYear(month, year);
        Map<Integer, SearchResultDTO> resultGroups = new HashMap<>();

        // Tính số ngày trong tháng
        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();

        for (Object[] arr : rawResults) {
            int runNumber = (Integer) arr[0];
            String searchKeywords = (String) arr[1];
            String platform = (String) arr[2];
            String matchingPattern = (String) arr[3];
            List<String> matchKeywords = Arrays.asList(((String) arr[4]).split(","));
            String remarks = (String) arr[5];
            LocalDate searchDate = ((Date) arr[6]).toLocalDate();
            int dayOfMonth = searchDate.getDayOfMonth(); // Lấy số ngày trong tháng
            String imgUrl = String.valueOf(arr[7]);
            List<String> resultsList = Arrays.asList(((String) arr[8]).split(","));

            // Tìm hoặc tạo SearchResultDTO
            SearchResultDTO searchResultDTO = resultGroups.computeIfAbsent(runNumber, k -> {
                SearchResultDTO dto = new SearchResultDTO();
                dto.setRunNumber(runNumber);
                dto.setSearchKeywords(searchKeywords);
                dto.setPlatform(platform);
                dto.setMatchingPattern(matchingPattern);
                dto.setMatchKeywords(matchKeywords);
                dto.setRemarks(remarks);
                // Khởi tạo List với số phần tử = số ngày trong tháng, tất cả phần tử đều rỗng ban đầu
                dto.setResults(new ArrayList<>(Collections.nCopies(daysInMonth, null)));
                return dto;
            });

            // Tạo SearchResultDetailDTO cho ngày cụ thể
            SearchResultDetailDTO detailDTO = new SearchResultDetailDTO();
            detailDTO.setSearchDate(searchDate.toString());
            detailDTO.setImgUrl(imgUrl);
            detailDTO.setResults(resultsList);
            Integer indexFound = null;

            if (matchingPattern.equals("Unanimous")) {
                for (int i = 0; i < resultsList.size(); i++) {
                    boolean found = true;
                    for (String keyword : matchKeywords) {
                        if (!resultsList.get(i).equalsIgnoreCase(keyword)) {
                            found = false;
                            break;
                        }
                    }
                    if (found) {
                        indexFound = i;
                        break;
                    }
                }
            } else if (matchingPattern.equals("Partial match")) {
                for (int i = 0; i < resultsList.size(); i++) {
                    for (String keyword : matchKeywords) {
                        if (resultsList.get(i).toLowerCase().contains(keyword.toLowerCase())) {
                            indexFound = i;
                            break;
                        }
                    }
                    if (indexFound != null) {
                        break;
                    }
                }
            }
            detailDTO.setIndexFound(indexFound);
            searchResultDTO.getResults().set(dayOfMonth - 1, detailDTO);
        }

        return new ArrayList<>(resultGroups.values());
    }
}


