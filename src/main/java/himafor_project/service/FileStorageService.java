package himafor_project.service;

import himafor_project.dto.UploadResponse;
import himafor_project.exception.BadRequestException;
import himafor_project.model.Upload;
import himafor_project.repository.UploadRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Objects;
import java.util.UUID;

/**
 * Service untuk menangani upload dan penyimpanan berkas gambar.
 */
@Service
public class FileStorageService {

    private final Path fileStorageLocation;
    private final UploadRepository uploadRepository;

    public FileStorageService(@Value("${file.upload-dir:uploads}") String uploadDir,
                              UploadRepository uploadRepository) {
        this.uploadRepository = uploadRepository;
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new BadRequestException("Tidak dapat membuat direktori penyimpanan berkas: " + ex.getMessage());
        }
    }

    public UploadResponse storeFile(MultipartFile file) {
        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        if (file.isEmpty()) {
            throw new BadRequestException("Gagal menyimpan berkas kosong.");
        }

        try {
            String extension = "";
            int i = originalFileName.lastIndexOf('.');
            if (i > 0) {
                extension = originalFileName.substring(i);
            }

            String newFileName = UUID.randomUUID().toString() + extension;
            Path targetLocation = this.fileStorageLocation.resolve(newFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            String fileUrl = "/uploads/" + newFileName;

            Upload upload = Upload.builder()
                    .filename(newFileName)
                    .filePath(fileUrl)
                    .fileSize(file.getSize())
                    .build();

            uploadRepository.save(upload);

            return UploadResponse.builder()
                    .filename(newFileName)
                    .url(fileUrl)
                    .size(file.getSize())
                    .build();

        } catch (IOException ex) {
            throw new BadRequestException("Gagal menyimpan berkas " + originalFileName + ". Silakan coba lagi!");
        }
    }

    public String storeFileAndGetUrl(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        UploadResponse response = storeFile(file);
        return response.getUrl();
    }
}
