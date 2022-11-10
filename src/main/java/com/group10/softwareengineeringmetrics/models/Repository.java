package com.group10.softwareengineeringmetrics.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "repositories")
public class Repository {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="readMe")
    private String readMe;

//    @Column(name="releases")
//    private int releases;

    //THIS PROBABLY DOESN'T WORK FOR TABLE FORMAT IN OUR CURRENT DATABASE
    // HOPEFULLY CAN BE IMPLEMENTED WHEN WE GET API
    private List<Branch> branches;
    //private List<String> languages;
    private List<User> collaborators;
    //private List<Commit> commits;


    public Repository() {

    }

    public Repository(String name, String readMe){
        this.name = name;
        this.readMe = readMe;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReadMe() {
        return readMe;
    }

    public void setReadMe(String readMe) {
        this.readMe = readMe;
    }

    public List<Branch> getBranches() { return branches; }

    public void addBranch(Branch branch) { branches.add(branch); }

    public List<User> getCollaborators() { return collaborators; }

    public void addCollaborator(User user) { collaborators.add(user); }

    @Override
    public String toString() {
        return "Repository [id=" + id + ", name=" + name + ", readMe=" + readMe + "]";
    }
}
