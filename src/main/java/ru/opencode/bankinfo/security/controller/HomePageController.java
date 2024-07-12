package ru.opencode.bankinfo.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/")
public class HomePageController {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public String getManualsByInfoId() {
        return "Добро пожаловать!";
    }
}
