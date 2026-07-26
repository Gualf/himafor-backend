package himafor_project.repository;

import himafor_project.model.Upload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository JPA untuk entitas Upload.
 */
@Repository
public interface UploadRepository extends JpaRepository<Upload, Long> {
}
