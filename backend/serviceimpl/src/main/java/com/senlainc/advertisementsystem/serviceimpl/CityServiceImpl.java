package com.senlainc.advertisementsystem.serviceimpl;

import com.senlainc.advertisementsystem.backendutils.Converter;
import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.backendutils.ViewUpdateParameter;
import com.senlainc.advertisementsystem.dao.CityRepository;
import com.senlainc.advertisementsystem.dto.address.country.city.CityView;
import com.senlainc.advertisementsystem.dto.address.country.city.CityDto;
import com.senlainc.advertisementsystem.dto.address.country.city.CityUpdateDto;
import com.senlainc.advertisementsystem.mapper.CityMapper;
import com.senlainc.advertisementsystem.model.address.country.city.City;
import com.senlainc.advertisementsystem.model.address.country.city.City_;
import com.senlainc.advertisementsystem.serviceapi.CityService;
import com.senlainc.advertisementsystem.validatorapi.CityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;
    private final CityValidator cityValidator;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository, CityMapper cityMapper, CityValidator cityValidator) {
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
        this.cityValidator = cityValidator;
    }

    @Override
    public CityDto add(CityUpdateDto cityDto) {
        City city = cityMapper.toEntity(cityDto);
        cityRepository.save(city);
        return cityMapper.toDto(city);
    }

    @Override
    public CityDto update(Long id, CityUpdateDto cityDto) {
        City city = cityRepository.getOne(id);
        cityValidator.checkNotNull(city);

        cityMapper.map(cityDto, city);
        cityRepository.save(city);
        return cityMapper.toDto(city);
    }

    @Override
    public CityDto partiallyUpdate(Long id, List<ViewUpdateParameter> parameters) {
        cityRepository.partiallyUpdate(City_.id, id, Converter.convertUpdateParameter(City_.class, parameters));
        return cityMapper.toDto(cityRepository.getOne(id));
    }

    @Override
    public Boolean delete(Long id) {
        City city = cityRepository.getOne(id);
        cityValidator.checkNotNull(city);

        cityRepository.delete(city);
        return true;
    }

    @Override
    public CityDto getById(Long id) {
        City city = cityRepository.getOne(id);
        cityValidator.checkNotNull(city);

        return cityMapper.toDto(city);
    }

    @Override
    public List<CityDto> get(List<ViewSortParameter> parameters) {
        return cityMapper.toDtoList(
                cityRepository.findAll(Converter.convertSortParameter(parameters)));
    }

    @Override
    public List<CityView> getByCountry(Long countryId, List<ViewSortParameter> parameters) {
        return cityRepository.getByCountryId(countryId);
    }
}
