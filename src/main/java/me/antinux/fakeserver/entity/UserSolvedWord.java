package me.antinux.fakeserver.entity;

import javax.persistence.*;

@Entity
@Table(name = "c__user_word")
public class UserSolvedWord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;

    private Long wordId;

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public Long getWordId() { return wordId; }

    public void setWordId(Long wordId) { this.wordId = wordId; }

}
