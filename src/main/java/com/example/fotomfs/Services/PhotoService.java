package com.example.fotomfs.Services;

import com.example.fotomfs.Model.Photo;
import com.example.fotomfs.Model.User;
import com.example.fotomfs.Repository.PhotoRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class PhotoService {
    public static final String uploadingDir = System.getProperty("user.dir") + "/src/main/upload/";

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
    public List<Photo> findAllUserPhoto(Long userId) {
        return photoRepository.findAllByUserId(userId);
    }

    public void removeFileByFileName(String fileName) {
        Photo photo = photoRepository.findByFileName(fileName);
        photoRepository.delete(photo);
        Path fileToDeletePath = Paths.get(uploadingDir + fileName);
        try {
            Files.delete(fileToDeletePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> findAllUserPhotoByUserIdAndAreChoice(Long id) {
        List<String> fileList = new ArrayList<>();
        List<Photo> allByUserId = photoRepository.findAllByUserId(id);
        for (Photo photo : allByUserId) {
            if (photo.isChosen()) {
                fileList.add(photo.getFileName());
            }
        }
        return fileList;
    }

    public void changeChoice(String filename) {
        Photo photo = photoRepository.findByFileName(filename);
        photo.setChosen(!photo.isChosen());
        photoRepository.save(photo);
    }

    public void deletePhotoByUserId(Long userId) {
        List<String> photoList = findAllUserPhotoByUserId(userId);
        for (String photo: photoList) {
            removeFileByFileName(photo);
        }
        photoRepository.deleteAllByUserId(userId);
    }
}
