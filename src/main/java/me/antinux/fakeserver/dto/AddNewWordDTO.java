package me.antinux.fakeserver.dto;

import org.springframework.stereotype.Component;

@Component
public class AddNewWordDTO {

    private String content;

    private Long userId;

    public String getContent() { return content; }

    public Long getUserId() { return userId; }

}
