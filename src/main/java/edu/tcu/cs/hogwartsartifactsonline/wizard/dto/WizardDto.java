package edu.tcu.cs.hogwartsartifactsonline.wizard.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;

public record WizardDto(Integer id,
                        @NotEmpty(message = "name is required")
                        String name,
                        Integer numberOfArtifacts) {
}
