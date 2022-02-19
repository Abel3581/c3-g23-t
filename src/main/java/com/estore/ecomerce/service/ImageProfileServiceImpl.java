package com.estore.ecomerce.service;

import com.estore.ecomerce.domain.ImageProfile;
import com.estore.ecomerce.dto.ImageProfileRequest;
import com.estore.ecomerce.mapper.ImageMapper;
import com.estore.ecomerce.repository.IImageProfileRepository;
import com.estore.ecomerce.service.abstraction.IImageProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageProfileServiceImpl implements IImageProfileService {

    @Autowired
    private ImageMapper imageMapper;
    @Autowired
    private IImageProfileRepository imageProfileRepository;

    @Override
    public ImageProfileRequest save(ImageProfileRequest imageProfileRequest) {
        ImageProfile entity =  imageMapper.imageProfileDto2Entity(imageProfileRequest);
        ImageProfile entitySaved = imageProfileRepository.save(entity);
        ImageProfileRequest result = imageMapper.imageProfileEntity2Dto(entitySaved);
        return result;
    }
}
