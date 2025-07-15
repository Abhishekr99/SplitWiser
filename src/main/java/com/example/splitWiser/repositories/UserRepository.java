package com.example.splitWiser.repositories;

import com.example.splitWiser.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
