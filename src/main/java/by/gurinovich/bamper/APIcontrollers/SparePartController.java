package by.gurinovich.bamper.APIcontrollers;

import by.gurinovich.bamper.DTO.spareParts.SparePartNameDTO;
import by.gurinovich.bamper.models.sparePartsEntities.SparePart;
import by.gurinovich.bamper.models.sparePartsEntities.SparePartName;
import by.gurinovich.bamper.requests.SparePartCreatingRequest;
import by.gurinovich.bamper.services.sparePart.SparePartNameService;
import by.gurinovich.bamper.services.sparePart.SparePartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/spareParts")
@RequiredArgsConstructor
@Tag(name = "Spare part controller", description = "Spare part API")
public class SparePartController {

    private final SparePartNameService sparePartNameService;
    private final SparePartService sparePartService;

    @Operation(summary = "Get all spare parts names")
    @GetMapping("/names/all")
    public ResponseEntity<Object> getAllSparePartsName(){
        return new ResponseEntity<>(sparePartNameService.getAllSparePartsName().stream().map(SparePartNameService::convertToDTO).toList(), HttpStatus.OK);
    }

    @Operation(summary = "Get spare part name by id")
    @GetMapping("/names/{name_id}")
    public ResponseEntity<Object> getSparePartName(@PathVariable("name_id") Integer name_id){
        SparePartName sparePartName = sparePartNameService.getById(name_id);
        if (sparePartName == null)
            return new ResponseEntity<>("SparePartName with this id not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(SparePartNameService.convertToDTO(sparePartName), HttpStatus.OK);
    }

    @Operation(summary = "Add spare part name")
    @PostMapping("/names/add")
    public ResponseEntity<Object> addSparePartName(@RequestBody SparePartNameDTO sparePartNameDTO){
        sparePartNameService.save(sparePartNameDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Delete spare part name by id")
    @DeleteMapping("/names/delete/{spare_part_name_id}")
    public ResponseEntity<Object> deleteSparePartName(@PathVariable("spare_part_name_id") Integer sparePartName_id){
        SparePartName sparePartName = sparePartNameService.getById(sparePartName_id);
        if (sparePartName == null)
            return new ResponseEntity<>("SparePartName with this id not found", HttpStatus.NOT_FOUND);
        sparePartNameService.delete(sparePartName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Get all spare parts")
    @GetMapping("/all")
    public ResponseEntity<Object> getAllSpareParts(){
        return new ResponseEntity<>(sparePartService.findAll().stream().map(SparePartService::converToDTO).toList(), HttpStatus.OK);
    }

    @Operation(summary = "Get spare part by id")
    @GetMapping("/{spare_part_id}")
    public ResponseEntity<Object> getById(@PathVariable("spare_part_id") Integer sparePartId){
        SparePart sparePart = sparePartService.getById(sparePartId);
        if (sparePart == null)
            return new ResponseEntity<>("SparePart with this id or name not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(SparePartService.converToDTO(sparePart), HttpStatus.OK);
    }

    @Operation(summary = "Add new spare part")
    @PostMapping("/add")
    public ResponseEntity<Object> addNewSparePart(@RequestBody SparePartCreatingRequest sparePartCreatingRequest){
        SparePart newSparePart = sparePartService.save(sparePartCreatingRequest);
        if ( newSparePart == null)
            return new ResponseEntity<>("SparePartName with this id or name not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(SparePartService.converToDTO(newSparePart),HttpStatus.CREATED);
    }

    @Operation(summary = "Delete spare part")
    @DeleteMapping("/delete/{spare_part_id}")
    public ResponseEntity<Object> deleteSparePart(@PathVariable("spare_part_id") Integer spare_part_id){
        if (sparePartService.deleteById(spare_part_id))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>("SparePart with this id not found", HttpStatus.NOT_FOUND);
    }

}
