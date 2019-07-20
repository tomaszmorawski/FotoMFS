package com.example.fotomfs.Controllers;

import com.example.fotomfs.Model.Role;
import com.example.fotomfs.Model.User;
import com.example.fotomfs.Services.RoleService;
import com.example.fotomfs.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InitAdminUserController {
    private final
    UserService userService;
    private final
    RoleService roleService;
    private final
    PasswordEncoder passwordEncoder;
    public InitAdminUserController(UserService userService, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/init")
    public String InitAdminUser() {
        userService.removeAllUsers();
        roleService.removeAllRoles();
        User user = new User("admin","admin");
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");
        roleService.addRole(roleAdmin);
        roleService.addRole(roleUser);
        user.addRole(roleService.getRoleByName("ROLE_ADMIN"));
        user.addRole(roleService.getRoleByName("ROLE_USER"));
        userService.addUser(user);

        return "redirect:/";
    }
}
