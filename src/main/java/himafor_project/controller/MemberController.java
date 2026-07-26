package himafor_project.controller;

import himafor_project.dto.ApiResponse;
import himafor_project.dto.MemberRequest;
import himafor_project.dto.MemberResponse;
import himafor_project.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * Controller untuk CRUD Anggota / Pengurus dengan dukungan upload foto langsung.
 */
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    @SuppressWarnings("unchecked")
    public ResponseEntity<ApiResponse<List<MemberResponse>>> getAllMembers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String search) {

        Map<String, Object> result = memberService.getAllMembers(page, limit, search);
        List<MemberResponse> data = (List<MemberResponse>) result.get("data");
        Object meta = result.get("meta");

        return ResponseEntity.ok(ApiResponse.success("Daftar anggota berhasil diambil", data, meta));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MemberResponse>> getMemberById(@PathVariable Long id) {
        MemberResponse response = memberService.getMemberById(id);
        return ResponseEntity.ok(ApiResponse.success("Detail anggota berhasil diambil", response));
    }

    /**
     * Tambah Anggota baru dengan opsi langsung upload file foto.
     */
    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ApiResponse<MemberResponse>> createMemberWithFile(
            @RequestParam("name") String name,
            @RequestParam("position") String position,
            @RequestParam("period") String period,
            @RequestParam(value = "photo", required = false) MultipartFile photoFile) {

        MemberRequest request = MemberRequest.builder()
                .name(name)
                .position(position)
                .period(period)
                .build();

        MemberResponse response = memberService.createMember(request, photoFile);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Anggota berhasil ditambahkan", response));
    }

    /**
     * Tambah Anggota baru via Payload JSON.
     */
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ApiResponse<MemberResponse>> createMemberJson(@Valid @RequestBody MemberRequest request) {
        MemberResponse response = memberService.createMember(request, null);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Anggota berhasil ditambahkan", response));
    }

    /**
     * Edit Anggota dengan opsi langsung upload file foto baru.
     */
    @PatchMapping(value = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ApiResponse<MemberResponse>> updateMemberWithFile(
            @PathVariable Long id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "position", required = false) String position,
            @RequestParam(value = "period", required = false) String period,
            @RequestParam(value = "photo", required = false) MultipartFile photoFile) {

        MemberRequest request = MemberRequest.builder()
                .name(name)
                .position(position)
                .period(period)
                .build();

        MemberResponse response = memberService.updateMember(id, request, photoFile);
        return ResponseEntity.ok(ApiResponse.success("Data anggota berhasil diperbarui", response));
    }

    /**
     * Edit Anggota via Payload JSON.
     */
    @PatchMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ApiResponse<MemberResponse>> updateMemberJson(
            @PathVariable Long id,
            @RequestBody MemberRequest request) {
        MemberResponse response = memberService.updateMember(id, request, null);
        return ResponseEntity.ok(ApiResponse.success("Data anggota berhasil diperbarui", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.ok(ApiResponse.successMessage("Anggota berhasil dihapus"));
    }
}
