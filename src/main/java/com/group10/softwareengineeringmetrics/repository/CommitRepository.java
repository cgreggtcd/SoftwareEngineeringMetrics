package com.group10.softwareengineeringmetrics.repository;

import com.group10.softwareengineeringmetrics.models.Commit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommitRepository extends JpaRepository<Commit, Long> {
    List<Commit> findByAuthorName(String authorName);
    List<Commit> findByAuthorId(long authorId);
}
