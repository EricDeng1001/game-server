package me.antinux.fakeserver.service;

import me.antinux.fakeserver.dto.AddNewWordDTO;
import me.antinux.fakeserver.dto.SolvedWordDTO;
import me.antinux.fakeserver.entity.MaxWordSize;
import me.antinux.fakeserver.entity.User;
import me.antinux.fakeserver.entity.UserSolvedWord;
import me.antinux.fakeserver.entity.Word;
import me.antinux.fakeserver.repository.MaxWordSizeRepository;
import me.antinux.fakeserver.repository.UserRepository;
import me.antinux.fakeserver.repository.UserSolvedWordRepository;
import me.antinux.fakeserver.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Long.max;
import static java.lang.Math.min;

@Service
public class WordService {

    private WordRepository wordRepository;

    private MaxWordSizeRepository maxWordSizeRepository;

    private UserRepository userRepository;

    private UserSolvedWordRepository userSolvedWordRepository;

    private Map<Integer, List<Word>> wordLists = new HashMap<>();

    @Autowired
    public WordService(
        WordRepository wordRepository,
        MaxWordSizeRepository maxWordSizeRepository,
        UserRepository userRepository,
        UserSolvedWordRepository userSolvedWordRepository
    ) {
        this.wordRepository = wordRepository;
        this.maxWordSizeRepository = maxWordSizeRepository;
        this.userRepository = userRepository;
        this.userSolvedWordRepository = userSolvedWordRepository;
    }

    public Word getRandomWordByLength(int length) {
        int maxLength = maxWordSizeRepository.findTopByOrderByLengthDesc().getLength();
        System.out.println(maxLength);
        System.out.println(length);
        length = min(maxLength, length);
        List<Word> words = wordLists.get(length);
        if (words == null) {
            words = wordRepository.findAllByLength(length);
            wordLists.put(length, words);
        }

        int random = (int) Math.floor(Math.random() * words.size());

        return words.get(random);
    }

    public boolean addNewWord(AddNewWordDTO data) {
        if (wordRepository.existsByContent(data.getContent())) return false;

        if (!userRepository.existsById(data.getUserId())) return false;

        String content = data.getContent();
        Word word = new Word();
        word.setContent(content);
        int length = content.length();
        word.setDuration(length);
        word.setOwnerId(data.getUserId());
        word.setLength(length);
        int expGained = length * (int) Math.pow(1.3, max(length - 4, 0));
        word.setExpGain(expGained);
        MaxWordSize topByOrderByLengthAsc = maxWordSizeRepository.findTopByOrderByLengthDesc();
        int maxLength;
        if (topByOrderByLengthAsc == null) {
            maxLength = 0;
        } else {
            maxLength = topByOrderByLengthAsc.getLength();
        }

        if (length > maxLength) {
            MaxWordSize record = new MaxWordSize();
            record.setDate(new Date(new java.util.Date().getTime()));
            record.setLength(length);
            maxWordSizeRepository.save(record);
        }

        wordRepository.save(word);
        return true;
    }

    public boolean solvedWord(Long userId, Long wordId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return false;
        }
        Word word = wordRepository.findById(wordId).orElse(null);
        if (word == null) {
            return false;
        }

        int solvedNumber = user.getSolvedNumber();
        solvedNumber++;
        user.setSolvedNumber(solvedNumber);

        int exp = user.getExp();
        int required = (int) (100 * Math.pow(1.01, user.getLevel()));

        int total = exp + word.getExpGain();
        if (total > required) {
            int level = user.getLevel();
            level++;
            user.setLevel(level);
            user.setExp(total - required);
        } else {
            user.setExp(total);
        }

        userRepository.save(user);

        UserSolvedWord record = new UserSolvedWord();
        record.setUserId(userId);
        record.setWordId(wordId);
        userSolvedWordRepository.save(record);

        return true;
    }

    public List<Word> getOwnWords(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }
        return wordRepository.findByOwnerId(userId);
    }

    public List<Word> getSolvedWords(Long userId) {
        List<Long> ids = userSolvedWordRepository.findAllByUserId(userId).stream().map(
            UserSolvedWord::getWordId
        ).collect(Collectors.toList());

        List<Word> words = new ArrayList<>();
        for (Word word : wordRepository.findAllById(ids)) {
            words.add(word);
        }

        return words;
    }

    public List<User> getSolvedUsers(Long wordId) {
        List<Long> ids = userSolvedWordRepository.findAllByWordId(wordId).stream().map(
            UserSolvedWord::getUserId
        ).collect(Collectors.toList());

        List<User> users = new ArrayList<>();
        for (User user : userRepository.findAllById(ids)) {
            users.add(user);
        }

        return users;
    }

    public List<Word> listAllWords() {
        List<Word> x = new LinkedList<>();
        for (Word w : wordRepository.findAll()) {
            x.add(w);
        }
        return x;
    }

}
