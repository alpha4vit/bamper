package by.gurinovich.bamper.services.sparePart;

import by.gurinovich.bamper.exceptions.InvalidOperationException;
import by.gurinovich.bamper.exceptions.ResourceNotFoundException;
import by.gurinovich.bamper.models.sparePartsEntities.SparePart;
import by.gurinovich.bamper.models.sparePartsEntities.SparePartName;
import by.gurinovich.bamper.repositories.sparePart.SparePartNameRepo;
import by.gurinovich.bamper.repositories.sparePart.SparePartRepo;
import by.gurinovich.bamper.requests.SparePartCreatingRequest;
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

@ExtendWith(MockitoExtension.class)
class SparePartServiceTest {

    @Mock  private SparePartRepo sparePartRepo;
    @Mock  private SparePartNameRepo sparePartNameRepo;

    @InjectMocks
    private SparePartService sparePartService;

    private SparePartName sparePartName;
    private SparePart sparePart;
    private SparePartCreatingRequest sparePartCreatingRequest;

    @BeforeEach
    void setUp(){
        sparePartName = SparePartName.builder()
                .id(1)
                .name("Engine")
                .build();
        sparePart = SparePart.builder()
                .id(1)
                .name(sparePartName)
                .number("213513312")
                .build();
        sparePartCreatingRequest = SparePartCreatingRequest.builder()
                .number("213513312")
                .build();
    }


    @Test
    void findAll() {
        Mockito.when(sparePartRepo.findAll()).thenReturn(List.of(new SparePart(), sparePart));
        org.assertj.core.api.Assertions.assertThat(sparePartService.findAll()).hasSize(2);
        Mockito.verify(sparePartRepo).findAll();
    }

    @Test
    void getById() {
        Mockito.when(sparePartRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(sparePart));
        Assertions.assertEquals(sparePart, sparePartService.getById(Mockito.anyInt()));
        Mockito.verify(sparePartRepo).findById(Mockito.anyInt());
    }

    @Test
    void throwExceptionWhenTryingToGetObjectWithNotExistingId(){
        Mockito.when(sparePartRepo.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> sparePartService.getById(Mockito.anyInt()));
        Mockito.verify(sparePartRepo).findById(Mockito.anyInt());
    }

    @Test
    void deleteById() {
        Mockito.when(sparePartRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(sparePart));
        sparePartService.deleteById(Mockito.anyInt());
        Mockito.verify(sparePartRepo).findById(Mockito.anyInt());
        Mockito.verify(sparePartRepo).deleteById(Mockito.anyInt());
    }

    @Test
    void throwExceptionWhenTryingToDeleteObjectWithNotExistingId() {
        Mockito.when(sparePartRepo.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> sparePartService.deleteById(Mockito.anyInt()));
        Mockito.verify(sparePartRepo).findById(Mockito.anyInt());
        Mockito.verify(sparePartRepo, Mockito.times(0)).deleteById(Mockito.anyInt());
    }

    @Test
    void saveWhenSparePartNameIdGivenAndSparePartNameNotGiven() {
        sparePartCreatingRequest.setName_id(1);
        Mockito.when(sparePartNameRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(sparePartName));
        Mockito.when(sparePartRepo.save(Mockito.any(SparePart.class))).thenReturn(sparePart);
        Assertions.assertEquals(sparePart, sparePartService.save(sparePartCreatingRequest));
        Mockito.verify(sparePartNameRepo).findById(Mockito.anyInt());
        Mockito.verify(sparePartNameRepo, Mockito.times(0)).findByName(Mockito.anyString());
        Mockito.verify(sparePartRepo).save(Mockito.any(SparePart.class));
    }


    @Test
    void saveWhenSparePartNameGivenAndSparePartNameIdNotGiven() {
        sparePartCreatingRequest.setName("Engine");
        Mockito.when(sparePartNameRepo.findByName(Mockito.anyString())).thenReturn(Optional.of(sparePartName));
        Mockito.when(sparePartRepo.save(Mockito.any(SparePart.class))).thenReturn(sparePart);
        Assertions.assertEquals(sparePart, sparePartService.save(sparePartCreatingRequest));
        Mockito.verify(sparePartNameRepo, Mockito.times(0)).findById(Mockito.anyInt());
        Mockito.verify(sparePartNameRepo).findByName(Mockito.anyString());
        Mockito.verify(sparePartRepo).save(Mockito.any(SparePart.class));
    }


    @Test
    void saveWhenSparePartNameNotGivenAndSparePartNameIdNotGiven() {
        Assertions.assertThrows(InvalidOperationException.class, () -> sparePartService.save(sparePartCreatingRequest));
        Mockito.verify(sparePartNameRepo, Mockito.times(0)).findById(Mockito.anyInt());
        Mockito.verify(sparePartNameRepo, Mockito.times(0)).findByName(Mockito.anyString());
        Mockito.verify(sparePartRepo, Mockito.times(0)).save(Mockito.any(SparePart.class));
    }
}