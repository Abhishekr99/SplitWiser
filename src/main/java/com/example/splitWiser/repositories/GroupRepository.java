package com.example.splitWiser.repositories;

import com.example.splitWiser.models.Group;
import com.example.splitWiser.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query("SELECT g.members FROM my_group g WHERE g.id = :groupId")
    List<User> findMembersByGroupId(@Param("groupId") Long groupId);
}
