package me.antinux.fakeserver.service;

import me.antinux.fakeserver.repository.UserRepository;
import me.antinux.fakeserver.repository.ValidationPair;
import me.antinux.fakeserver.dto.UserSignUpDTO;
import me.antinux.fakeserver.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean validatePassword(ValidationPair data) {
        User user = userRepository.findTopByEmail(data.getEmail());
        return data.getPassword().equals(user.getPassword());
    }

    public void addNewUser(UserSignUpDTO data) {
        User n = new User();
        n.setEmail(data.getEmail());
        n.setPassword(data.getPassword());
        n.setNickname(data.getNickname());
        userRepository.save(n);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByEmail(String email) {
        return userRepository.findTopByEmail(email);
    }

    public boolean userExist(ValidationPair data) {
        return userRepository.existsByEmail(data.getEmail());
    }

    public List<User> sortUser() {
        return userRepository.findAllByOrderByLevelDesc();
    }
}
