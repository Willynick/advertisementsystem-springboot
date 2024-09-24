package com.senlainc.advertisementsystem.dto.message;

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
public class MessageDto extends AbstractDto {

    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime uploadDate;
    private boolean readed;
    private Long senderId;
    private Long recipientId;
}
