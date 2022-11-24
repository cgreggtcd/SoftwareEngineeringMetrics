package com.group10.softwareengineeringmetrics.repository;

import com.group10.softwareengineeringmetrics.models.PullRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PullRequestRepository extends JpaRepository<PullRequest, Long> {
    void deleteAllByRepoId(Long repoId);
}
