package com.estore.ecomerce.service;

import java.util.ArrayList;

import com.estore.ecomerce.domain.ImagePost;
import com.estore.ecomerce.domain.ImageProfile;
import com.estore.ecomerce.repository.ImageRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService{

    private final ImageRepository imageRepository;

    @Override
    public ImageProfile uploadImageProfileToDB(MultipartFile image) {
        if(image != null){
            ImageProfile profileImage = new ImageProfile();
            try {
                profileImage.setFileData(image.getBytes());
                profileImage.setFileType(image.getContentType());
                profileImage.setName(image.getOriginalFilename());
                imageRepository.save(profileImage);
                return profileImage;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }else{
            return null;
        }
    }

    @Override
    public ArrayList<ImagePost> uploadImagePostToDB(ArrayList<MultipartFile> postImage) {
        if(postImage != null && postImage.size()>0){
            ArrayList<ImagePost> Images = new ArrayList<ImagePost>();
            for (MultipartFile image : postImage) {
                try {
                    Images.add(
                    new ImagePost(
                        image.getOriginalFilename(),
                        image.getContentType(),
                        image.getBytes()
                    )
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
            /*
            for (ImagePost post_image : Images) {
                imageRepository.save(post_image);
            }
            */
            return Images;
        }else{
            return null;
        }
    }

    @Override
    public void deleteImagePostToDB(ArrayList<ImagePost> postImage) {
        for (ImagePost element : postImage) {
            imageRepository.delete(element);
        }
        
    }
    
}
