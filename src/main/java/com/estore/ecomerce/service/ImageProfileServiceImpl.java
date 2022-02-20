package com.estore.ecomerce.service;

import com.estore.ecomerce.domain.ImageProfile;
import com.estore.ecomerce.dto.ImageProfileRequest;
import com.estore.ecomerce.mapper.ImageMapper;
import com.estore.ecomerce.repository.IImageProfileRepository;
import com.estore.ecomerce.service.abstraction.IImageProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ImageProfileServiceImpl implements IImageProfileService {

    private static final String IMAGE_PROFILE_NOT_FOUND_MESSAGE = "Image not found.";

    @Autowired
    private ImageMapper imageMapper;
    @Autowired
    private IImageProfileRepository imageProfileRepository;

    @Override
    public ImageProfileRequest save(ImageProfileRequest imageProfileRequest) {
        ImageProfile entity = imageMapper.imageProfileDto2Entity(imageProfileRequest);
        ImageProfile entitySaved = imageProfileRepository.save(entity);
        ImageProfileRequest result = imageMapper.imageProfileEntity2Dto(entitySaved);
        return result;
    }

    @Override
    public ImageProfile getImageProfile(String imageProfileId) {
        Optional<ImageProfile> imageProfileOptional = imageProfileRepository.findByName(imageProfileId);
        if (imageProfileOptional.isEmpty() || imageProfileOptional.get().isSoftDelete()) {
            throw new EntityNotFoundException(IMAGE_PROFILE_NOT_FOUND_MESSAGE);
        }
        return imageProfileOptional.get();
    }

    @Override
    public ImageProfile getImage(ImageProfile imageProfile) {
        return null;
    }




}
