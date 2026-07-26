package himafor_project.repository;

import himafor_project.model.HomeSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository JPA untuk entitas HomeSetting.
 */
@Repository
public interface HomeSettingRepository extends JpaRepository<HomeSetting, Long> {
}
