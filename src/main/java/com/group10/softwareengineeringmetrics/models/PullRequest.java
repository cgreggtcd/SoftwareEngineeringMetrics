package com.group10.softwareengineeringmetrics.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pull_requests")
public class PullRequest {
    @Id
    @Column(name="id")
    private long id;

    @Column(name="state")
    private String state;

    @Column(name="repoFullName")
    private String repoFullName;

    @Column(name="repoId")
    private long repoId;

    @Column(name="createdAt")
    private String createdAt;

    // This should include the time it was merged or closed if the pull request did not go through
    @Column(name="closedAt")
    private String closedAt;

    // This is "head" in the API call
    @Column(name="branchFrom")
    private String branchFrom;

    // This is "base" in the API call
    @Column(name="branchTo")
    private String branchTo;

    public PullRequest(long id, String state, String repoFullName, long repoId, String createdAt, String closedAt, String branchFrom, String branchTo) {
        this.id = id;
        this.state = state;
        this.repoFullName = repoFullName;
        this.repoId = repoId;
        this.createdAt = createdAt;
        this.closedAt = closedAt;
        this.branchFrom = branchFrom;
        this.branchTo = branchTo;
    }

    public PullRequest() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRepoFullName() {
        return repoFullName;
    }

    public void setRepoFullName(String repoFullName) {
        this.repoFullName = repoFullName;
    }

    public long getRepoId() {
        return repoId;
    }

    public void setRepoId(long repoId) {
        this.repoId = repoId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(String closedAt) {
        this.closedAt = closedAt;
    }

    public String getBranchFrom() {
        return branchFrom;
    }

    public void setBranchFrom(String branchFrom) {
        this.branchFrom = branchFrom;
    }

    public String getBranchTo() {
        return branchTo;
    }

    public void setBranchTo(String branchTo) {
        this.branchTo = branchTo;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", state=" + state + ", repoFullName=" + repoFullName + ", repoId=" + repoId +
                ", createdAt=" + createdAt +  ", closedAt=" + closedAt +  ", branchFrom=" + branchFrom +
                ", branchTo=" + branchTo + "]";
    }
}
