package himafor_project.repository;

import himafor_project.model.ProfileSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository JPA untuk entitas ProfileSetting.
 */
@Repository
public interface ProfileSettingRepository extends JpaRepository<ProfileSetting, Long> {
}
