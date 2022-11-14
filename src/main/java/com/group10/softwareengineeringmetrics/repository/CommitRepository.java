package com.group10.softwareengineeringmetrics.repository;

import com.group10.softwareengineeringmetrics.models.Commit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommitRepository extends JpaRepository<Commit, Long> {}
