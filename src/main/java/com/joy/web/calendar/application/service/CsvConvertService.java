package com.joy.web.calendar.application.service;

import com.joy.config.exception.constant.ExceptionInfo;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 민팅 이벤트 서비스.
 */
@Service
@RequiredArgsConstructor
public class CsvConvertService {
  private static String fetchFirstDataAtRow(final CSVRecord csvRecord) {
    return csvRecord.stream().findFirst().map(String::toLowerCase).orElseThrow(ExceptionInfo.INVALID_WHITE_LIST_FILE::exception);
  }

  public List<String> convertToSubjects(final MultipartFile file) throws IOException {
    return new CSVParser(new InputStreamReader(file.getInputStream()), CSVFormat.DEFAULT.builder()
                                                                                        .setHeader()
                                                                                        .setSkipHeaderRecord(true)
                                                                                        .build())
        .stream()
        .distinct()
        .map(CsvConvertService::fetchFirstDataAtRow)
        .toList();
  }
}
