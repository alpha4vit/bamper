package by.gurinovich.bamper.services.sparePart;

import by.gurinovich.bamper.exceptions.InvalidOperationException;
import by.gurinovich.bamper.exceptions.ResourceNotFoundException;
import by.gurinovich.bamper.models.sparePartsEntities.SparePart;
import by.gurinovich.bamper.models.sparePartsEntities.SparePartName;
import by.gurinovich.bamper.repositories.sparePart.SparePartNameRepository;
import by.gurinovich.bamper.repositories.sparePart.SparePartRepository;
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

    @Mock  private SparePartRepository sparePartRepository;
    @Mock  private SparePartNameRepository sparePartNameRepository;

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
                .id(1L)
                .name(sparePartName)
                .number("213513312")
                .build();
        sparePartCreatingRequest = SparePartCreatingRequest.builder()
                .number("213513312")
                .build();
    }


    @Test
    void findAll() {
        Mockito.when(sparePartRepository.findAll()).thenReturn(List.of(new SparePart(), sparePart));
        org.assertj.core.api.Assertions.assertThat(sparePartService.findAll()).hasSize(2);
        Mockito.verify(sparePartRepository).findAll();
    }

    @Test
    void getById() {
        Mockito.when(sparePartRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(sparePart));
        Assertions.assertEquals(sparePart, sparePartService.getById(Mockito.anyLong()));
        Mockito.verify(sparePartRepository).findById(Mockito.anyLong());
    }

    @Test
    void throwExceptionWhenTryingToGetObjectWithNotExistingId(){
        Mockito.when(sparePartRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> sparePartService.getById(Mockito.anyLong()));
        Mockito.verify(sparePartRepository).findById(Mockito.anyLong());
    }

    @Test
    void deleteById() {
        Mockito.when(sparePartRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(sparePart));
        sparePartService.deleteById(Mockito.anyLong());
        Mockito.verify(sparePartRepository).findById(Mockito.anyLong());
        Mockito.verify(sparePartRepository).deleteById(Mockito.anyLong());
    }

    @Test
    void throwExceptionWhenTryingToDeleteObjectWithNotExistingId() {
        Mockito.when(sparePartRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> sparePartService.deleteById(Mockito.anyLong()));
        Mockito.verify(sparePartRepository).findById(Mockito.anyLong());
        Mockito.verify(sparePartRepository, Mockito.times(0)).deleteById(Mockito.anyLong());
    }

    @Test
    void saveWhenSparePartNameIdGivenAndSparePartNameNotGiven() {
        sparePartCreatingRequest.setName_id(1);
        Mockito.when(sparePartNameRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(sparePartName));
        Mockito.when(sparePartRepository.save(Mockito.any(SparePart.class))).thenReturn(sparePart);
        Assertions.assertEquals(sparePart, sparePartService.save(sparePartCreatingRequest));
        Mockito.verify(sparePartNameRepository).findById(Mockito.anyInt());
        Mockito.verify(sparePartNameRepository, Mockito.times(0)).findByName(Mockito.anyString());
        Mockito.verify(sparePartRepository).save(Mockito.any(SparePart.class));
    }


    @Test
    void saveWhenSparePartNameGivenAndSparePartNameIdNotGiven() {
        sparePartCreatingRequest.setName("Engine");
        Mockito.when(sparePartNameRepository.findByName(Mockito.anyString())).thenReturn(Optional.of(sparePartName));
        Mockito.when(sparePartRepository.save(Mockito.any(SparePart.class))).thenReturn(sparePart);
        Assertions.assertEquals(sparePart, sparePartService.save(sparePartCreatingRequest));
        Mockito.verify(sparePartNameRepository, Mockito.times(0)).findById(Mockito.anyInt());
        Mockito.verify(sparePartNameRepository).findByName(Mockito.anyString());
        Mockito.verify(sparePartRepository).save(Mockito.any(SparePart.class));
    }


    @Test
    void saveWhenSparePartNameNotGivenAndSparePartNameIdNotGiven() {
        Assertions.assertThrows(InvalidOperationException.class, () -> sparePartService.save(sparePartCreatingRequest));
        Mockito.verify(sparePartNameRepository, Mockito.times(0)).findById(Mockito.anyInt());
        Mockito.verify(sparePartNameRepository, Mockito.times(0)).findByName(Mockito.anyString());
        Mockito.verify(sparePartRepository, Mockito.times(0)).save(Mockito.any(SparePart.class));
    }
}