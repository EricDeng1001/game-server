package me.antinux.fakeserver.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "c__user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;

    private String nickname;

    private int level = 1;

    private int exp = 0;

    private int solvedNumber = 0;

    public String getNickname() { return nickname; }

    public void setNickname(String nickname) { this.nickname = nickname; }

    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public int getLevel() { return level; }

    public void setLevel(int level) { this.level = level; }

    public int getExp() { return exp; }

    public void setExp(int exp) { this.exp = exp; }

    public int getSolvedNumber() { return solvedNumber; }

    public void setSolvedNumber(int solvedNumber) { this.solvedNumber = solvedNumber; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) {
        this.password = password;
    }

}
