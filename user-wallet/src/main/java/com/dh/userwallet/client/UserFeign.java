package com.dh.userwallet.client;

import com.dh.userwallet.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@FeignClient(name = "user-profile", url = "http://localhost:8081/users")

public interface UserFeign {

    @GetMapping("/{id}")
    User getUserById(@PathVariable Long id);


}
