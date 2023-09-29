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
import by.gurinovich.bamper.utils.mappers.impl.car.CarBrandMapper;
import by.gurinovich.bamper.utils.mappers.impl.car.CarModelGenerationMapper;
import by.gurinovich.bamper.utils.mappers.impl.car.CarModelMapper;
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
public class CarController {

    private final CarBrandService carBrandService;
    private final CarModelService carModelService;
    private final CarModelGenerationService carModelGenetionService;
    private final CarBrandMapper carBrandMapper;
    private final CarModelMapper carModelMapper;
    private final CarModelGenerationMapper carModelGenerationMapper;


    @GetMapping("/brands/all")
    public ResponseEntity<List<CarBrandDTO>> getCarBrands(){
        return new ResponseEntity<>(carBrandMapper.toDTOs(carBrandService.getAll()), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/brands/{brand_id}")
    public ResponseEntity<Object> getCarBrand(@PathVariable("brand_id") Integer brand_id){
        return new ResponseEntity<>(carBrandMapper.toDTO(carBrandService.getById(brand_id)), HttpStatus.OK);
    }


    @PostMapping("/brands/add")
    public ResponseEntity<Object> addCarBrand(@RequestBody CarBrandDTO carBrandDTO){
        carBrandService.save(carBrandMapper.fromDTO(carBrandDTO));
        return new ResponseEntity<>(HttpStatusCode.valueOf(201));
    }

    @DeleteMapping("/brands/delete/{brand_id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBrand(@PathVariable("brand_id") Integer brand_id){
        carBrandService.deleteById(brand_id);
    }


    @GetMapping("/brands/models/{brand_id}")
    public ResponseEntity<Object> getModelForCarBrand(@PathVariable("brand_id") Integer brand_id){
        CarBrand carBrand = carBrandService.getById(brand_id);
        return  new ResponseEntity<>(carModelMapper.toDTOs(carModelService.getAllModelForBrand(carBrand)), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/models/{model_id}")
    public ResponseEntity<Object> getModel(@PathVariable("model_id") Integer model_id){
        CarModel carModel = carModelService.getById(model_id);
        return new ResponseEntity<>(carModelMapper.toDTO(carModel), HttpStatus.OK);
    }

    @PostMapping("/models/add/{brand_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addModelForCarBrand(@PathVariable("brand_id") Integer brand_id, @RequestBody CarModelDTO carModelDTO){
        carModelService.save(carBrandService.getById(brand_id), carModelDTO);
    }

    @DeleteMapping("/models/delete/{model_id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteModelForBrand(@PathVariable("model_id") Integer model_id){
        carModelService.deleteById(model_id);
    }

    @GetMapping("/models/{model_id}/generations")
    public ResponseEntity<Object> getModelGenerations(@PathVariable("model_id") Integer model_id){
        CarModel carModel = carModelService.getById(model_id);
        return new ResponseEntity<>(
                carModelGenerationMapper.toDTOs(carModelGenetionService.getAllGenerationsForModel(carModel)),
                HttpStatus.OK);
    }

    @GetMapping("/models/generations/{generation_id}")
    public ResponseEntity<Object> getModelGeneration(@PathVariable("generation_id") Integer generation_id){
        CarModelGeneration carModelGeneration = carModelGenetionService.getById(generation_id);
        return new ResponseEntity<>(carModelGenerationMapper.toDTO(carModelGeneration), HttpStatus.NOT_FOUND);
    }

    @PostMapping("/models/{model_id}/generations/add")
    public ResponseEntity<Object> addGenerationForCarModel(@PathVariable("model_id") Integer model_id, @RequestBody CarModelGenerationDTO carModelGenerationDTO) throws ParseException {
        CarModel carModel = carModelService.getById(model_id);
        CarModelGeneration carModelGeneration = carModelGenetionService.save(carModel, carModelGenerationDTO);
        return new ResponseEntity<>(carModelGenerationMapper.toDTO(carModelGeneration), HttpStatus.CREATED);
    }

    @DeleteMapping("/models/generations/delete/{generation_id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGeneration(@PathVariable("generation_id") Integer generation_id){
        carModelGenetionService.deleteById(generation_id);
    }
}
