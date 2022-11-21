package com.group10.softwareengineeringmetrics;

import com.group10.softwareengineeringmetrics.models.Commit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/db")
public class DatabaseController {
    @Autowired
    DatabaseApiController databaseApiController;

    @GetMapping("/init-repo")
    public ResponseEntity<HttpStatus> initialiseDatabaseWithStandardRepo(){
        System.out.println("Initialising repo");
        boolean success = databaseApiController.initialiseFromRepo();
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
}
