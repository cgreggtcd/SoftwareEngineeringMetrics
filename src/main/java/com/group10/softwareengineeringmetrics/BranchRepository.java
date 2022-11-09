package com.group10.softwareengineeringmetrics;

import org.springframework.data.repository.CrudRepository;

public interface BranchRepository extends CrudRepository<Branch, Integer> {
    Branch findBranchById(Integer id);
}
