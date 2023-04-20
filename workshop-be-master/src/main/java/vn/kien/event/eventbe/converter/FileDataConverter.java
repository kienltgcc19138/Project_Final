package vn.kien.event.eventbe.converter;

import org.springframework.stereotype.Component;
import vn.kien.event.eventbe.entity.FileData;
import vn.kien.event.eventbe.response.FileDataResponse;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Le-Hong-Quan
 * Date: 04/03/2023
 * Time: 22:04
 */

public class FileDataConverter {
    public static FileDataResponse convertFileDataResponse(FileData fileData) {
        if(fileData == null) return null;
        FileDataResponse fileDataResponse = new FileDataResponse();
        fileDataResponse.setFileDataId(fileData.getFileDataId());
        fileDataResponse.setFileDataName(fileData.getFileName());
        fileDataResponse.setLink(fileData.getLink());
        fileDataResponse.setType(fileData.getType());
        return fileDataResponse;
    }

    public static Set<FileDataResponse> convertFileDataResponses(Set<FileData> fileData) {
        if(fileData == null) return new LinkedHashSet<>();
        return fileData.stream().map(FileDataConverter::convertFileDataResponse).collect(Collectors.toSet());
    }
}
