package com.group10.softwareengineeringmetrics.models;

public class Commit {
    Repository repo;
    //Branch branch;
    User committer;
    String message;

    public Commit(Repository repo, User committer, String message){
        this.repo = repo;
        this.committer = committer;
        this.message = message;
    }

    public Repository getRepo() {
        return repo;
    }

    public void setRepo(Repository repo) {
        this.repo = repo;
    }

    public User getCommitter() {
        return committer;
    }

    public void setCommitter(User committer) {
        this.committer = committer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
