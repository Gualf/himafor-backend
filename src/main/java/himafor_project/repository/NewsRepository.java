package himafor_project.repository;

import himafor_project.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository JPA untuk entitas News.
 */
@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    Optional<News> findBySlug(String slug);
    Boolean existsBySlug(String slug);
    Page<News> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    List<News> findTop5ByOrderByCreatedAtDesc();
}
