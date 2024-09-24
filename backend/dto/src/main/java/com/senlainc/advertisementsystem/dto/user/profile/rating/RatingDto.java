package com.senlainc.advertisementsystem.dto.user.profile.rating;

import com.senlainc.advertisementsystem.dto.abstractdto.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingDto extends AbstractDto {

    private int rating;
    private Long senderId;
    private Long recipientId;
}
