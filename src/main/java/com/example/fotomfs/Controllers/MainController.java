package com.example.fotomfs.Controllers;

import com.example.fotomfs.Model.User;
import com.example.fotomfs.Services.PhotoService;
import com.example.fotomfs.Services.RoleService;
import com.example.fotomfs.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    public static final String uploadingDir = System.getProperty("user.dir") + "/src/main/upload/";

    URL res = getClass().getClassLoader().getResource("upload");

    private UserService userService;
    private RoleService roleService;
    private PhotoService photoService;

    public MainController(UserService userService, RoleService roleService, PhotoService photoService) {
        this.userService = userService;
        this.roleService = roleService;
        this.photoService = photoService;
    }




    @GetMapping("/photoUser/{id}")
    private String showAddedPhotosToUser (@PathVariable Long id, Model model) {
        List<String> fileList = photoService.findAllUserPhotoByUserId(id);
        model.addAttribute("fileList", fileList);
        return "photoUser";
    }

    @GetMapping("/photoUserChoice/{id}")
    private String showAddedPhotosToAdmin (@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        List<String> fileList = photoService.findAllUserPhotoByUserIdAndAreChoice(id);
        model.addAttribute("userLogin", user.getLogin());
        model.addAttribute("fileList",fileList);
        return "photoUserChoice";
    }

    @GetMapping("/loginHome")
    private String showLoginPage(Model model) {
        return "loginHome";
    }

    @GetMapping("/addNewUser")
    private String addNewUser(Model model) {
        User newUser = new User();
        newUser.setPassword("randomStringGenerated");
        model.addAttribute("newUser", newUser);
        return "addNewUser";
    }

    @PostMapping("/newuser")
    private String crateNewUser(User newUser, Model model) {
        if (newUser.getLogin().isEmpty() || newUser.getPassword().isEmpty()) {
            model.addAttribute("newUser", newUser);
            return "addNewUser";
        }
        newUser.getRoles().add(roleService.getRoleByName("ROLE_USER"));
        userService.addUser(newUser);
        List<User> userList = userService.getAllUsers();
        model.addAttribute("users", userList);
        return "redirect:/admin";
    }

    @GetMapping("/admin")
    private String adminPage(Model model) {
        List<User> userList = userService.getAllUsers();
        model.addAttribute("users", userList);
        return "admin";
    }

    @GetMapping("/delete/{userId}")
    private String deleteUser(@PathVariable Long userId, Model model){
        userService.deleteUserById(userId);
        return "redirect:/admin";
    }

    @GetMapping("/delete/{userId}/{fileName}")
    private String deleteUser(@PathVariable Long userId,@PathVariable String fileName, Model model){
        photoService.removeFileByFileName(fileName);
        return "redirect:/photoAdmin/"+userId;
        }


    @GetMapping("/photoAdmin/{userId}")
    private String showMainPage(@PathVariable Long userId,Model model) {
        List<String> fileList = photoService.findAllUserPhotoByUserId(userId);
        model.addAttribute("count", fileList.size());
        model.addAttribute("userId",userId);
        model.addAttribute("fileList", fileList);
        return "photoAdmin";
    }

    @PostMapping("photoAdmin/{id}")
    public String uploadingPost(@RequestParam("uploadingFiles") MultipartFile[] uploadingFiles,
                                @PathVariable Long id) throws IOException {
        List<String> fileList = new ArrayList<>();
        for(MultipartFile uploadedFile : uploadingFiles) {
            File file = new File(uploadingDir + uploadedFile.getOriginalFilename());
            uploadedFile.transferTo(file);
            fileList.add(uploadedFile.getOriginalFilename());
        }
        photoService.joinPhotoToUser(fileList,id);
        return "redirect:/photoAdmin/"+id;
    }

    @RequestMapping(value = "image/{imageName}")
    @ResponseBody
    public byte[] getImage(@PathVariable(value = "imageName") String imageName) throws IOException {

        File serverFile = new File(uploadingDir + imageName);

        return Files.readAllBytes(serverFile.toPath());
    }
  
    @GetMapping("/errorPage")
    private String errorPage (Model model){
        return "errorPage";
    }

}
