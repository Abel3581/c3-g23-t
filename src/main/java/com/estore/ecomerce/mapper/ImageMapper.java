package com.estore.ecomerce.mapper;

import com.estore.ecomerce.domain.ImageProfile;
import com.estore.ecomerce.dto.ImageProfileRequest;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {

    public ImageProfile imageProfileDto2Entity(ImageProfileRequest imageProfileRequest) {
        ImageProfile entity = new ImageProfile();
        entity.setName(imageProfileRequest.getName());
        entity.setFileData(imageProfileRequest.getFileData());
        entity.setFileType(imageProfileRequest.getFileType());
        return entity;
    }


    public ImageProfileRequest imageProfileEntity2Dto(ImageProfile entity) {
        ImageProfileRequest dto = new ImageProfileRequest();
        dto.setName(entity.getName());
        dto.setFileData(entity.getFileData());
        dto.setFileType(entity.getFileType());
        return dto;
    }
}
