package com.anchuk.citylist.model.dto.city;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CitiesResponse {

    private List<CityDto> cities;
    private long totalElements;
    private int totalPages;
    private int number;
    private int numberOfElements;
    private int size;

}

