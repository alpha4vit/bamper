package by.gurinovich.bamper.services.car;


import by.gurinovich.bamper.exceptions.InvalidOperationException;
import by.gurinovich.bamper.exceptions.ResourceNotFoundException;
import by.gurinovich.bamper.models.carsEntities.CarBrand;
import by.gurinovich.bamper.repositories.car.CarBrandRepo;
import by.gurinovich.bamper.services.car.CarBrandService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Random;


@ExtendWith(MockitoExtension.class)
class CarBrandServiceTest {

    @Mock
    private CarBrandRepo carBrandRepo;

    @InjectMocks
    private CarBrandService carBrandService;

    private CarBrand carBrand;

    @BeforeEach
    void setUp(){
        this.carBrand = CarBrand.builder().name("BMW").build();
    }

    @Test
    void save() {
        Mockito.when(carBrandRepo.save(Mockito.any(CarBrand.class))).thenReturn(carBrand);
        final boolean result = carBrandService.save(new CarBrand());
        Assertions.assertTrue(result);
        Mockito.verify(carBrandRepo).save(Mockito.any(CarBrand.class));
    }

    @Test
    void throwExceptionWhenTryigToSaveBrandWithExistingName() {
        Mockito.when(carBrandRepo.findByName(Mockito.anyString())).thenReturn(Optional.of(carBrand));
        Assertions.assertThrows(InvalidOperationException.class, () -> carBrandService.save(carBrand));
        Mockito.verify(carBrandRepo).findByName(Mockito.anyString());
        Mockito.verify(carBrandRepo, Mockito.times(0)).save(Mockito.any(CarBrand.class));
    }

    @Test
    void shouldReturnExistingCarBrandById(){
        Mockito.when(carBrandRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(carBrand));
        Assertions.assertEquals(carBrand, carBrandService.getById(Mockito.anyInt()));
        Mockito.verify(carBrandRepo).findById(Mockito.anyInt());
    }


    @Test
    void throwExceptionWhenTryingToGetCarBrandByNotExistingId(){
        Mockito.when(carBrandRepo.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> carBrandService.getById(Mockito.anyInt()));
        Mockito.verify(carBrandRepo).findById(Mockito.anyInt());
    }


    @Test
    void shouldDeleteById(){
        Mockito.doNothing().when(carBrandRepo).deleteById(Mockito.anyInt());
        Mockito.when(carBrandRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(carBrand));
        carBrandService.deleteById(Mockito.anyInt());
        Mockito.verify(carBrandRepo).findById(Mockito.anyInt());
        Mockito.verify(carBrandRepo).deleteById(Mockito.anyInt());
    }

    @Test
    void shouldThrowExceptionWhenTryingToDeleteCarBrandWithNotExistingId(){
        Mockito.when(carBrandRepo.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> carBrandService.deleteById(Mockito.anyInt()));
        Mockito.verify(carBrandRepo).findById(Mockito.anyInt());
        Mockito.verify(carBrandRepo, Mockito.times(0)).deleteById(Mockito.anyInt());
    }

    @Test
    void shouldReturnListOfCarBrands(){
        Mockito.when(carBrandRepo.findAll()).thenReturn(List.of(carBrand, new CarBrand()));
        org.assertj.core.api.Assertions.assertThat(carBrandService.getAll()).hasSize(2);
        Mockito.verify(carBrandRepo).findAll();
    }


}