package me.antinux.fakeserver.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "c__word")
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;

    private Long ownerId;

    private int length;

    private int duration;

    private int expGain;

    public int getExpGain() { return expGain; }

    public void setExpGain(int expGain) { this.expGain = expGain; }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public Long getOwnerId() { return ownerId; }

    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }

    public int getLength() { return length; }

    public void setLength(int length) { this.length = length; }

    public int getDuration() { return duration; }

    public void setDuration(int duration) { this.duration = duration; }

}
