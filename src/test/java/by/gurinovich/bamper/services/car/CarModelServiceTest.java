package by.gurinovich.bamper.services.car;

import by.gurinovich.bamper.DTO.car.CarModelDTO;
import by.gurinovich.bamper.exceptions.InvalidOperationException;
import by.gurinovich.bamper.exceptions.ResourceNotFoundException;
import by.gurinovich.bamper.models.carsEntities.CarBrand;
import by.gurinovich.bamper.models.carsEntities.CarModel;
import by.gurinovich.bamper.repositories.car.CarModelRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CarModelServiceTest {

    @Mock
    private CarModelRepo carModelRepo;

    @InjectMocks
    private CarModelService carModelService;

    private CarBrand carBrand;
    private CarModel carModel;
    private CarModelDTO carModelDTO;

    @BeforeEach
    void setUp(){
        carBrand = CarBrand.builder()
                .id(1)
                .name("Opel")
                .build();
        carModel = CarModel.builder()
                .id(1)
                .name("Astra")
                .carBrand(carBrand)
                .build();
        carModelDTO = CarModelDTO.builder()
                .id(1)
                .name("Astra")
                .build();

    }

    @Test
    void getAllModelForBrand() {
        Mockito.when(carModelRepo.findByCarBrand(Mockito.any(CarBrand.class))).thenReturn(List.of(carModel, new CarModel()));
        org.assertj.core.api.Assertions.assertThat(carModelService.getAllModelForBrand(carBrand)).hasSize(2);
        Mockito.verify(carModelRepo).findByCarBrand(Mockito.any(CarBrand.class));
    }

    @Test
    void getById() {
        Mockito.when(carModelRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(carModel));
        Assertions.assertEquals(carModel, carModelService.getById(Mockito.anyInt()));
        Mockito.verify(carModelRepo).findById(Mockito.anyInt());
    }

    @Test
    void throwExceptionWhenTryToGetCarModelWithNotExistingId(){
        Mockito.when(carModelRepo.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> carModelService.getById(Mockito.anyInt()));
        Mockito.verify(carModelRepo).findById(Mockito.anyInt());
    }

    @Test
    void deleteById() {
        Mockito.when(carModelRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(carModel));
        Mockito.doNothing().when(carModelRepo).deleteById(Mockito.anyInt());
        carModelService.deleteById(Mockito.anyInt());
        Mockito.verify(carModelRepo).findById(Mockito.anyInt());
        Mockito.verify(carModelRepo).deleteById(Mockito.anyInt());
    }

    @Test
    void throwExceptionWhenTryToDeleteCarModelWithNotExistingId(){
        Mockito.when(carModelRepo.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> carModelService.deleteById(Mockito.anyInt()));
        Mockito.verify(carModelRepo, Mockito.times(0)).deleteById(Mockito.anyInt());
    }

    @Test
    void save() {
        Mockito.when(carModelRepo.findByCarBrand(carBrand)).thenReturn(new ArrayList<>());
        Mockito.when(carModelRepo.save(Mockito.any(CarModel.class))).thenReturn(carModel);
        Assertions.assertTrue(carModelService.save(carBrand, carModelDTO));
        Mockito.verify(carModelRepo).save(Mockito.any(CarModel.class));

    }

    @Test
    void throwExceptionWhenTryingToSaveCarModelWithExistingNameForBrand() {
        Mockito.when(carModelRepo.findByCarBrand(carBrand)).thenReturn(List.of(carModel));
        Assertions.assertThrows(InvalidOperationException.class, () -> carModelService.save(carBrand, carModelDTO));
        Mockito.verify(carModelRepo, Mockito.times(0)).save(Mockito.any(CarModel.class));

    }
}