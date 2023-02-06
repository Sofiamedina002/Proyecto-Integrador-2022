package com.dh.userprofile.controller;

import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;
import com.dh.userprofile.model.User;
import com.dh.userprofile.model.UserLoginDTO;
import com.dh.userprofile.model.UserVerifyDTO;
import com.dh.userprofile.service.IUserService;
import com.dh.userprofile.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        //log.info("User saved: " + user.getNombre());

        userService.save(user);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/verify")
    public Boolean verifyUser(@RequestBody UserVerifyDTO userverifyDTO){
        return userService.verifyUser(userverifyDTO);
    }

    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUserByID(@PathVariable Long id) {
        return ResponseEntity.ok().body(userService.getByID(id));
    }

    @GetMapping("/sub/{idSub}")
    public ResponseEntity<Optional<User>> getUserByIdSub(@PathVariable String idSub) {
        return ResponseEntity.ok().body(userService.getByIdSub(idSub));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResultType> userLogin(@RequestBody UserLoginDTO userLoginDTO){
        return ResponseEntity.ok().body(userService.userLogin(userLoginDTO));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> userLogout(@RequestBody String refreshToken){
        userService.userLogout(refreshToken);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public Optional<User> updateUser(@PathVariable Long id, @RequestBody Map<Object, Object> fields){
        return userService.patchUser(id,fields);
    }
}