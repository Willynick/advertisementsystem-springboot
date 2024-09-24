package com.senlainc.advertisementsystem.mapper;

import com.senlainc.advertisementsystem.dto.order.OrderStatusDto;
import com.senlainc.advertisementsystem.model.order.OrderStatusModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class OrderStatusMapper extends AbstractMapper<OrderStatusModel, OrderStatusDto, OrderStatusDto>  {

    private final ModelMapper modelMapper;

    @Autowired
    public OrderStatusMapper(ModelMapper modelMapper) {
        super(modelMapper, OrderStatusModel.class, OrderStatusDto.class);
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(OrderStatusModel.class, OrderStatusDto.class)
                .addMappings(m -> {
                    m.skip(OrderStatusDto::setStatus);
                }).setPostConverter(toDtoConverter());
    }

    @Override
    protected void mapFields(OrderStatusModel source, OrderStatusDto destination) {
        destination.setStatus(source.getStatus().getName());
    }

    @Override
    protected void mapFields(OrderStatusDto source, OrderStatusModel destination) {

    }
}
