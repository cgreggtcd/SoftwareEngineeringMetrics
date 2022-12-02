package com.group10.softwareengineeringmetrics.models;

import javax.persistence.*;

@Entity
@Table(name = "branches")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="repoFullName")
    private String repoFullName;

    @Column(name="repoId")
    private long repoId;

    public Branch() {
        this.name = "error";
        this.repoFullName = "errorreponame";
        this.repoId = 9999;
    }

    public Branch(String name, String repoFullName, long repo_id){
        this.name = name;
        this.repoFullName = repoFullName;
        this.repoId = repo_id;
    }

    public long getId() { return id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRepoFullName() {
        return repoFullName;
    }

    public void setRepoFullName(String repoFullName) {
        this.repoFullName = repoFullName;
    }

    public long getRepoId() { return repoId; }

    public void setRepoId(long repoId) { this.repoId = repoId; }

    @Override
    public String toString() {
        return "Branch [name=" + name + ", repoFullName=" + repoFullName + ", repoId=" + repoId + "]";
    }
}