package com.anchuk.citylist.converter;

import com.anchuk.citylist.model.converter.CityConverter;
import com.anchuk.citylist.model.dto.city.CitiesResponse;
import com.anchuk.citylist.model.dto.city.CityDto;
import com.anchuk.citylist.model.entity.CityEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CityConverterTest {

    CityEntity cityEntity = new CityEntity(1L, "name", "photo");
    CityDto cityDto = new CityDto(1L, "name", "photo");

    @Test
    void convertToDto() {
        CityDto response = CityConverter.convertToDto(cityEntity);
        assertEquals(response, cityDto);
    }

    @Test
    void convertToDtoList() {
        List<CityEntity> cities = List.of(CityEntity.builder().id(1L).name("name").photo("photo").build());

        Pageable pageable = PageRequest.of(0, 8);

        Page<CityEntity> result = new PageImpl<>(cities, pageable, 3);


        CitiesResponse response = CityConverter.convertToDto(result);
        assertEquals(List.of(cityDto), response.getCities());
        assertEquals(1, response.getTotalElements());
        assertEquals(1, response.getTotalPages());
        assertEquals(0, response.getNumber());
        assertEquals(1, response.getNumberOfElements());
        assertEquals(8, response.getSize());
    }


}
