package com.abc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.abc.entities.User;
import com.abc.entities.Province;
import com.abc.services.UserService;
import com.abc.services.ProvinceService;

import jakarta.servlet.http.HttpSession;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.List;

@Controller
public class AuthController {

    private UserService userService;
    private ProvinceService provinceService;

    @Autowired
    public AuthController(UserService userService, ProvinceService provinceService) {
        this.userService = userService;
        this.provinceService = provinceService;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session, Model model) {
        User user = userService.getUserByUserName(username);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("user", user);
            session.setAttribute("user_id", user.getId());
            return "redirect:/";
        }
        model.addAttribute("error", "Sai tên đăng nhập hoặc mật khẩu");
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("provinces", provinceService.getAllProvinces());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               @RequestParam("email") String email,
                               @RequestParam("birthday") String birthday,
                               @RequestParam("provinceId") int provinceId,
                               @RequestParam("avatarFile") MultipartFile avatarFile,
                               Model model) {
        try {
            if (userService.getUserByUserName(username) != null) {
                throw new Exception("Tên người dùng đã tồn tại");
            }

            User user = new User(username, password);
            user.setEmail(email);
            user.setBirthday(Date.valueOf(birthday));
            user.setProvince(provinceService.getProvinceById(provinceId));

            // Xử lý upload avatar
            if (!avatarFile.isEmpty()) {
                validateAvatar(avatarFile);
                String fileName = System.currentTimeMillis() + "_" + avatarFile.getOriginalFilename();
                Path path = Paths.get("src/main/webapp/uploads/" + fileName);
                Files.createDirectories(path.getParent());
                avatarFile.transferTo(path.toFile());
                user.setAvatar("/uploads/" + fileName);
            }

            userService.registerUser(user);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("provinces", provinceService.getAllProvinces());
            return "register";
        }
    }

    private void validateAvatar(MultipartFile file) throws Exception {
        String contentType = file.getContentType();
        if (!contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
            throw new Exception("Chỉ chấp nhận file JPG hoặc PNG");
        }
        if (file.getSize() > 200 * 1024) {
            throw new Exception("Dung lượng file không được vượt quá 200KB");
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}