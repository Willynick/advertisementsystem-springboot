package com.senlainc.advertisementsystem.serviceimpl;

import com.senlainc.advertisementsystem.backendutils.Converter;
import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.backendutils.ViewUpdateParameter;
import com.senlainc.advertisementsystem.dao.CountryRepository;
import com.senlainc.advertisementsystem.daospec.CountrySpecification;
import com.senlainc.advertisementsystem.dto.address.country.CountryDto;
import com.senlainc.advertisementsystem.dto.address.country.CountryUpdateDto;
import com.senlainc.advertisementsystem.mapper.CountryMapper;
import com.senlainc.advertisementsystem.model.address.country.Country;
import com.senlainc.advertisementsystem.model.address.country.Country_;
import com.senlainc.advertisementsystem.serviceapi.CountryService;
import com.senlainc.advertisementsystem.validatorapi.CountryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;
    private final CountryValidator countryValidator;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository, CountryMapper countryMapper, CountryValidator countryValidator) {
        this.countryRepository = countryRepository;
        this.countryMapper = countryMapper;
        this.countryValidator = countryValidator;
    }

    @Override
    public CountryDto add(CountryUpdateDto countryDto) {
        Country country = countryMapper.toEntity(countryDto);
        countryRepository.save(country);
        return countryMapper.toDto(country);
    }

    @Override
    public CountryDto update(Long id, CountryUpdateDto countryDto) {
        Optional<Country> country = countryRepository.findById(id);
        countryValidator.checkNotNull(country.get());

        countryMapper.map(countryDto, country.get());
        countryRepository.save(country.get());
        return countryMapper.toDto(country.get());
    }

    @Override
    public CountryDto partiallyUpdate(Long id, List<ViewUpdateParameter> parameters) {
        countryRepository.partiallyUpdate(Country_.id, id, Converter.convertUpdateParameter(Country_.class, parameters));
        return countryMapper.toDto(countryRepository.getOne(id));
    }

    @Override
    public Boolean delete(Long id) {
        Country country = countryRepository.getOne(id);
        countryValidator.checkNotNull(country);

        countryRepository.delete(country);
        return true;
    }

    @Override
    public CountryDto getById(Long id) {
        Country country = countryRepository.getOne(id);
        countryValidator.checkNotNull(country);

        return countryMapper.toDto(country);
    }

    @Override
    public List<CountryDto> get(List<ViewSortParameter> parameters) {
        return countryMapper.toDtoList(
                countryRepository.findAll(Converter.convertSortParameter(parameters)));
    }

    @Override
    public CountryDto getByCity(Long cityId) {
        Country country = countryRepository.findOne(CountrySpecification.getByCityId(cityId)).get();
        countryValidator.checkNotNull(country);

        return countryMapper.toDto(country);
    }
}
