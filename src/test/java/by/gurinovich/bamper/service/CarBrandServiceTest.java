package by.gurinovich.bamper.service;


import by.gurinovich.bamper.config.TestConfig;
import by.gurinovich.bamper.exceptions.InvalidOperationException;
import by.gurinovich.bamper.models.carsEntities.CarBrand;
import by.gurinovich.bamper.repositories.car.CarBrandRepo;
import by.gurinovich.bamper.repositories.car.CarModelGenerationRepo;
import by.gurinovich.bamper.repositories.car.CarModelRepo;
import by.gurinovich.bamper.repositories.posts.PostRepo;
import by.gurinovich.bamper.repositories.sparePart.SparePartNameRepo;
import by.gurinovich.bamper.repositories.sparePart.SparePartRepo;
import by.gurinovich.bamper.repositories.user.UserRepo;
import by.gurinovich.bamper.services.car.CarBrandService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
class CarBrandServiceTest {

    @MockBean private CarBrandRepo carBrandRepo;
    @MockBean private AuthenticationManager authenticationManager;
    @MockBean private CarModelRepo carModelRepo;
    @MockBean private CarModelGenerationRepo carModelGenerationRepo;
    @MockBean private PostRepo postRepo;
    @MockBean private SparePartRepo sparePartRepo;
    @MockBean private SparePartNameRepo sparePartNameRepo;
    @MockBean private UserRepo userRepo;

    private CarBrandService carBrandService;

    @BeforeEach
    void setUp(){
        this.carBrandService = new CarBrandService(carBrandRepo);
    }

    @Test
    void getById() {
    }

    @Test
    void getAll() {

    }

    @Test
    void save() {
        CarBrand carBrand = new CarBrand();
        carBrand.setName("BMW");
        boolean res = carBrandService.save(carBrand);
        Assertions.assertTrue(res);
        Assertions.assertNotNull(carBrand.getName());
        Mockito.verify(carBrandRepo).save(carBrand);
    }

    @Test
    void throwErrorWhenTryigToSaveBrandWithExistingName() {
        CarBrand carBrand = new CarBrand();
        carBrand.setName("BMW");
        Mockito.doThrow(InvalidOperationException.class)
                        .when(carBrandRepo)
                                .save(carBrand);
        Mockito.verify(carBrandRepo, Mockito.times(0)).save(carBrand);
    }

    @Test
    void deleteById() {
        int id = 1;
        carBrandService.save(new CarBrand());
        carBrandService.deleteById(id);
        Mockito.verify(carBrandRepo).deleteById(id);
    }
}