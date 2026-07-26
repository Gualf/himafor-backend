package himafor_project.controller;

import himafor_project.dto.ApiResponse;
import himafor_project.dto.UploadResponse;
import himafor_project.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller untuk Endpoint Upload Berkas Gambar.
 */
@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class UploadController {

    private final FileStorageService fileStorageService;

    @PostMapping
    public ResponseEntity<ApiResponse<UploadResponse>> uploadFile(@RequestParam("file") MultipartFile file) {
        UploadResponse response = fileStorageService.storeFile(file);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("File berhasil diunggah", response));
    }
}
