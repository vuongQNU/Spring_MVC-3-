package com.abc.controllers;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.abc.entities.Post;
import com.abc.entities.User;
import com.abc.entities.Province;
import com.abc.services.PostService;
import com.abc.services.ProvinceService;
import com.abc.services.UserService;

import jakarta.servlet.http.HttpSession;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class UserController {

    private PostService postService;
    private ProvinceService provinceService;
    private UserService userService;

    @Autowired
    public UserController(PostService postService, ProvinceService provinceService, UserService userService) {
        this.postService = postService;
        this.provinceService = provinceService;
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String profileUser(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            return "redirect:/login";

        List<Post> posts = postService.getPostById(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("posts", posts);
        return "profile";
    }

    @GetMapping("/edit")
    public String showEditProfile(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            return "redirect:/login";

        List<Province> provinces = provinceService.getAllProvinces();
        model.addAttribute("user", user);
        model.addAttribute("provinces", provinces);
        return "editProfile";
    }

    @PostMapping("/edit")
    public String handleEditProfile(@RequestParam String email,
                                    @RequestParam String birthday,
                                    @RequestParam int provinceId,
                                    @RequestParam("avatarFile") MultipartFile avatarFile,
                                    Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            return "redirect:/login";

        try {
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

            userService.updateUser(user);
            session.setAttribute("user", user);
            return "redirect:/profile";
        } catch (Exception e) {
            Map<String, String> errors = new HashMap<>();
            errors.put("error", e.getMessage());
            model.addAttribute("errors", errors);
            model.addAttribute("user", user);
            model.addAttribute("provinces", provinceService.getAllProvinces());
            return "editProfile";
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
}