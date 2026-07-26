package himafor_project.repository;

import himafor_project.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository JPA untuk entitas Member.
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Page<Member> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
