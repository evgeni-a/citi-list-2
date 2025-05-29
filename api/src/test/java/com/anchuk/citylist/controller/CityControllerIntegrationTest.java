package com.anchuk.citylist.controller;

import com.anchuk.citylist.model.dto.city.CitiesResponse;
import com.anchuk.citylist.model.dto.city.CityDto;
import com.anchuk.citylist.service.CityService;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = CityController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CityControllerIntegrationTest {

    @Autowired
    CityController cityController;
    @MockBean
    private CityService cityService;
    @Autowired
    private MockMvc mockMvc;

    private static List<CityDto> arrayOfTestCities() {
        return List.of(CityDto.builder().id(1L).name("name").photo("https://site.com/131").build(),
                CityDto.builder().id(2L).name("name1").photo("https://site.com/133").build(),
                CityDto.builder().id(3L).name("name2").photo("https://site.com/134").build(),
                CityDto.builder().id(4L).name("name3").photo("https://site.com/135").build(),
                CityDto.builder().id(5L).name("name3").photo("https://site.com/135").build(),
                CityDto.builder().id(6L).name("name3").photo("https://site.com/135").build(),
                CityDto.builder().id(7L).name("name3").photo("https://site.com/135").build(),
                CityDto.builder().id(8L).name("name3").photo("https://site.com/135").build(),
                CityDto.builder().id(9L).name("name3").photo("https://site.com/135").build(),
                CityDto.builder().id(10L).name("name3").photo("https://site.com/135").build());
    }

    @Test
    public void whenGetRequestToGetAllCitiesWithPagination_thenCorrectResponse() throws Exception {
        when(cityService.getAllCities(anyInt(), anyInt(), isNull()))
                .thenReturn(CitiesResponse.builder()
                        .cities(arrayOfTestCities())
                        .totalElements(arrayOfTestCities().size())
                        .totalPages(1)
                        .number(0)
                        .numberOfElements(10)
                        .size(arrayOfTestCities().size())
                        .build());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cities"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.cities[3].id", Is.is(4)))
                .andExpect(jsonPath("$.cities[0].name", Is.is("name")))
                .andExpect(jsonPath("$.cities[1].name", Is.is("name1")))
                .andExpect(jsonPath("$.cities[3].photo", Is.is("https://site.com/135")))
                .andExpect(jsonPath("$.totalElements", Is.is(10)))
                .andExpect(jsonPath("$.totalPages", Is.is(1)))
                .andExpect(jsonPath("$.number", Is.is(0)))
                .andExpect(jsonPath("$.numberOfElements", Is.is(10)))
                .andExpect(jsonPath("$.size", Is.is(10)));
    }

    @Test
    public void whenGetRequestToGetCityById_thenCorrectResponse() throws Exception {
        CityDto city = arrayOfTestCities().get(0);
        when(cityService.getCityById(anyLong()))
                .thenReturn(city);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cities/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Is.is(1)))
                .andExpect(jsonPath("$.name", Is.is(city.getName())))
                .andExpect(jsonPath("$.photo", Is.is(city.getPhoto())));
    }

    @Test
    public void whenPutRequestToUpdateCity_thenCorrectResponse() throws Exception {
        CityDto city = arrayOfTestCities().get(0);
        when(cityService.updateCity(anyLong(), any()))
                .thenReturn(city);

        String request = """
                {"name": "%s", "photo" : "%s"}"""
                .formatted(city.getName(), city.getPhoto());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/cities/1")
                        .content(request)
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Is.is(1)))
                .andExpect(jsonPath("$.name", Is.is(city.getName())))
                .andExpect(jsonPath("$.photo", Is.is(city.getPhoto())));
    }

    @Test
    public void whenPutRequestToUpdateCity_withEmptyName_thenBadRequest() throws Exception {
        CityDto city = arrayOfTestCities().get(0);
        when(cityService.updateCity(anyLong(), any()))
                .thenReturn(city);

        String request = """
                {"name": "%s", "photo" : "%s"}"""
                .formatted("", city.getPhoto());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/cities/1")
                        .content(request)
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.name", Is.is("Name length should be between 3 an 50")));
    }

    @Test
    public void whenPutRequestToUpdateCity_withShortPhotoUrl_thenBadRequest() throws Exception {
        CityDto city = arrayOfTestCities().get(0);
        when(cityService.updateCity(anyLong(), any()))
                .thenReturn(city);

        String request = """
                {"name": "%s", "photo" : "%s"}"""
                .formatted("New Your", "htt");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/cities/1")
                        .content(request)
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.photo", Is.is("Photo URL length should be between 10 an 50")));
    }

}

