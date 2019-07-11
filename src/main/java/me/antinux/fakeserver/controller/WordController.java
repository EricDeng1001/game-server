package me.antinux.fakeserver.controller;

import me.antinux.fakeserver.dto.AddNewWordDTO;
import me.antinux.fakeserver.dto.SolvedWordDTO;
import me.antinux.fakeserver.entity.User;
import me.antinux.fakeserver.entity.Word;
import me.antinux.fakeserver.service.BattleService;
import me.antinux.fakeserver.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
public class WordController {

    private WordService wordService;

    private BattleService battleService;

    @Autowired
    public WordController(WordService wordService, BattleService battleService) {
        this.wordService = wordService;
        this.battleService = battleService;
    }

    @GetMapping("/word/all")
    public @ResponseBody
    List<Word> getAllWords() {
        return wordService.listAllWords();
    }

    @GetMapping("/word/{length}")
    public @ResponseBody
    Word getWord(@PathVariable int length) {
        return wordService.getRandomWordByLength(length);
    }

    @GetMapping("/word/{id}/solved/")
    public @ResponseBody
    List<User> getSolvedUser(@PathVariable Long id) {
        return wordService.getSolvedUsers(id);
    }

    @PostMapping("/word/new")
    public @ResponseBody
    boolean addNewWord(@Valid @RequestBody AddNewWordDTO data) {
        return wordService.addNewWord(data);
    }

    @PostMapping("/word/solved")
    public @ResponseBody
    boolean solvedWord(@Valid @RequestBody SolvedWordDTO data) {
        return wordService.solvedWord(data.getUserId(), data.getWordId());
    }

}
