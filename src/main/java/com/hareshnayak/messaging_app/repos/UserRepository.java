package com.hareshnayak.messaging_app.repos;

import com.hareshnayak.messaging_app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
