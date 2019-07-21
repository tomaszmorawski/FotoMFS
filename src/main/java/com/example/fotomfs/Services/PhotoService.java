package com.example.fotomfs.Services;

import com.example.fotomfs.Model.Photo;
import com.example.fotomfs.Model.User;
import com.example.fotomfs.Repository.PhotoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PhotoService {

    PhotoRepository photoRepository;
    UserService userService;

    public PhotoService(PhotoRepository photoRepository, UserService userService) {
        this.photoRepository = photoRepository;
        this.userService = userService;
    }

    public List<String> joinPhotoToUser(List<String> fileList , Long userId){
        User user = userService.getUserById(userId);
        for (String file: fileList){
            Photo photo = new Photo();
            photo.setFileName(file);
            photo.setUser(user);
            photoRepository.save(photo);
        }
        return fileList;
    }

    public List<String> findAllUserPhotoByUserId(Long userId) {
        List<String> fileList = new ArrayList<>();
        List<Photo> allByUserId = photoRepository.findAllByUserId(userId);
        for (Photo photo : allByUserId) {
            fileList.add(photo.getFileName());
        }
        return fileList;
    }
}
