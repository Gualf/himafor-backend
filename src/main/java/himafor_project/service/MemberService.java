package himafor_project.service;

import himafor_project.dto.MemberRequest;
import himafor_project.dto.MemberResponse;
import himafor_project.exception.ResourceNotFoundException;
import himafor_project.model.Member;
import himafor_project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service untuk manajemen CRUD data Anggota dengan dukungan upload foto langsung.
 */
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final FileStorageService fileStorageService;

    public Map<String, Object> getAllMembers(int page, int limit, String search) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Member> memberPage;

        if (search != null && !search.trim().isEmpty()) {
            memberPage = memberRepository.findByNameContainingIgnoreCase(search.trim(), pageable);
        } else {
            memberPage = memberRepository.findAll(pageable);
        }

        List<MemberResponse> data = memberPage.getContent().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        Map<String, Object> meta = new HashMap<>();
        meta.put("page", page);
        meta.put("limit", limit);
        meta.put("total_data", memberPage.getTotalElements());
        meta.put("total_pages", memberPage.getTotalPages());

        Map<String, Object> result = new HashMap<>();
        result.put("data", data);
        result.put("meta", meta);

        return result;
    }

    public MemberResponse getMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Anggota dengan ID " + id + " tidak ditemukan"));
        return mapToResponse(member);
    }

    public MemberResponse createMember(MemberRequest request, MultipartFile photoFile) {
        String photoUrl = request.getPhoto();
        if (photoFile != null && !photoFile.isEmpty()) {
            photoUrl = fileStorageService.storeFileAndGetUrl(photoFile);
        }

        Member member = Member.builder()
                .name(request.getName())
                .position(request.getPosition())
                .photo(photoUrl)
                .period(request.getPeriod())
                .build();

        Member savedMember = memberRepository.save(member);
        return mapToResponse(savedMember);
    }

    public MemberResponse updateMember(Long id, MemberRequest request, MultipartFile photoFile) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Anggota dengan ID " + id + " tidak ditemukan"));

        if (photoFile != null && !photoFile.isEmpty()) {
            member.setPhoto(fileStorageService.storeFileAndGetUrl(photoFile));
        } else if (request.getPhoto() != null) {
            member.setPhoto(request.getPhoto());
        }

        if (request.getName() != null) member.setName(request.getName());
        if (request.getPosition() != null) member.setPosition(request.getPosition());
        if (request.getPeriod() != null) member.setPeriod(request.getPeriod());

        Member updatedMember = memberRepository.save(member);
        return mapToResponse(updatedMember);
    }

    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Anggota dengan ID " + id + " tidak ditemukan"));
        memberRepository.delete(member);
    }

    private MemberResponse mapToResponse(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .position(member.getPosition())
                .photo(member.getPhoto())
                .period(member.getPeriod())
                .createdAt(member.getCreatedAt())
                .build();
    }
}
