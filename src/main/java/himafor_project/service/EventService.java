package himafor_project.service;

import himafor_project.dto.EventRequest;
import himafor_project.dto.EventResponse;
import himafor_project.exception.ResourceNotFoundException;
import himafor_project.model.Event;
import himafor_project.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service untuk manajemen CRUD data Kegiatan / Event.
 */
@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public List<EventResponse> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public EventResponse getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kegiatan dengan ID " + id + " tidak ditemukan"));
        return mapToResponse(event);
    }

    public EventResponse createEvent(EventRequest request) {
        Event event = Event.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .date(request.getDate())
                .location(request.getLocation())
                .status(request.getStatus() != null ? request.getStatus() : "Upcoming")
                .build();

        Event savedEvent = eventRepository.save(event);
        return mapToResponse(savedEvent);
    }

    public EventResponse updateEvent(Long id, EventRequest request) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kegiatan dengan ID " + id + " tidak ditemukan"));

        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setDate(request.getDate());
        event.setLocation(request.getLocation());
        if (request.getStatus() != null) {
            event.setStatus(request.getStatus());
        }

        Event updatedEvent = eventRepository.save(event);
        return mapToResponse(updatedEvent);
    }

    public void deleteEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kegiatan dengan ID " + id + " tidak ditemukan"));
        eventRepository.delete(event);
    }

    public EventResponse mapToResponse(Event event) {
        return EventResponse.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .date(event.getDate())
                .location(event.getLocation())
                .status(event.getStatus())
                .createdAt(event.getCreatedAt())
                .build();
    }
}
