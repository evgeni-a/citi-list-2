package com.anchuk.citylist.model.converter;


import com.anchuk.citylist.model.dto.city.CitiesResponse;
import com.anchuk.citylist.model.dto.city.CityDto;
import com.anchuk.citylist.model.entity.CityEntity;
import org.springframework.data.domain.Page;

public class CityConverter {

    private CityConverter() {
    }

    public static CityDto convertToDto(CityEntity cityEntity) {
        return CityDto.builder()
                .id(cityEntity.getId())
                .name(cityEntity.getName())
                .photo(cityEntity.getPhoto()).build();
    }

    public static CitiesResponse convertToDto(Page<CityEntity> result) {
        return CitiesResponse.builder()
                .cities(result.get().map(CityConverter::convertToDto).toList())
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .number(result.getNumber())
                .numberOfElements(result.getNumberOfElements())
                .size(result.getSize())
                .build();
    }
}
