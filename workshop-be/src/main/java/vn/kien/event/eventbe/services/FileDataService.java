package vn.kien.event.eventbe.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.kien.event.eventbe.converter.FileDataConverter;
import vn.kien.event.eventbe.entity.FileData;
import vn.kien.event.eventbe.repository.IFileDataRepository;
import vn.kien.event.eventbe.response.FileDataResponse;
import vn.kien.event.eventbe.response.sub.CloudinaryResponse;

import java.io.IOException;
import java.util.Date;


@Service
@RequiredArgsConstructor
public class FileDataService {

    private final IFileDataRepository fileDataRepository;
    private final CloudinaryService cloudinaryService;

    public FileDataResponse createFileData(MultipartFile file, String type, String createdBy) {
        try {
            CloudinaryResponse cloudinaryResponse = cloudinaryService.uploadFile(file, type);
            FileData fileData = new FileData();
            fileData.setFileName(file.getOriginalFilename());
            fileData.setExternalId(cloudinaryResponse.getPublicId());
            fileData.setLink(cloudinaryResponse.getLink());
            fileData.setCreatedBy(createdBy);
            fileData.setCreatedAt(new Date());
            fileDataRepository.save(fileData);
            return FileDataConverter.convertFileDataResponse(fileData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FileDataResponse getFileData(Long id) {
        FileData fileData = fileDataRepository.findById(id).orElseThrow(() -> new RuntimeException("File data not found"));
        return FileDataConverter.convertFileDataResponse(fileData);
    }
}
