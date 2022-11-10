package com.group10.softwareengineeringmetrics.controller;

import com.group10.softwareengineeringmetrics.models.Repository;
import com.group10.softwareengineeringmetrics.repository.RepositoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "https://localhost:8081")
@RestController
@RequestMapping("/db")
public class RepositoryController {
    @Autowired
    RepositoryRepository repoRepository;

    @GetMapping("/list")
    public ResponseEntity<List<Repository>> listRepos(){
        List<Repository> repos = new ArrayList<>(repoRepository.findAll());
        if (repos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(repos, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Repository> addRepo(@RequestBody Repository repo){
        Repository newRepo = repoRepository.save(repo);

        return new ResponseEntity<>(newRepo, HttpStatus.CREATED);
    }

    @PutMapping("/addMany")
    public ResponseEntity<List<Repository>> addRepos(@RequestBody List<Repository> repos) {
        List<Repository> newRepos = repoRepository.saveAll(repos);
        return new ResponseEntity<>(newRepos, HttpStatus.CREATED);
    }

}
