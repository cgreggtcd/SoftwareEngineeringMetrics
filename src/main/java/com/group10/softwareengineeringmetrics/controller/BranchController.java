package com.group10.softwareengineeringmetrics.controller;

import com.group10.softwareengineeringmetrics.models.Branch;
import com.group10.softwareengineeringmetrics.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "https://localhost:8081")
@RestController
@RequestMapping("/db")
public class BranchController {
    @Autowired
    BranchRepository branchRepository;

    @GetMapping("/list")
    public ResponseEntity<List<Branch>> listBranches(){
        List<Branch> branches = new ArrayList<>(branchRepository.findAll());
        if (branches.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(branches, HttpStatus.OK);
    }

    @PutMapping("/add")
    public ResponseEntity<Branch> addBranch(@RequestBody Branch branch){
        Branch newBranch = branchRepository.save(branch);

        return new ResponseEntity<>(newBranch, HttpStatus.CREATED);
    }

    @PutMapping("/addMany")
    public ResponseEntity<List<Branch>> addBranches(@RequestBody List<Branch> branches) {
        List<Branch> newBranches = branchRepository.saveAll(branches);
        return new ResponseEntity<>(newBranches, HttpStatus.CREATED);
    }

}
