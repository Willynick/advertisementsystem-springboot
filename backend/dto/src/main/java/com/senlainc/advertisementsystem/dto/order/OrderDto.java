package com.senlainc.advertisementsystem.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senlainc.advertisementsystem.dto.abstractdto.AbstractDto;
import com.senlainc.advertisementsystem.dto.order.purchase.PurchaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto extends AbstractDto {

    private Long userId;
    private List<PurchaseDto> purchases = new ArrayList<>();
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime date;
    private String deliveryType;
    private List<OrderStatusDto> orderStatuses = new ArrayList<>();
}
