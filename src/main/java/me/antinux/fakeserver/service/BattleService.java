package me.antinux.fakeserver.service;

import me.antinux.fakeserver.entity.User;
import me.antinux.fakeserver.entity.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BattleService {

    private Long id = 0L;

    private WordService wordService;

    private UserService userService;

    private Map<Long, Word> wordMap = new HashMap<>();

    private Map<Long, Battle> battleMap = new HashMap<>();

    @Autowired
    public BattleService(WordService wordService, UserService userService) {
        this.wordService = wordService;
        this.userService = userService;
    }

    public Long createBattle(Long user1, Long user2) {
        Battle battle = new Battle(user1, user2);
        battleMap.put(id, battle);
        return id++;
    }

    public Word getBattleWord(Long battleId) {
        Word word = wordMap.get(battleId);
        if (word == null) {
            word = wordService.getRandomWordByLength(6);
            wordMap.put(battleId, word);
        }
        return word;
    }

    public boolean battleSolved(Long battleId, Long userId) {
        Battle battle = battleMap.get(battleId);

        if (!battle.user1.equals(userId) && !battle.user2.equals(userId)) return false;

        wordService.solvedWord(userId, wordMap.get(battleId).getId());

        if (battle.solved) {
            battleMap.remove(battleId);
            return false;
        }
        battle.solved = true;
        return true;
    }

    private static class Battle {

        private Long user1;

        private Long user2;

        private boolean solved = false;

        private Battle(Long user1, Long user2) {
            this.user1 = user1;
            this.user2 = user2;
        }

    }

}
