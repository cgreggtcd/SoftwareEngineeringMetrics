package com.group10.softwareengineeringmetrics.repository;

import com.group10.softwareengineeringmetrics.models.Commit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommitRepository extends JpaRepository<Commit, Long> {
    void deleteAllByRepoId(Long repoId);
    List<Commit> findByAuthorName(String authorName);
}
