package com.group10.softwareengineeringmetrics.repository;

import com.group10.softwareengineeringmetrics.models.Merge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MergeRepository extends JpaRepository<Merge, Long> {
    void deleteAllByRepoId(Long repoId);
    List<Merge> findByOwnerName(String ownerName);
    List<Merge> findByTime(String time);
}
