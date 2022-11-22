package com.group10.softwareengineeringmetrics.repository;

import com.group10.softwareengineeringmetrics.models.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    void deleteAllByRepoId(Long repoId);
}
