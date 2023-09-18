package com.hommies.userauthwithjwt.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/management")
@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
public class ManagementController {

    @PostMapping
    @PreAuthorize("hasAnyAuthority('management:create','admin:create')")
    public String post(){
        return "MANAGEMENT :: post";
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('management:read','admin:read')")
    public String get(){
        return "MANAGEMENT :: get";
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('management:update','admin:update')")
    public String put(){
        return "MANAGEMENT :: put";
    }

    @DeleteMapping
   @PreAuthorize("hasAnyAuthority('management:delete','admin:delete')")
    public String delete(){
        return "MANAGEMENT :: delete";
    }
}
