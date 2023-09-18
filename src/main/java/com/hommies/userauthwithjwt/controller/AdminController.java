package com.hommies.userauthwithjwt.controller;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @PostMapping
    public String post(){
        return "ADMIN :: post";
    }

    @GetMapping
    public String get(){
        return "ADMIN :: get";
    }

    @PutMapping
    public String put(){
        return "ADMIN :: put";
    }

    @DeleteMapping
    public String delete(){
        return "ADMIN :: delete";
    }
}
