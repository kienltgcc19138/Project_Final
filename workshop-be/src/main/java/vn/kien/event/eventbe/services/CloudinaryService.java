package vn.kien.event.eventbe.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.Singleton;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.kien.event.eventbe.response.sub.CloudinaryResponse;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {
    private final Cloudinary cloudinary = Singleton.getCloudinary();

    public CloudinaryResponse uploadFile(MultipartFile file, String type) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                "folder", "event-be",
                "resource_type", type
        ));
        return new CloudinaryResponse(
                uploadResult.get("public_id").toString(),
                uploadResult.get("url").toString()
        );
    }
}
