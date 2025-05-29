package com.anchuk.citylist.controller;

import com.anchuk.citylist.model.dto.city.CitiesResponse;
import com.anchuk.citylist.model.dto.city.CityDto;
import com.anchuk.citylist.model.dto.city.CityUpdateRequest;
import com.anchuk.citylist.service.CityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;

class CityControllerTest {
    @Mock
    private CityService cityService;

    @InjectMocks
    private CityController cityController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCities_success() {
        List<CityDto> cities = List.of(
                CityDto.builder().id(1L).name("New York").photo("https://upload.wikimedia.org/wikipedia/commons/thumb/0/05/View_of_Empire_State_Building_from_Rockefeller_Center_New_York_City_dllu.jpg/536px-View_of_Empire_State_Building_from_Rockefeller_Center_New_York_City_dllu.jpg").build(),
                CityDto.builder().id(2L).name("Warsaw").photo("https://upload.wikimedia.org/wikipedia/commons/thumb/2/2a/Panorama_siekierkowski.jpg/2880px-Panorama_siekierkowski.jpg").build(),
                CityDto.builder().id(3L).name("Praga").photo("http://t0.gstatic.com/licensed-image?q=tbn:ANd9GcRzR-Cm7dKnn9iArOLyS3ABBb-HI4UXXdmc1FJW7QrymV_50Gc8g9euBO5Y40IVj1BN").build()
        );
        CitiesResponse expected = CitiesResponse.builder()
                .cities(cities)
                .totalElements(cities.size())
                .totalPages(1)
                .number(0)
                .numberOfElements(cities.size())
                .size(cities.size())
                .build();
        when(cityService.getAllCities(anyInt(), anyInt(), isNull())).thenReturn(expected);

        ResponseEntity<CitiesResponse> response = cityController.getAllCities(0, 10, null);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    void getCityById_success() {
        CityDto expected = CityDto.builder().build();
        when(cityService.getCityById(any())).thenReturn(expected);

        ResponseEntity<CityDto> response = cityController.getCityById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    void updateCity_success() {
        CityDto expected = CityDto.builder().id(1L).name("Belgrad").photo("https://photoUrl.com/belgrad").build();
        when(cityService.updateCity(any(), any())).thenReturn(expected);

        CityUpdateRequest request = CityUpdateRequest.builder().name("Belgrad").photo("https://photoUrl.com/belgrad").build();
        ResponseEntity<CityDto> response = cityController.updateCity(1L, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expected);
    }

}
