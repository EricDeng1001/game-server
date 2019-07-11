package me.antinux.fakeserver.controller;

import me.antinux.fakeserver.dto.SolvedBattleDTO;
import me.antinux.fakeserver.entity.User;
import me.antinux.fakeserver.entity.Word;
import me.antinux.fakeserver.service.BattleService;
import me.antinux.fakeserver.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Controller
public class BattleController {

    private boolean isWaiting = false;

    private boolean created = false;

    private Long newBattleId;

    private Long waitingUser;

    private Long thatUser;

    private UserService userService;

    private BattleService battleService;

    public BattleController(UserService userService, BattleService battleService) {
        this.userService = userService;
        this.battleService = battleService;
    }

    @GetMapping("/battle/wait/{userId}")
    public @ResponseBody
    BattleResponse createBattle(@PathVariable Long userId) {
        if (!isWaiting) {
            isWaiting = true;
            waitingUser = userId;
            int wait_time = 0;
            while (!created) {
                try {
                    Thread.sleep(20);
                    wait_time += 20;
                    if (wait_time == 30 * 1000) {
                        isWaiting = false;
                        return null;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            created = false;
            return new BattleResponse(newBattleId, userService.getUserById(thatUser));
        } else {
            thatUser = userId;
            newBattleId = battleService.createBattle(userId, waitingUser);
            isWaiting = false;
            created = true;
            return new BattleResponse(newBattleId, userService.getUserById(waitingUser));
        }
    }

    @PostMapping("/battle/solved")
    public @ResponseBody
    boolean solvedBattle(@RequestBody SolvedBattleDTO data) {
        return battleService.battleSolved(data.getBattleId(), data.getUserId());
    }

    @GetMapping("/battle/{battleId}")
    public @ResponseBody
    Word getWord(@PathVariable Long battleId) {
        return battleService.getBattleWord(battleId);
    }

    private static class BattleResponse {

        private Long battleId;

        private User opposite;

        private BattleResponse(Long battleId, User opposite) {
            this.battleId = battleId;
            this.opposite = opposite;
        }

        public Long getBattleId() { return battleId; }

        public User getOpposite() { return opposite; }

    }

}
