package edu.tcu.cs.hogwartsartifactsonline.artifact;

import edu.tcu.cs.hogwartsartifactsonline.wizard.Wizard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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
    @InjectMocks
    ArtifactService artifactService;

    @BeforeEach
    void setUp() {
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


}