package me.antinux.fakeserver.controller;

import me.antinux.fakeserver.dto.UserLoginDTO;
import me.antinux.fakeserver.dto.UserSignUpDTO;
import me.antinux.fakeserver.entity.User;
import me.antinux.fakeserver.entity.Word;
import me.antinux.fakeserver.service.UserService;
import me.antinux.fakeserver.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
public class UserController {

    private UserService userService;

    private WordService wordService;

    @Autowired
    public UserController(UserService userService, WordService wordService) {
        this.userService = userService;
        this.wordService = wordService;
    }

    @PostMapping("/user/signUp")
    public @ResponseBody
    boolean signUp(@Valid @RequestBody UserSignUpDTO data) {
        if (userService.userExist(data)) {
            return false;
        }
        userService.addNewUser(data);
        return true;
    }

    @PostMapping("/user/login")
    public @ResponseBody
    User login(@Valid @RequestBody UserLoginDTO data) throws Exception {
        if (!userService.userExist(data)) {
            throw new Exception("false login");
        }
        if (userService.validatePassword(data)) {
            return userService.getUserByEmail(data.getEmail());
        } else {
            throw new Exception("false login");
        }
    }

    @GetMapping("/user/{id}")
    public @ResponseBody
    User getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/user/sort")
    public @ResponseBody
    List<User> sortUser() {
        return userService.sortUser();
    }

    @GetMapping("/user/{id}/solvedWords")
    public @ResponseBody
    List<Word> getSolvedWords(@PathVariable Long id) {
        return wordService.getSolvedWords(id);
    }

    @GetMapping("/user/{id}/ownWords")
    public @ResponseBody
    List<Word> getOwnWords(@PathVariable Long id) {
        return wordService.getOwnWords(id);
    }

}
