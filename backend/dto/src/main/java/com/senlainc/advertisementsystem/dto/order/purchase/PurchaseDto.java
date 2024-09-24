package com.senlainc.advertisementsystem.dto.order.purchase;

import com.senlainc.advertisementsystem.dto.abstractdto.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseDto extends AbstractDto {

    private double earnedMoney;
    private Long advertisementId;
    private Long orderId;
}
