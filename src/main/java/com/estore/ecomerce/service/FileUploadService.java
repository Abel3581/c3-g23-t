package com.estore.ecomerce.service;

import java.util.ArrayList;

import com.estore.ecomerce.domain.ImagePost;
import com.estore.ecomerce.domain.ImageProfile;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    public ImageProfile uploadImageProfileToDB(MultipartFile image);  
    public ArrayList<ImagePost> uploadImagePostToDB(ArrayList<MultipartFile> postImage);
    public ArrayList<ImagePost> updateImagesPostToDB(ArrayList<MultipartFile> postImage);
    public void deleteImagePostToDB(ArrayList<ImagePost> postImage);
}
