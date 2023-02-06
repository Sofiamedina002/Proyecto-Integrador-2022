package com.dh.userprofile.service;

import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;
import com.dh.userprofile.model.User;
import com.dh.userprofile.model.UserLoginDTO;
import com.dh.userprofile.model.UserVerifyDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IUserService {
    User save(User user);
    List<User> getAll();
    Optional<User> getByID(Long id);
    Boolean verifyUser(UserVerifyDTO userverifyDto);
    AuthenticationResultType userLogin(UserLoginDTO userLoginDTO);
    void userLogout(String refreshToken);
    Optional<User> getByIdSub(String idSub);
    Optional<User> patchUser(Long id, Map<Object, Object> fields);
}
