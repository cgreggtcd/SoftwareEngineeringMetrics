package com.group10.softwareengineeringmetrics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MetricsController {

    @Autowired
    private BranchRepository branchRepository;

    @PostMapping("/add")
    public String addBranch(@RequestParam String name, @RequestParam boolean isProtected) {
        Branch branch = new Branch();
        branch.setName(name);
        branch.setIsProtected(isProtected);
        branchRepository.save(branch);
        return "Added new customer to repo!";
    }

    @GetMapping("/list")
    public Iterable<Branch> getBranches() {
        return branchRepository.findAll();
    }

    @GetMapping("/find/{id}")
    public Branch findBranchById(@PathVariable Integer id) {
        return  branchRepository.findBranchById(id);
    }
}
