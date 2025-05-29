package com.anchuk.citylist.service;

import com.anchuk.citylist.exception.EntityNotFoundException;
import com.anchuk.citylist.model.converter.CityConverter;
import com.anchuk.citylist.model.dto.city.CitiesResponse;
import com.anchuk.citylist.model.dto.city.CityDto;
import com.anchuk.citylist.model.dto.city.CityUpdateRequest;
import com.anchuk.citylist.model.entity.CityEntity;
import com.anchuk.citylist.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;

    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
    public CitiesResponse getAllCities(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CityEntity> result = StringUtils.isNotBlank(search) ?
                cityRepository.findByNameContainsIgnoreCase(search, pageable) : cityRepository.findAll(pageable);
        return CityConverter.convertToDto(result);
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
    public CityDto getCityById(Long id) {
        CityEntity result = cityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("City not found with ID: " + id));
        return CityConverter.convertToDto(result);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public CityDto updateCity(Long id, CityUpdateRequest request) {
        CityEntity repositoryItem = cityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("City not found with ID: " + id));
        repositoryItem.setName(request.getName());
        repositoryItem.setPhoto(request.getPhoto());
        cityRepository.saveAndFlush(repositoryItem);
        return CityConverter.convertToDto(repositoryItem);
    }

}
