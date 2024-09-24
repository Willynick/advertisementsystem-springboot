package com.senlainc.advertisementsystem.dto.order;

import com.senlainc.advertisementsystem.model.order.DeliveryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderUpdateDto {

    private DeliveryType deliveryType;
}
