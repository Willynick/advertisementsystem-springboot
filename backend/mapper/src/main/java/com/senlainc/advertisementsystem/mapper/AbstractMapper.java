package com.senlainc.advertisementsystem.mapper;

import com.senlainc.advertisementsystem.dto.abstractdto.AbstractDto;
import com.senlainc.advertisementsystem.model.abstractmodel.AbstractModel;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractMapper<E extends AbstractModel, D extends AbstractDto, UD> implements Mapper<E, D, UD> {

    private final ModelMapper modelMapper;
    private final Class<E> entityClass;
    private final Class<D> dtoClass;

    public AbstractMapper(ModelMapper modelMapper, Class<E> entityClass, Class<D> dtoClass) {
        this.modelMapper = modelMapper;
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

    @Override
    public E toEntity(UD dto) {
        return Objects.isNull(dto) ? null : modelMapper.map(dto, entityClass);
    }

    @Override
    public D toDto(E entity) {
        return Objects.isNull(entity) ? null : modelMapper.map(entity, dtoClass);
    }

    @Override
    public List<E> toEntityList(List<UD> dtoList) {
        List<E> entityList = new ArrayList<>();
        dtoList.forEach(dto -> entityList.add(toEntity(dto)));
        return entityList;
    }

    @Override
    public List<D> toDtoList(List<E> entityList) {
        List<D> dtoList = new ArrayList<>();
        entityList.forEach(entity -> dtoList.add(toDto(entity)));
        return dtoList;
    }

    @Override
    public void map(UD source, E destination) {
        modelMapper.map(source, destination);
    }

    Converter<E, D> toDtoConverter() {
        return context -> {
            E source = context.getSource();
            D destination = context.getDestination();
            mapFields(source, destination);
            return context.getDestination();
        };
    }

    Converter<UD, E> toEntityConverter() {
        return context -> {
            UD source = context.getSource();
            E destination = context.getDestination();
            mapFields(source, destination);
            return context.getDestination();
        };
    }

    protected abstract void mapFields(E source, D destination);

    protected abstract void mapFields(UD source, E destination);
}
