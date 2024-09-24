package com.senlainc.advertisementsystem.mapper;

import com.senlainc.advertisementsystem.dao.AddressRepository;
import com.senlainc.advertisementsystem.dao.CategoryRepository;
import com.senlainc.advertisementsystem.dto.advertisement.AdvertisementDto;
import com.senlainc.advertisementsystem.dto.advertisement.AdvertisementUpdateDto;
import com.senlainc.advertisementsystem.model.address.Address;
import com.senlainc.advertisementsystem.model.advertisement.Advertisement;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AdvertisementMapper extends AbstractMapper<Advertisement, AdvertisementDto, AdvertisementUpdateDto> {

    private final CategoryRepository categoryRepository;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final ModelMapper modelMapper;

    @Autowired
    public AdvertisementMapper(CategoryRepository categoryRepository, AddressRepository addressRepository, ModelMapper modelMapper,
                               AddressMapper addressMapper) {
        super(modelMapper, Advertisement.class, AdvertisementDto.class);
        this.categoryRepository = categoryRepository;
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
        this.addressMapper = addressMapper;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(Advertisement.class, AdvertisementDto.class)
                .addMappings(m -> {
                    m.skip(AdvertisementDto::setUserId);
                    m.skip(AdvertisementDto::setCategoryId);
                    m.skip(AdvertisementDto::setAdvertisementStatus);
                    m.skip(AdvertisementDto::setProductCondition);
                }).setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(AdvertisementUpdateDto.class, Advertisement.class)
                .addMappings(m -> {
                    m.skip(Advertisement::setCategory);
                    m.skip(Advertisement::setAddresses);
                }).setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapFields(Advertisement source, AdvertisementDto destination) {
        destination.setUserId(source.getProfile().getUser().getId());
        destination.setCategoryId(source.getCategory().getId());
        destination.setAdvertisementStatus(source.getAdvertisementStatus().getName());
        destination.setProductCondition(source.getProductCondition().getName());
    }

    @Override
    protected void mapFields(AdvertisementUpdateDto source, Advertisement destination) {
        destination.setCategory(categoryRepository.getOne(source.getCategoryId()));

        List<Address> addresses = source.getAddresses().stream().map(address ->
                address.getId() != null
                        ? addressRepository.getOne(address.getId())
                        : getAddress(addressMapper.toEntity(address), destination))
                .collect(Collectors.toList());
        destination.setAddresses(addresses);
    }

    private Address getAddress(Address address, Advertisement advertisement) {
        address.setAdvertisement(advertisement);
        return address;
    }
}
