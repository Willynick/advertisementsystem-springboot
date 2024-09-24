package com.senlainc.advertisementsystem.mapper;

import com.senlainc.advertisementsystem.dto.abstractdto.AbstractDto;
import com.senlainc.advertisementsystem.model.abstractmodel.AbstractModel;

import java.util.List;

public interface Mapper<E extends AbstractModel, D extends AbstractDto, UD> {

    E toEntity(UD dto);
    D toDto(E entity);
    List<E> toEntityList(List<UD> dtoList);
    List<D> toDtoList(List<E> entityList);
    void map(UD source, E destination);
}
