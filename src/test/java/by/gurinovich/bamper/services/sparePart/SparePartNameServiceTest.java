package by.gurinovich.bamper.services.sparePart;

import by.gurinovich.bamper.DTO.spareParts.SparePartNameDTO;
import by.gurinovich.bamper.exceptions.InvalidOperationException;
import by.gurinovich.bamper.exceptions.ResourceNotFoundException;
import by.gurinovich.bamper.models.sparePartsEntities.SparePartName;
import by.gurinovich.bamper.repositories.sparePart.SparePartNameRepo;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SparePartNameServiceTest {

    @Mock
    private SparePartNameRepo sparePartNameRepo;

    @InjectMocks
    private SparePartNameService sparePartNameService;

    private SparePartName sparePartName;
    private SparePartNameDTO sparePartNameDTO;

    @BeforeEach
    void setUp(){
        sparePartName = SparePartName.builder()
                .id(1)
                .name("Engine")
                .build();
        sparePartNameDTO = SparePartNameDTO.builder()
                .id(1)
                .name("Engine")
                .build();
    }

    @Test
    void getById() {
        Mockito.when(sparePartNameRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(sparePartName));
        Assertions.assertEquals(sparePartName, sparePartNameService.getById(Mockito.anyInt()));
        Mockito.verify(sparePartNameRepo).findById(Mockito.anyInt());
    }

    @Test
    void throwExceptionWhenTryingToGetObjectWithNotExistingId(){
        Mockito.when(sparePartNameRepo.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> sparePartNameService.getById(Mockito.anyInt()));
        Mockito.verify(sparePartNameRepo).findById(Mockito.anyInt());
    }

    @Test
    void deleteById() {
        Mockito.when(sparePartNameRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(sparePartName));
        sparePartNameService.deleteById(Mockito.anyInt());
        Mockito.verify(sparePartNameRepo).findById(Mockito.anyInt());
        Mockito.verify(sparePartNameRepo).deleteById(Mockito.anyInt());
    }

    @Test
    void throwExceptionWhenTryingToDeleteObjectWithNotExistingId(){
        Mockito.when(sparePartNameRepo.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> sparePartNameService.deleteById(Mockito.anyInt()));
        Mockito.verify(sparePartNameRepo).findById(Mockito.anyInt());
        Mockito.verify(sparePartNameRepo, Mockito.times(0)).deleteById(Mockito.anyInt());
    }

    @Test
    void save() {
        Mockito.when(sparePartNameRepo.findByName(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(sparePartNameRepo.save(Mockito.any(SparePartName.class))).thenReturn(sparePartName);
        Assertions.assertEquals(sparePartName, sparePartNameService.save(sparePartNameDTO));
        Mockito.verify(sparePartNameRepo).findByName(Mockito.anyString());
        Mockito.verify(sparePartNameRepo).save(Mockito.any(SparePartName.class));
    }

    @Test
    void throwExceptionWhenTryingToSaveObjectWithExistingName(){
        Mockito.when(sparePartNameRepo.findByName(Mockito.anyString())).thenReturn(Optional.of(sparePartName));
        Assertions.assertThrows(InvalidOperationException.class, () -> sparePartNameService.save(sparePartNameDTO));
        Mockito.verify(sparePartNameRepo).findByName(Mockito.anyString());
        Mockito.verify(sparePartNameRepo, Mockito.times(0)).save(Mockito.any(SparePartName.class));
    }

    @Test
    void getAllSparePartsName() {
        Mockito.when(sparePartNameRepo.findAll()).thenReturn(List.of(sparePartName, new SparePartName()));
        org.assertj.core.api.Assertions.assertThat(sparePartNameService.getAllSparePartsName()).hasSize(2);
        Mockito.verify(sparePartNameRepo).findAll();
    }
}