package edu.tcu.cs.hogwartsartifactsonline.artifact;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ArtifactService {

    private final ArtifcactRepository artifcactRepository;

    public ArtifactService(ArtifcactRepository artifcactRepository) {
        this.artifcactRepository = artifcactRepository;
    }

    public Artifact findById(String artifactId){
        return this.artifcactRepository.findById(artifactId)
                .orElseThrow(() -> new ArtifactNotFoundException(artifactId));
    }

}
