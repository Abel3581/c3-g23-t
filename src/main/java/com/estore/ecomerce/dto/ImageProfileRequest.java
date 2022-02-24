package com.estore.ecomerce.dto;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;

@Getter @Setter
public class ImageProfileRequest {


    @NotBlank(message = "Image Profile cannot be empty")
    private String name;
    @NotBlank(message = "FileType cannot be empty")
    private String fileType;

    private byte[] fileData;
}
