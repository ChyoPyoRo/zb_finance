package com.zerobase.finance.utils;

import com.zerobase.finance.dto.ScrapingDataDto;
import com.zerobase.finance.enums.ErrorCode;
import com.zerobase.finance.exception.ScrappiingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScrappingUtil {

    public List<ScrapingDataDto> getScrapingData(String ticker) throws IOException {
        // Yahoo Finance URL
        String url = "https://finance.yahoo.com/quote/" + ticker + "/history";
        System.out.println("Scraping Dividend Data from: " + url);

        // HTML 문서 로드
        Document doc = Jsoup.connect(url).get();
//        log.info("htmlCodeStart : {}", doc);  // HTML 일부 확인
//        log.info("html end");

        List<ScrapingDataDto> dividendList = new ArrayList<>();
        Element companyElement = doc.selectFirst("h1");
        String companyName = companyElement != null ? companyElement.text() : "Unknown";
        System.out.println("Extracted Company Name: " + companyName);

        Elements dividendRows = doc.select("tr.yf-1jecxey"); // 로그에서 확인된 class명

        for (Element row : dividendRows) {
            Elements columns = row.select("td");

            if (columns.size() >= 2) {
                String dateText = columns.get(0).text(); // 첫 번째 <td>에 날짜 존재
                String dividendText = columns.get(1).text(); // 두 번째 <td>에 배당금 존재

                // "Dividend" 키워드가 포함된 경우만 처리
                if (dividendText.contains("Dividend")) {
                    String dividendValue = dividendText.replace("Dividend", "").trim();

                    LocalDateTime marketDate = parseExDividendDate(dateText);
                    ScrapingDataDto dataDto = new ScrapingDataDto(companyName, ticker, dividendValue, marketDate);
                    dividendList.add(dataDto);
                }
            }
        }

        // 디버깅용 리스트 확인
        if (dividendList.isEmpty()) {
            throw new ScrappiingException(ErrorCode.COMPANY_NOT_EXIST.name());
        }

        return dividendList;
    }

    private static LocalDateTime parseExDividendDate(String dateText) {
        if (dateText == null || dateText.isEmpty()) {
            return LocalDateTime.now();
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);
            LocalDate parsedDate = LocalDate.parse(dateText, formatter);
            return LocalDateTime.of(parsedDate, java.time.LocalTime.MIDNIGHT);
        } catch (Exception e) {
            System.out.println("Date Parsing Error: " + e.getMessage());
            return LocalDateTime.now();
        }
    }
}
