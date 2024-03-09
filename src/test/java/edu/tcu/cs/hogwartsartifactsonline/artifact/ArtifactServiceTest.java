package edu.tcu.cs.hogwartsartifactsonline.artifact;

import edu.tcu.cs.hogwartsartifactsonline.artifact.utils.IdWorker;
import edu.tcu.cs.hogwartsartifactsonline.wizard.Wizard;
import org.junit.jupiter.api.AfterEach;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ArtifactServiceTest {
    @Mock
    ArtifcactRepository artifcactRepository;

    @Mock
    IdWorker idWorker;

    @InjectMocks
    ArtifactService artifactService;

    List<Artifact> artifacts;

    @BeforeEach
    void setUp() {
        Artifact a1 = new Artifact();
        a1.setId("1250808601744904191");
        a1.setName("Deluminator");
        a1.setDescription("A Deluminator is a device invented by Albus Dumbledore that resembles a cigarette lighter. It is used to remove or absorb (as well as return) the light from any light source to provide cover to the user.");
        a1.setImageURL("ImageUrl");

        Artifact a2 = new Artifact();
        a2.setId("1250808601744904192");
        a2.setName("Invisibility Cloak");
        a2.setDescription("An invisibility cloak is used to make the wearer invisible.");
        a2.setImageURL("ImageUrl");

        this.artifacts = new ArrayList<>();
        this.artifacts.add(a1);
        this.artifacts.add(a2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindByIdSuccess() {
        // Given. Arrange inputs and targets. Define the behavior of Mock object artifactRepository
//        "id": "1250808601744904192",
//        "name": "Invisibility Cloak",
//        "description": "An invisibility cloak is used to make the wearer invisible.",
//        "imageUrl": "ImageUrl",
        Artifact a = new Artifact();
        a.setId("1250808601744904192");
        a.setName("Invisibility Cloak");
        a.setDescription("An invisibility cloak is used to make the wearer invisible.");
        a.setImageURL("ImageURL");

        Wizard w = new Wizard();
        w.setId(2);
        w.setName("Harry Potter");
        a.setOwner(w);

        given(artifcactRepository.findById("1250808601744904192")).willReturn(Optional.of(a)); // Defines the behavior of the mock object

        // When. Act on the behavior. When steps should cover the method to be tested.
        Artifact returnArtifact =  artifactService.findById("1250808601744904192");

        // Then. Assert exepcted outcomes.
        assertThat(returnArtifact.getId()).isEqualTo(a.getId());
        assertThat(returnArtifact.getName()).isEqualTo(a.getName());
        assertThat(returnArtifact.getDescription()).isEqualTo(a.getDescription());
        assertThat(returnArtifact.getImageURL()).isEqualTo(a.getImageURL());

        verify(artifcactRepository, times(1)).findById("1250808601744904192");
    }

    @Test void testFindIdNotFound(){
        // Given
        given(artifcactRepository.findById(Mockito.any(String.class))).willReturn(Optional.empty());

        // When
        Throwable thrown = catchThrowable(()-> {
            Artifact returnArtifact =  artifactService.findById("1250808601744904192");
        });

        // Then
        assertThat(thrown)
                .isInstanceOf(ArtifactNotFoundException.class)
                .hasMessage("Could not find artifact with Id 1250808601744904192 :(");
        verify(artifcactRepository, times(1)).findById("1250808601744904192");
    }

    @Test
    void testFindAllSuccess(){
        // Given
        given(artifcactRepository.findAll()).willReturn(this.artifacts);

        // When
        List<Artifact> actualArtifacts = artifactService.findAll();

        // Then
        assertThat(actualArtifacts.size()).isEqualTo(this.artifacts.size());
        verify(artifcactRepository, times(1)).findAll();

    }

    @Test
    void testSaveSuccess(){
        // Given
        Artifact newArtifact = new Artifact();
        newArtifact.setName("Artifact 3");
        newArtifact.setDescription("Description...");
        newArtifact.setImageURL("ImageUrl...");

        given(idWorker.nextId()).willReturn(123456L);
        given(artifcactRepository.save(newArtifact)).willReturn(newArtifact);

        // When
        Artifact savedArtifact = artifactService.save(newArtifact);

        // Then
        assertThat(savedArtifact.getId()).isEqualTo("123456");
        assertThat(savedArtifact.getName()).isEqualTo(newArtifact.getName());
        assertThat(savedArtifact.getDescription()).isEqualTo(newArtifact.getDescription());
        assertThat(savedArtifact.getImageURL()).isEqualTo(newArtifact.getImageURL());
        verify(artifcactRepository, times(1)).save(newArtifact);

    }

    @Test
    void testUpdateSuccess() {
        Artifact oldArtifact = new Artifact();
        oldArtifact.setId("1250808601744904192");
        oldArtifact.setName("Invisibility Cloak");
        oldArtifact.setDescription("An invisibility cloak is used to make the wearer invisible.");
        oldArtifact.setImageURL("ImageUrl");

        Artifact update = new Artifact();
        update.setId("1250808601744904192");
        update.setName("Invisibility Cloak");
        update.setDescription("A new description");
        update.setImageURL("ImageUrl");

        given(artifcactRepository.findById("1250808601744904192")).willReturn(Optional.of(oldArtifact));
        given(artifcactRepository.save(oldArtifact)).willReturn(oldArtifact);

        // When
        Artifact updatedArtifact = artifactService.update("1250808601744904192", update);

        // Then
        assertThat(updatedArtifact.getId()).isEqualTo(updatedArtifact.getId());
        assertThat(updatedArtifact.getDescription()).isEqualTo(updatedArtifact.getDescription());

        verify(artifcactRepository, times(1)).findById("1250808601744904192");
        verify(artifcactRepository, times(1)).save(oldArtifact);
    }

    @Test
    void testUpdateNotFound(){
        // Given
        Artifact update = new Artifact();
        update.setName("Invisibility Cloak");
        update.setDescription("A new description.");
        update.setImageURL("ImageUrl");

        given(artifcactRepository.findById("1250808601744904192")).willReturn(Optional.empty());

        // When
        assertThrows(ArtifactNotFoundException.class, () -> {
            this.artifactService.update("1250808601744904192", update);
        });

        // Then
        verify(this.artifcactRepository, times(1)).findById("1250808601744904192");
    }


}