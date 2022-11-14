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

    @Column(name="isProtected")
    private boolean isProtected;

    public Branch() {}

    public Branch(String name, boolean isProtected){
        this.name = name;
        this.isProtected = isProtected;
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

    public boolean isProtected() {
        return isProtected;
    }

    public void setProtected(boolean isProtected) {
        this.isProtected = isProtected;
    }

    //@Override
    /*public String toString() {
        return "Branch [id=" + id + ", name=" + name + ", protected=" + isProtected + "]";
    }*/
}
