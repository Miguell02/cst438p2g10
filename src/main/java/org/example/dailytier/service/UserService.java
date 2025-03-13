package org.example.dailytier.service;

import org.example.dailytier.model.User;
import org.example.dailytier.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

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
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(password)) {  // Ensure password matches
                userRepository.deleteById(id);
                return true;
            }
        }
        return false;
    }


    public boolean updateUser(Long id, User updatedUser) {

        return false;
    }


    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }


}


