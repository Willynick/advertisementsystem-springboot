package com.senlainc.advertisementsystem.mapper;

import com.senlainc.advertisementsystem.dto.order.OrderDto;
import com.senlainc.advertisementsystem.dto.order.OrderUpdateDto;
import com.senlainc.advertisementsystem.model.order.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;

@Component
public class OrderMapper extends AbstractMapper<Order, OrderDto, OrderUpdateDto> {

    private final PurchaseMapper purchaseMapper;
    private final ModelMapper modelMapper;
    private final OrderStatusMapper orderStatusMapper;

    @Autowired
    public OrderMapper(PurchaseMapper purchaseMapper, ModelMapper modelMapper, OrderStatusMapper orderStatusMapper) {
        super(modelMapper, Order.class, OrderDto.class);
        this.purchaseMapper = purchaseMapper;
        this.modelMapper = modelMapper;
        this.orderStatusMapper = orderStatusMapper;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(Order.class, OrderDto.class)
                .addMappings(m -> {
                    m.skip(OrderDto::setUserId);
                    m.skip(OrderDto::setPurchases);
                    m.skip(OrderDto::setOrderStatuses);
                    m.skip(OrderDto::setDeliveryType);
                }).setPostConverter(toDtoConverter());
    }

    @Override
    protected void mapFields(Order source, OrderDto destination) {
        destination.setUserId(source.getProfile().getUser().getId());
        destination.setPurchases(
                source.getPurchases().stream().map(purchaseMapper::toDto).collect(Collectors.toList())
        );
        destination.setDeliveryType(source.getDeliveryType().getName());
        destination.setOrderStatuses(
                source.getOrderStatuses().stream().map(orderStatusMapper::toDto).collect(Collectors.toList())
        );
    }

    @Override
    protected void mapFields(OrderUpdateDto source, Order destination) {

    }
}
