package com.group10.softwareengineeringmetrics;

import com.group10.softwareengineeringmetrics.models.Branch;
import com.group10.softwareengineeringmetrics.models.Commit;
import com.group10.softwareengineeringmetrics.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/db")
public class DatabaseController {
    @Autowired
    DatabaseApiController databaseApiController;
    Burnout burnout;

    @GetMapping("/init-repo/{user}/{reponame}")
    public ResponseEntity<HttpStatus> initialiseDatabaseWithStandardRepo(@PathVariable("user") String user, @PathVariable("reponame") String repoName){
        boolean success = databaseApiController.initialiseFromRepo(user, repoName);
        if (success){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/delete-repo/{user}/{reponame}")
    public ResponseEntity<HttpStatus> deleteRepo(@PathVariable("user") String user, @PathVariable("reponame") String repoName) {
        databaseApiController.clearRepositoryReferences(user+"/"+repoName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/reload-repo/{user}/{reponame}")
    public ResponseEntity<HttpStatus> reloadRepo(@PathVariable("user") String user, @PathVariable("reponame") String repoName){
        databaseApiController.clearRepositoryReferences(user+"/"+repoName);
        boolean success = databaseApiController.initialiseFromRepo(user, repoName);
        if (success){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/get-commits")
    public ResponseEntity<List<Commit>> getCommits(){
        List<Commit> commits = databaseApiController.getCommits();
        return new ResponseEntity<>(commits, HttpStatus.OK);
    }
    @GetMapping("/get-branches")
    public ResponseEntity<List<Branch>> getBranches(){
        List<Branch> branches = databaseApiController.getBranches();
        return new ResponseEntity<>(branches, HttpStatus.OK);
    }
    @GetMapping("/get-users")
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = databaseApiController.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/test-burnout")
    public ResponseEntity<List<String>> getBurnout(){
        burnout = new Burnout();
        List<String> topAuthors = Arrays.asList(burnout.getBurnoutAuthors());
        return new ResponseEntity<>(topAuthors, HttpStatus.OK);
    }
}
