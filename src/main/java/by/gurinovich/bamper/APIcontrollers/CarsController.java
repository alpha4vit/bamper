package by.gurinovich.bamper.APIcontrollers;

import by.gurinovich.bamper.DTO.car.CarBrandDTO;
import by.gurinovich.bamper.DTO.car.CarModelDTO;
import by.gurinovich.bamper.DTO.car.CarModelGenerationDTO;
import by.gurinovich.bamper.models.carsEntities.CarBrand;
import by.gurinovich.bamper.models.carsEntities.CarModel;
import by.gurinovich.bamper.models.carsEntities.CarModelGeneration;
import by.gurinovich.bamper.services.car.CarBrandService;
import by.gurinovich.bamper.services.car.CarModelGenerationService;
import by.gurinovich.bamper.services.car.CarModelService;
import by.gurinovich.bamper.utils.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/car", produces = "application/json")
@RequiredArgsConstructor
public class CarsController {

    private final CarBrandService carBrandService;
    private final CarModelService carModelService;
    private final CarModelGenerationService carModelGenetionService;



    @GetMapping("/brands/all")
    public ResponseEntity<List<CarBrandDTO>> getCarBrands(){
        List<CarBrandDTO> brandDTOs = carBrandService.getAll().stream().map(CarBrandService::convertToDTO).toList();
        return new ResponseEntity<>(brandDTOs, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/brands/{brand_id}")
    public ResponseEntity<Object> getCarBrand(@PathVariable("brand_id") Integer brand_id){
        return new ResponseEntity<>(CarBrandService.convertToDTO(carBrandService.getById(brand_id)), HttpStatus.OK);
    }


    @PostMapping("/brands/add")
    public ResponseEntity<Object> addCarBrand(@RequestBody @JsonView(Views.Internal.class) CarBrand carBrand){
        carBrandService.save(carBrand);
        return new ResponseEntity<>(HttpStatusCode.valueOf(201));
        //TODO create validation for adding carBrand with the existed name
    }

    @DeleteMapping("/brands/delete/{brand_id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBrand(@PathVariable("brand_id") Integer brand_id){
        carBrandService.deleteById(brand_id);
    }


    @GetMapping("/models/getModels/{brand_id}")
    public ResponseEntity<Object> getModelForCarBrand(@PathVariable("brand_id") Integer brand_id){
        CarBrand carBrand = carBrandService.getById(brand_id);
        return  new ResponseEntity<>(carModelService.getAllModelForBrand(carBrand).stream().map(CarModelService::convertToDTO).toList(), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/models/getModel/{model_id}")
    public ResponseEntity<Object> getModel(@PathVariable("model_id") Integer model_id){
        CarModel carModel = carModelService.getById(model_id);
        return new ResponseEntity<>(CarModelService.convertToDTO(carModel), HttpStatus.OK);
    }

    @PostMapping("/models/addModel/{brand_id}")
    public ResponseEntity<Object> addModelForCarBrand(@PathVariable("brand_id") Integer brand_id, @RequestBody CarModelDTO carModelDTO){
        CarBrand carBrand = carBrandService.getById(brand_id);
        if (carBrand == null)
            return new ResponseEntity<>("CarBrand with this id not found", HttpStatus.NOT_FOUND);
        carModelService.save(carBrand, carModelDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/models/deleteModel/{model_id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteModelForBrand(@PathVariable("model_id") Integer model_id){
        carModelService.deleteById(model_id);
    }

    @GetMapping("/models/getGenerations/{model_id}")
    public ResponseEntity<Object> getModelGenerations(@PathVariable("model_id") Integer model_id){
        CarModel carModel = carModelService.getById(model_id);
        return new ResponseEntity<>(carModelGenetionService.getAllGenerationsForModel(carModel).stream().map(CarModelGenerationService::convertToDTO).toList(), HttpStatus.OK);
    }

    @GetMapping("/models/getGeneration/{generation_id}")
    public ResponseEntity<Object> getModelGeneration(@PathVariable("generation_id") Integer generation_id){
        CarModelGeneration carModelGeneration = carModelGenetionService.getById(generation_id);
        return new ResponseEntity<>(CarModelGenerationService.convertToDTO(carModelGeneration), HttpStatus.NOT_FOUND);
    }

    @PostMapping("/models/addGeneration/{model_id}")
    public ResponseEntity<Object> addGenerationForCarModel(@PathVariable("model_id") Integer model_id, @RequestBody CarModelGenerationDTO carModelGenerationDTO) throws ParseException {
        CarModel carModel = carModelService.getById(model_id);
        CarModelGeneration carModelGeneration = carModelGenetionService.save(carModel, carModelGenerationDTO);
        return new ResponseEntity<>(CarModelGenerationService.convertToDTO(carModelGeneration), HttpStatus.CREATED);

    }

    @DeleteMapping("/models/deleteGeneration/{generation_id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGeneration(@PathVariable("generation_id") Integer generation_id){
        carModelGenetionService.deleteById(generation_id);
    }
}