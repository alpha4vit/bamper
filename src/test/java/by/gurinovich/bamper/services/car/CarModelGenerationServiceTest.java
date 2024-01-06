package by.gurinovich.bamper.services.car;

import by.gurinovich.bamper.DTO.car.CarModelGenerationDTO;
import by.gurinovich.bamper.exceptions.InvalidOperationException;
import by.gurinovich.bamper.exceptions.ResourceNotFoundException;
import by.gurinovich.bamper.models.carsEntities.CarBrand;
import by.gurinovich.bamper.models.carsEntities.CarModel;
import by.gurinovich.bamper.models.carsEntities.CarModelGeneration;
import by.gurinovich.bamper.repositories.car.CarModelGenerationRepository;
import by.gurinovich.bamper.utils.mappers.impl.car.CarModelGenerationMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class CarModelGenerationServiceTest {
    @Mock
    private CarModelGenerationRepository carModelGenerationRepository;

    @InjectMocks
    private CarModelGenerationService carModelGenerationService;
    private final CarModelGenerationMapper carModelGenerationMapper = new CarModelGenerationMapper();

    private CarModelGeneration carModelGeneration;
    private CarModel carModel;
    private CarModelGenerationDTO carModelGenerationDTO;

    @BeforeEach
    void setUp(){
        carModelGeneration = CarModelGeneration.builder()
                .id(1)
                .carModel(new CarModel())
                .name("3 series")
                .endYearOfProduction(new Date("Sat Dec 01 00:00:00 GMT 2012"))
                .startYearOfProduction(new Date("Sat Dec 01 00:00:00 GMT 2012"))
                .build();
        carModel = CarModel.builder()
                .id(1)
                .carBrand(new CarBrand())
                .name("Opel")
                .build();
        carModelGenerationDTO = CarModelGenerationDTO.builder()
                .name("3 series")
                .startYearOfProduction("2012-01")
                .endYearOfProduction("2012-01")
                .build();
    }

    @Test
    @SneakyThrows
    void save() {
        Mockito.when(carModelGenerationRepository.findAllByCarModel(carModel)).thenReturn(new ArrayList<>());
        Mockito.when(carModelGenerationRepository.save(Mockito.any(CarModelGeneration.class))).thenReturn(carModelGeneration);
        Assertions.assertEquals(carModelGeneration, carModelGenerationService.save(carModel, carModelGenerationDTO));
        Mockito.verify(carModelGenerationRepository).findAllByCarModel(Mockito.any(CarModel.class));
        Mockito.verify(carModelGenerationRepository).save(Mockito.any(CarModelGeneration.class));
    }

    @SneakyThrows
    @Test
    void throwExceptionWhenTryigToSaveGenerationWithExistingNameForOneBrand() {
        Mockito.when(carModelGenerationRepository.findAllByCarModel(carModel)).thenReturn(List.of(carModelGeneration));
        Assertions.assertThrows(InvalidOperationException.class, () -> carModelGenerationService.save(carModel, carModelGenerationDTO));
        Mockito.verify(carModelGenerationRepository).findAllByCarModel(Mockito.any(CarModel.class));
        Mockito.verify(carModelGenerationRepository, Mockito.times(0)).save(Mockito.any(CarModelGeneration.class));
    }

    @Test
    void shouldReturnExistingCarModelGenerationById(){
        Mockito.when(carModelGenerationRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(carModelGeneration));
        Assertions.assertEquals(carModelGeneration, carModelGenerationService.getById(Mockito.anyInt()));
        Mockito.verify(carModelGenerationRepository).findById(Mockito.anyInt());
    }


    @Test
    void throwExceptionWhenTryingToGetcarModelGenerationByNotExistingId(){
        Mockito.when(carModelGenerationRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> carModelGenerationService.getById(Mockito.anyInt()));
        Mockito.verify(carModelGenerationRepository).findById(Mockito.anyInt());
    }


    @Test
    void shouldDeleteById(){
        Mockito.doNothing().when(carModelGenerationRepository).deleteById(Mockito.anyInt());
        Mockito.when(carModelGenerationRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(carModelGeneration));
        carModelGenerationService.deleteById(Mockito.anyInt());
        Mockito.verify(carModelGenerationRepository).findById(Mockito.anyInt());
        Mockito.verify(carModelGenerationRepository).deleteById(Mockito.anyInt());
    }

    @Test
    void shouldThrowExceptionWhenTryingToDeleteCarModelGenerationWithNotExistingId(){
        Mockito.when(carModelGenerationRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> carModelGenerationService.deleteById(Mockito.anyInt()));
        Mockito.verify(carModelGenerationRepository).findById(Mockito.anyInt());
        Mockito.verify(carModelGenerationRepository, Mockito.times(0)).deleteById(Mockito.anyInt());
    }


    @Test
    void getAllGenerationsForModel() {
        Mockito.when(carModelGenerationRepository.findAllByCarModel(Mockito.any(CarModel.class))).thenReturn(List.of(carModelGeneration, new CarModelGeneration()));
        org.assertj.core.api.Assertions.assertThat(carModelGenerationRepository.findAllByCarModel(carModel)).hasSize(2);
        Mockito.verify(carModelGenerationRepository).findAllByCarModel(Mockito.any(CarModel.class));
    }
}