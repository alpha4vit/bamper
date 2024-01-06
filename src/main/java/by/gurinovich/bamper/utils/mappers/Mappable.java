package by.gurinovich.bamper.utils.mappers;

import java.text.ParseException;
import java.util.List;

public interface Mappable<E, D> {
     E fromDTO(D dto) throws ParseException;

     D toDTO(E entity);

     List<D> toDTOs(List<E> entities);

     List<E> toEntities(List<D> dtos) throws Exception;
}
