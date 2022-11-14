package com.group10.softwareengineeringmetrics.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "commits")
public class Commit {
    @Id
    @Column(name="sha")
    private String sha;

    @Column(name="branch")
    private String branch;

    @Column(name="time")
    private String time;

    @Column(name="authorName")
    private String authorName;

    @Column(name="authorId")
    private long authorId;

    @Column(name="additions")
    private int additions;

    @Column(name="deletions")
    private int deletions;

    @Column(name="changes")
    private int changes;

    public Commit(String sha, String branch, String time, String authorName, long authorId, int additions, int deletions, int changes) {
        this.sha = sha;
        this.branch = branch;
        this.time = time;
        this.authorName = authorName;
        this.authorId = authorId;
        this.additions = additions;
        this.deletions = deletions;
        this.changes = changes;
    }

    public String getSha() { return sha; }
    public void setSha(String sha) { this.sha = sha; }

    public String getBranch() { return branch; }
    public void setBranch(String branch) { this.branch = branch; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public long getAuthorId() { return authorId; }
    public void setAuthorId(long authorId) { this.authorId = authorId; }

    public int getAdditions() { return additions; }
    public void setAdditions(int additions) { this.additions = additions; }

    public int getDeletions() { return deletions; }
    public void setDeletions(int deletions) { this.deletions = deletions; }

    public int getChanges() { return changes; }
    public void setChanges(int changes) { this.changes = changes; }
}
