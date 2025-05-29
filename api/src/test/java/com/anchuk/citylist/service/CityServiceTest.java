package com.anchuk.citylist.service;

import com.anchuk.citylist.model.dto.city.CitiesResponse;
import com.anchuk.citylist.model.dto.city.CityDto;
import com.anchuk.citylist.model.dto.city.CityUpdateRequest;
import com.anchuk.citylist.model.entity.CityEntity;
import com.anchuk.citylist.repository.CityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CityServiceTest {

    @Autowired
    private CityService cityService;
    @Autowired
    private CityRepository cityRepository;

    @Test
    void getAllCities() {
        List<CityEntity> cities = List.of(
                CityEntity.builder().name("New York").photo("https://upload.wikim/iew_of_Empire.jpg").build(),
                CityEntity.builder().name("Warsaw").photo("https://upload.wikim/a_siekie").build(),
                CityEntity.builder().name("Praga").photo("https://t0.gstatic.com/licensed-image?q=").build()
        );

        cityRepository.saveAll(cities);
        CitiesResponse citiesResponse = cityService.getAllCities(0, 2, null);
        assertNotNull(citiesResponse);
        assertEquals(2, citiesResponse.getCities().size());
    }

    @Test
    void getCityById() {
        CityEntity city = CityEntity.builder().name("Test city").photo("http://prhotphoto123").build();
        CityEntity repositoryCity = cityRepository.save(city);
        CityDto cityDto = cityService.getCityById(repositoryCity.getId());
        assertNotNull(cityDto);
        assertEquals(city.getName(), cityDto.getName());
        assertEquals(city.getPhoto(), cityDto.getPhoto());
    }

    @Test
    void updateCity() {
        CityEntity city = CityEntity.builder().name("Test city").photo("http://prhotphoto123").build();
        CityEntity repositoryCity = cityRepository.save(city);

        CityUpdateRequest cityUpdateRequest = CityUpdateRequest.builder().name("City1Updated2").photo("photo1Updated").build();
        CityDto cityDto = cityService.updateCity(repositoryCity.getId(), cityUpdateRequest);
        CityEntity repositoryCityAfterUpdate = cityRepository.findById(repositoryCity.getId()).orElseThrow();
        assertNotNull(cityDto);
        assertEquals(cityUpdateRequest.getName(), cityDto.getName());
        assertEquals(cityUpdateRequest.getPhoto(), cityDto.getPhoto());
        assertEquals(repositoryCityAfterUpdate.getName(), cityDto.getName());
        assertEquals(repositoryCityAfterUpdate.getPhoto(), cityDto.getPhoto());
    }

}
