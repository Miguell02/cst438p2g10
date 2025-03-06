package org.example.dailytier.service;

import org.example.dailytier.model.User;
import org.example.dailytier.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User createUser(String username, String password, String email) {
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setEmail(email);

        return userRepository.save(newUser);
    }


    public boolean loginUser(String username, String password) {

        return false;
    }


    public boolean deleteUser(Long id, String password) {

        return false;
    }


    public boolean updateUser(Long id, User updatedUser) {

        return false;
    }


    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
