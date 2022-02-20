package com.estore.ecomerce.service.abstraction;

import com.estore.ecomerce.domain.ImageProfile;
import com.estore.ecomerce.dto.ImageProfileRequest;

public interface IImageProfileService {
    ImageProfileRequest save(ImageProfileRequest imageProfileRequest);

    ImageProfile getImageProfile(String imageProfileId);

    ImageProfile getImage(ImageProfile imageProfile);
}
