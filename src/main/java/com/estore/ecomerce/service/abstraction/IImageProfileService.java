package com.estore.ecomerce.service.abstraction;

import com.estore.ecomerce.dto.ImageProfileRequest;

public interface IImageProfileService {
    ImageProfileRequest save(ImageProfileRequest imageProfileRequest);
}
