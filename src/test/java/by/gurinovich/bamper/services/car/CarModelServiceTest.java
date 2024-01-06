package by.gurinovich.bamper.services.car;

import by.gurinovich.bamper.DTO.car.CarModelDTO;
import by.gurinovich.bamper.exceptions.InvalidOperationException;
import by.gurinovich.bamper.exceptions.ResourceNotFoundException;
import by.gurinovich.bamper.models.carsEntities.CarBrand;
import by.gurinovich.bamper.models.carsEntities.CarModel;
import by.gurinovich.bamper.repositories.car.CarModelRepository;
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

@ExtendWith(MockitoExtension.class)
class CarModelServiceTest {

    @Mock
    private CarModelRepository carModelRepository;

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
        Mockito.when(carModelRepository.findByCarBrand(Mockito.any(CarBrand.class))).thenReturn(List.of(carModel, new CarModel()));
        org.assertj.core.api.Assertions.assertThat(carModelService.getAllModelForBrand(carBrand)).hasSize(2);
        Mockito.verify(carModelRepository).findByCarBrand(Mockito.any(CarBrand.class));
    }

    @Test
    void getById() {
        Mockito.when(carModelRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(carModel));
        Assertions.assertEquals(carModel, carModelService.getById(Mockito.anyInt()));
        Mockito.verify(carModelRepository).findById(Mockito.anyInt());
    }

    @Test
    void throwExceptionWhenTryToGetCarModelWithNotExistingId(){
        Mockito.when(carModelRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> carModelService.getById(Mockito.anyInt()));
        Mockito.verify(carModelRepository).findById(Mockito.anyInt());
    }

    @Test
    void deleteById() {
        Mockito.when(carModelRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(carModel));
        Mockito.doNothing().when(carModelRepository).deleteById(Mockito.anyInt());
        carModelService.deleteById(Mockito.anyInt());
        Mockito.verify(carModelRepository).findById(Mockito.anyInt());
        Mockito.verify(carModelRepository).deleteById(Mockito.anyInt());
    }

    @Test
    void throwExceptionWhenTryToDeleteCarModelWithNotExistingId(){
        Mockito.when(carModelRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> carModelService.deleteById(Mockito.anyInt()));
        Mockito.verify(carModelRepository, Mockito.times(0)).deleteById(Mockito.anyInt());
    }

    @Test
    void save() {
        Mockito.when(carModelRepository.findByCarBrand(carBrand)).thenReturn(new ArrayList<>());
        Mockito.when(carModelRepository.save(Mockito.any(CarModel.class))).thenReturn(carModel);
        Assertions.assertTrue(carModelService.save(carBrand, carModelDTO));
        Mockito.verify(carModelRepository).save(Mockito.any(CarModel.class));

    }

    @Test
    void throwExceptionWhenTryingToSaveCarModelWithExistingNameForBrand() {
        Mockito.when(carModelRepository.findByCarBrand(carBrand)).thenReturn(List.of(carModel));
        Assertions.assertThrows(InvalidOperationException.class, () -> carModelService.save(carBrand, carModelDTO));
        Mockito.verify(carModelRepository, Mockito.times(0)).save(Mockito.any(CarModel.class));

    }
}