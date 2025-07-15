package com.example.splitWiser.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;

import javax.sql.rowset.serial.SerialStruct;
import java.util.List;

@Entity(name = "my_user") // not user, as user is a reserved keyword in SQL
@Data
public class User extends BaseModel{
    private String name;
    private String email;
    private String password;
    @ManyToMany(mappedBy = "members") // means my_group_members table created -- so data for this mapping table inserted only if members is set in group entity
    private List<Group> groups;
    @OneToMany(mappedBy = "user")
    private List<UserExpense> userExpenses;
}
