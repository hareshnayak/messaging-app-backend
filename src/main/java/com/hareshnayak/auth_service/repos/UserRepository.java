package com.hareshnayak.auth_service.repos;

import com.hareshnayak.auth_service.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
