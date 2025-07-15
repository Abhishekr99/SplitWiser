package com.example.splitWiser.models;

import com.example.splitWiser.repositories.GroupRepository;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Entity(name = "my_group") // not group, as group is a reserved keyword in SQL
@Data
public class Group extends BaseModel{
    private String name;
    private String description;
    @ManyToMany//(mappedBy = "groups") //means my_user_groups table created -- so data for this mapping table inserted only if groups is set in user entity
    private List<User> members;
    @ManyToOne
    private User admin;
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Expense> expenses;
}
