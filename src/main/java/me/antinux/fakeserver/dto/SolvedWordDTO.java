package me.antinux.fakeserver.dto;

import org.springframework.stereotype.Component;

@Component
public class SolvedWordDTO {

    private Long wordId;

    private Long userId;

    public Long getWordId() { return wordId; }

    public Long getUserId() { return userId; }

}
