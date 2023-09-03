package by.gurinovich.bamper.services.sparePart;

import by.gurinovich.bamper.DTO.car.SparePartNameDTO;
import by.gurinovich.bamper.models.sparePartsEntities.SparePartName;
import org.springframework.stereotype.Service;

@Service
public class SparePartNameService {

    public static SparePartNameDTO convertToDTO(SparePartName sparePartName){
        return new SparePartNameDTO(sparePartName.getId(), sparePartName.getName());
    }
}
