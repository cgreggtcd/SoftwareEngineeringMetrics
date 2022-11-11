package com.group10.softwareengineeringmetrics.repository;

import com.group10.softwareengineeringmetrics.models.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryRepository extends JpaRepository<Repository, Long> {
}
