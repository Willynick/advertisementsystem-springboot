package com.senlainc.advertisementsystem.dto.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senlainc.advertisementsystem.dto.abstractdto.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto extends AbstractDto {

    private String text;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime uploadDate;
    private Long userId;
    private Long advertisementId;
}
