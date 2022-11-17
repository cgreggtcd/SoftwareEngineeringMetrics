package com.group10.softwareengineeringmetrics.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "repositories")
public class Repository {
    @Id
    private long id;

    @Column(name="full_name")
    private String full_name;

    //private List<Branch> branches;
    //private List<String> languages;
    //private List<User> collaborators;
    //private User owner;
    //private List<Commit> commits;


    public Repository() {
    }

    public Repository(long id, String full_name){
        this.full_name = full_name;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return full_name;
    }

    public void setName(String name) {
        this.full_name = name;
    }
    
    //public User getOwner() { return owner; }
    //public void setOwner(User owner) { this.owner = owner; }

    //public List<Branch> getBranches() { return branches; }
    //public void addBranch(Branch branch) { branches.add(branch); }

    //public List<User> getCollaborators() { return collaborators; }
    //public void addCollaborator(User user) { collaborators.add(user); }

    @Override
    public String toString() {
        return "Repository [id=" + id + ", name=" + full_name + "]";
    }
}
