package com.anchuk.citylist;

import com.anchuk.citylist.exception.EntityNotFoundException;
import com.anchuk.citylist.model.entity.CityEntity;
import com.anchuk.citylist.repository.CityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CityRepositoryTest {

    @Autowired
    private CityRepository cityRepository;

    @Test
    public void cityEntityRepository_whenSaveAndGetEntity_thenOK() {
        CityEntity city = cityRepository.save(CityEntity.builder().name("name").photo("photo").build());
        CityEntity foundEntity = cityRepository.findById(city.getId()).orElseThrow(EntityNotFoundException::new);

        assertNotNull(foundEntity);
        assertEquals(city.getName(), foundEntity.getName());
        assertEquals(city.getPhoto(), foundEntity.getPhoto());
    }


    @Test
    public void cityEntityRepository_whenSaveAndSearchEntity_thenOK() {
        CityEntity city = cityRepository.save(CityEntity.builder().name("ThisIsCity Name").photo("http://prhotphoto123").build());
        Page<CityEntity> foundEntity = cityRepository.findByNameContainsIgnoreCase("isci", Pageable.ofSize(8));

        assertNotNull(foundEntity);
        assertEquals(foundEntity.getContent().getFirst().getPhoto(), city.getPhoto());
    }


    @Test
    public void cityEntityRepository_whenSearchEntityAbsent() {
        cityRepository.save(CityEntity.builder().name("ThisIsCity Name").photo("http://prhotphoto123").build());
        Page<CityEntity> foundEntity = cityRepository.findByNameContainsIgnoreCase("isadasdsci", Pageable.ofSize(8));

        assertNotNull(foundEntity);
        assertEquals(0, foundEntity.getTotalElements());
        assertNotNull(foundEntity.getContent());
        assertEquals(0, foundEntity.getContent().size());
    }


}
