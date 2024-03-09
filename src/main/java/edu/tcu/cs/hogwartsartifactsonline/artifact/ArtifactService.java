package edu.tcu.cs.hogwartsartifactsonline.artifact;

import edu.tcu.cs.hogwartsartifactsonline.artifact.dto.ArtifactDto;
import edu.tcu.cs.hogwartsartifactsonline.artifact.utils.IdWorker;
import jakarta.persistence.Id;
import jakarta.transaction.Transactional;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ArtifactService {

    private final ArtifcactRepository artifcactRepository;

    public ArtifactService(ArtifcactRepository artifcactRepository, IdWorker idWorker) {
        this.artifcactRepository = artifcactRepository;
        this.idWorker = idWorker;
    }

    private final IdWorker idWorker;


    public Artifact findById(String artifactId){
        return this.artifcactRepository.findById(artifactId)
                .orElseThrow(() -> new ArtifactNotFoundException(artifactId));
    }

    public List<Artifact> findAll(){
        return this.artifcactRepository.findAll();
    }

    public Artifact save(Artifact newArtifact){
        newArtifact.setId(idWorker.nextId() + "");
        return this.artifcactRepository.save(newArtifact);
    }

    public Artifact update(String artifactid, Artifact update){
        return this.artifcactRepository.findById(artifactid)
                .map(oldArtifact -> {
                    oldArtifact.setName(update.getName());
                    oldArtifact.setDescription(update.getDescription());
                    oldArtifact.setImageURL(update.getImageURL());
                    return this.artifcactRepository.save(oldArtifact);
                })
                .orElseThrow(() -> new ArtifactNotFoundException(artifactid));

    }

}
