package com.example.testproject.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "quote")
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "content")
    @Basic(fetch = FetchType.LAZY)
    private String content;

    @Column(name = "last_change")
    private Date lastChangeTime;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "quote")
    private List<Vote> votes = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Vote currentVote;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getLastChangeTime() {
        return lastChangeTime;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public Vote getCurrentVote() {
        return this.currentVote;
    }

    public void setCurrentVote(Vote currentVote) {
        this.currentVote = currentVote;
    }

    public void newVote(Vote vote) {
        votes.add(vote);
        vote.setQuote(this);
        setCurrentVote(vote);
    }

    @PrePersist
    protected void onCreate() {
        lastChangeTime = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        lastChangeTime = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quote)) return false;
        Quote quote = (Quote) o;
        return Objects.equals(id, quote.id);
    }

    @Override
    public int hashCode() {
        return 42;
    }
}