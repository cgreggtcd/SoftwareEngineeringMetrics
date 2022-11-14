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

    public Repository(){}
    public Repository(long id, String full_name){
        this.id = id;
        this.full_name = full_name;
    }
    @Override
    public String toString() {
        return "Repository [id=" + id + ", name=" + full_name + "]";
    }
}
