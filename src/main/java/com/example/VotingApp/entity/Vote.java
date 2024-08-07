package com.example.VotingApp.entity;

import java.util.Date; // Consider using java.util.Date or java.time.LocalDateTime if you need time information
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "User cannot be null")
    private User user;

    @ManyToOne
    @JoinColumn(name = "poll_id", nullable = false)
    @NotNull(message = "Poll cannot be null")
    private Poll poll;

    @Column(nullable = false)
    @NotBlank(message = "Choice cannot be blank")
    @Size(max = 255, message = "Choice cannot exceed 255 characters")
    private String choice;

    @Column(nullable = false)
    @NotNull(message = "Vote date cannot be null")
    private Date voteDate;

    @Column(nullable = false)
    private boolean isConfirmed;

    // Getters and Setters

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

    public Poll getPoll() {
        return poll;
    }
    @Transient // This field will not be persisted in the database
    private String username;

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public Date getVoteDate() {
        return voteDate;
    }

    public void setVoteDate(Date voteDate) {
        this.voteDate = voteDate;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Vote [id=" + id + ", user=" + user + ", poll=" + poll + ", choice=" + choice + ", voteDate=" + voteDate
                + ", isConfirmed=" + isConfirmed + "]";
    }

	
}
