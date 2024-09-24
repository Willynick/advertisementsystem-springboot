package com.senlainc.advertisementsystem.mapper;

import com.senlainc.advertisementsystem.dto.order.purchase.PurchaseDto;
import com.senlainc.advertisementsystem.model.order.purchase.Purchase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PurchaseMapper extends AbstractMapper<Purchase, PurchaseDto, PurchaseDto> {

    private final ModelMapper modelMapper;

    @Autowired
    public PurchaseMapper(ModelMapper modelMapper) {
        super(modelMapper, Purchase.class, PurchaseDto.class);
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(Purchase.class, PurchaseDto.class)
                .addMappings(m -> {
                    m.skip(PurchaseDto::setAdvertisementId);
                    m.skip(PurchaseDto::setOrderId);
                }).setPostConverter(toDtoConverter());
    }

    @Override
    protected void mapFields(Purchase source, PurchaseDto destination) {
        destination.setAdvertisementId(source.getAdvertisement().getId());
        destination.setOrderId(source.getOrder().getId());
    }

    @Override
    protected void mapFields(PurchaseDto source, Purchase destination) {

    }
}
