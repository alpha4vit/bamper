package by.gurinovich.bamper.services.sparePart;

import by.gurinovich.bamper.DTO.car.SparePartDTO;
import by.gurinovich.bamper.models.sparePartsEntities.SparePart;
import org.springframework.stereotype.Service;

@Service
public class SparePartService {
    public static SparePartDTO converToDTO(SparePart sparePart){
        return new SparePartDTO(sparePart.getId(),
                SparePartNameService.convertToDTO(sparePart.getName()), sparePart.getNumber());
    }
}
