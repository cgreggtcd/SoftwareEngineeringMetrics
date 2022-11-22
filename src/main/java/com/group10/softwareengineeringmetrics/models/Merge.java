package com.group10.softwareengineeringmetrics.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/* ReadMe
 * Created when a pull request is aproved. 
 * https://docs.github.com/en/rest/pulls/pulls#check-if-a-pull-request-has-been-merged
 * Can use this ^^ endpoint to check if list of pull requests has been merged and 
 * update the databse of merges accordingly.
 */

@Entity
@Table(name = "merges")
public class Merge {
    @Id
    @Column(name="id")
    private long id;

    @Column(name="time")
    private String time;

    @Column(name="repoFullName")
    private String repoFullName;

    @Column(name="owner")
    private User owner;

    @Column(name="ownerName")
    private String ownerName;
    
    @Column(name="pullNumber")
    private int pullNumber;

    public Merge(){}

    public Merge(long id, String time, String repoFullName, User owner, String ownerName, int pullNumber) {
        this.id = id;
        this.time = time;
        this.repoFullName = repoFullName;
        this.owner = owner;
        this.ownerName = ownerName;
        this.pullNumber = pullNumber;    
    }

    public long getId() { return id; }
    public void setId(long id) { this.id= id; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getRepoFullName() { return repoFullName; }
    public void setRepoFullName (String repoFullName) { this.repoFullName = repoFullName;}

    public User getOwner() { return owner; }
    public void setOwner (User owner) {this.owner = owner;}

    public String getOwnerName() { return ownerName; }
    public void setOwnerName (String ownerName) { this.ownerName = ownerName;}

    public int getPullNumber() { return pullNumber; }
    public void setPullNumber (int pullNumber) { this.pullNumber = pullNumber;}



    @Override
    public String toString() {
        return "Merge [id=" + id + ", time=" + time + ", repoFullName=" + repoFullName +
                ", owner=" + owner + ", ownerName=" + ownerName + ", pullNumber=" + pullNumber + "]";
    }
    
}