package com.enr.task.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class CsvUtil {

    public static final List<LocationGrid> LOCATION_GRIDS = new ArrayList<>();

    @PostConstruct
    public void readCsvFile() {
        String file = Objects.requireNonNull(getClass().getResource("/static/csv/location-grid.csv"), "파일을 찾을 수 없습니다.")
                .getFile();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                LocationGrid locationGrid = new LocationGrid(data[2], data[1], data[0], new int[]{Integer.parseInt(data[3]), Integer.parseInt(data[4])});
                LOCATION_GRIDS.add(locationGrid);
            }
        } catch (IOException e) {
            log.error("EX!!", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
