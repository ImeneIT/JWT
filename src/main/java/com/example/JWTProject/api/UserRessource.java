package com.example.JWTProject.api;

import com.example.JWTProject.domain.AppUser;
import com.example.JWTProject.domain.Role;
import com.example.JWTProject.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.List;

@RestController @RequiredArgsConstructor
@RequestMapping("/api")
public class UserRessource {
    @Autowired
    UserService userService;
@GetMapping("/users")
    public ResponseEntity<List<AppUser>>getUsers(){
    return ResponseEntity.ok().body(userService.getUsers());
}
@PostMapping("user/save")
public ResponseEntity<AppUser>saveUser(@RequestBody AppUser user){
    URI uri= URI.create(String.valueOf(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/user/save")));
    return ResponseEntity.created(uri).body(userService.saveUser(user));
}
    @PostMapping("role/save")
    public ResponseEntity<Role>saveUser(@RequestBody Role role){
        URI uri= URI.create(String.valueOf(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/role/save")));
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }
@PostMapping("/role/addtouser")
public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form){
    userService.addRoleToUser(form.getUsername(), form.getRolename());
    return ResponseEntity.ok().build();
}
    @Data
    class RoleToUserForm{
    private String username;
        private String rolename;
    }
}
