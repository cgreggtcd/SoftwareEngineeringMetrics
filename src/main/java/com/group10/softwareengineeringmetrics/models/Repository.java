package com.group10.softwareengineeringmetrics.models;

import javax.persistence.*;

@Entity
@Table(name = "repositories")
public class Repository {
    @Id
    private long id;

    @Column(name="fullName")
    private String fullName;

    //private List<Branch> branches;
    //private List<String> languages;
    //private List<User> collaborators;
    //private User owner;
    //private List<Commit> commits;


    public Repository() {
    }

    public Repository(long id, String full_name){
        this.fullName = full_name;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String name) {
        this.fullName = name;
    }
    
    //public User getOwner() { return owner; }
    //public void setOwner(User owner) { this.owner = owner; }

    //public List<Branch> getBranches() { return branches; }
    //public void addBranch(Branch branch) { branches.add(branch); }

    //public List<User> getCollaborators() { return collaborators; }
    //public void addCollaborator(User user) { collaborators.add(user); }

    @Override
    public String toString() {
        return "Repository [id=" + id + ", name=" + fullName + "]";
    }
}
