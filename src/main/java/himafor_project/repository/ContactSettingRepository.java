package himafor_project.repository;

import himafor_project.model.ContactSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository JPA untuk entitas ContactSetting.
 */
@Repository
public interface ContactSettingRepository extends JpaRepository<ContactSetting, Long> {
}
