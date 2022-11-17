package com.group10.softwareengineeringmetrics.models;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    // There is a separate id and user id as there will be separate user rows
    // if a user is in multiple repos
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="userId")
    private long userId;

    @Column(name="username")
    private String username;

    @Column(name="repoFullName")
    private String repoFullName;

    @Column(name="repoId")
    private long repoId;

    public User() {}

    public User(long userId, String username, String repoFullName, long repoId){
        this.userId = userId;
        this.username = username;
        this.repoFullName = repoFullName;
        this.repoId = repoId;
    }

    public long getId() {
        return id;
    }

    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }

    public String getUsername() {
        return username;
    }
    public void setUsername(String name) {
        this.username = name;
    }

    public String getRepoFullName() { return repoFullName; }
    public void setRepoFullName(String repoFullName) { this.repoFullName = repoFullName; }

    public long getRepoId() { return repoId; }
    public void setRepoId(long repoId) { this.repoId = repoId; }

    @Override
    public String toString() {
        return "User [id=" + id + ", userId=" + userId + ", username=" + username + ", repoFullName=" + repoFullName + ", repoId=" + repoId + "]";
    }
}

