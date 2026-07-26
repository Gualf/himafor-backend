package himafor_project.service;

import himafor_project.dto.DashboardResponse;
import himafor_project.repository.EventRepository;
import himafor_project.repository.MemberRepository;
import himafor_project.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service untuk menyusun ringkasan statistik Dashboard.
 */
@Service
@RequiredArgsConstructor
public class DashboardService {

    private final MemberRepository memberRepository;
    private final NewsRepository newsRepository;
    private final EventRepository eventRepository;

    public DashboardResponse getDashboardStats() {
        return DashboardResponse.builder()
                .totalMembers(memberRepository.count())
                .totalNews(newsRepository.count())
                .totalEvents(eventRepository.count())
                .build();
    }
}
