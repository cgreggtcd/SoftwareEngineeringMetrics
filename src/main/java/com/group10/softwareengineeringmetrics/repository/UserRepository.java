package com.group10.softwareengineeringmetrics.repository;

import com.group10.softwareengineeringmetrics.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
