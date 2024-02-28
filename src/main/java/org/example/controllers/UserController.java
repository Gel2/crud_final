package org.example.controllers;

import org.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.example.models.User;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String redirectToUsers() {
        log.info("Перенаправление на /users");
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        log.info("Получение всех пользователей");
        model.addAttribute("users", userService.getAllUsers());
        return "users/index";
    }

    @GetMapping("/users/show")
    public String getUser(@RequestParam("id") int id, Model model) {
        log.info("Получение пользователя с id: " + id);
        model.addAttribute("user", userService.getUser(id));
        return "users/show";
    }

    @GetMapping("/users/new")
    public String newUser(Model model) {
        log.info("Создание нового пользователя");
        model.addAttribute("user", new User());
        return "users/new";
    }

    @PostMapping("/users")
    public String addUser(@ModelAttribute("user") User user) {
        log.info("Добавление нового пользователя: " + user.getName());
        userService.addUser(user);
        return "redirect:/users";
    }

    @GetMapping("/users/edit")
    public String editUser(@RequestParam("id") int id, Model model) {
        log.info("Редактирование пользователя с id: " + id);
        model.addAttribute("user", userService.getUser(id));
        return "users/edit";
    }

    @PostMapping("/users/update")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam("id") int id) {
        log.info("Обновление пользователя с id: " + id);
        userService.updateUser(id, user);
        return "redirect:/users";
    }

    @PostMapping("/users/delete")
    public String deleteUser(@RequestParam("id") int id) {
        log.info("Удаление пользователя с id: " + id);
        userService.deleteUser(id);
        return "redirect:/users";
    }
}