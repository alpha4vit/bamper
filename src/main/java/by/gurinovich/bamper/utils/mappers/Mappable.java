package by.gurinovich.bamper.utils.mappers;

import java.util.List;

public interface Mappable<E, D> {

    public E fromDTO(D dto);

    public D toDTO(E entity);

    public List<D> toDTOs(List<E> entities);

    public List<E> toEntities(List<D> dtos);

}
