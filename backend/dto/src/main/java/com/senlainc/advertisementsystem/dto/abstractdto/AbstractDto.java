package com.senlainc.advertisementsystem.dto.abstractdto;

import lombok.Data;

import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public class AbstractDto {

    private Long id;
}
