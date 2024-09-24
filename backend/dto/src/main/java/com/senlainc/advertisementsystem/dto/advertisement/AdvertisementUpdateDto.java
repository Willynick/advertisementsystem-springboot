package com.senlainc.advertisementsystem.dto.advertisement;

import com.senlainc.advertisementsystem.dto.address.AddressUpdateDto;
import com.senlainc.advertisementsystem.model.advertisement.ProductCondition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdvertisementUpdateDto {

    @NotEmpty(message = "Please provide a title")
    private String title;
    @NotEmpty(message = "Please provide a description")
    private String description;
    private String manufacturer;
    private ProductCondition productCondition;
    @NotEmpty(message = "Please provide a phoneNumber")
    private String phoneNumber;
    private double price;
    private Long categoryId;
    private List<AddressUpdateDto> addresses = new ArrayList<>();
}
