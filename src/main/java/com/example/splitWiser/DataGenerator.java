package com.example.splitWiser;

import com.example.splitWiser.models.Expense;
import com.example.splitWiser.models.Group;
import com.example.splitWiser.models.User;
import com.example.splitWiser.models.UserExpense;
import com.example.splitWiser.models.enums.ExpenseType;
import com.example.splitWiser.models.enums.UserExpenseType;
import com.example.splitWiser.repositories.ExpenseRepository;
import com.example.splitWiser.repositories.GroupRepository;
import com.example.splitWiser.repositories.UserRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Lombok;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class DataGenerator {

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    ExpenseRepository expenseRepository;
    public void generateData(){



        //creating 4 users
        User user1 = new User();
        user1.setName("abi");
        user1.setEmail("abi@gmail.com");
        user1.setPassword("pass");
//        user.setGroups(new ArrayList<>()); -- ini null --no group
//        user.setUserExpenses(new ArrayList<>()); -- ini null
        userRepository.save(user1);

        User user2 = new User();
        user2.setName("sankey");
        user2.setEmail("sankey@gmail.com");
        user2.setPassword("pass2");
        userRepository.save(user2);

        User user3 = new User();
        user3.setName("patel");
        user3.setEmail("patel@gmail.com");
        user3.setPassword("pass3");
        userRepository.save(user3);

        User user4= new User();
        user4.setName("juji");
        user4.setEmail("juji@gmail.com");
        user4.setPassword("pass4");
        userRepository.save(user4);

        //creating group
        Group group = new Group();
        group.setName("Goa 2025");
        group.setDescription("Sankey homecoming trip to Goa in 2025");
        group.setMembers(List.of(user1, user2, user3, user4));
        group.setAdmin(user1);
//        group.setExpenses(new ArrayList<>()); -- ini no expenses, null
        groupRepository.save(group);

        //creating expenses
        Expense expense1 = new Expense();
        expense1.setDescription("Flight tickets to Goa");
        expense1.setTotalAmount(10);
            UserExpense userExpense1 = new UserExpense();
            userExpense1.setUser(user1);
            userExpense1.setAmount(10);
            userExpense1.setExpense(expense1);
            userExpense1.setUserExpenseType(UserExpenseType.PAID_BY);

            UserExpense userExpense2 = new UserExpense();
            userExpense2.setUser(user2);
            userExpense2.setAmount(10);
            userExpense2.setExpense(expense1);
            userExpense2.setUserExpenseType(UserExpenseType.PAID_FOR);

        expense1.setUserExpenses(List.of(userExpense1, userExpense2));
        expense1.setGroup(group);
        expense1.setExpenseType(ExpenseType.EXPENDITURE);
        expenseRepository.save(expense1);

        Expense expense2 = new Expense();
        expense2.setDescription("Car rental in Goa");
        expense2.setTotalAmount(10);
            UserExpense userExpense3 = new UserExpense();
            userExpense3.setUser(user2);
            userExpense3.setAmount(10);
            userExpense3.setExpense(expense2);
            userExpense3.setUserExpenseType(UserExpenseType.PAID_BY);

            UserExpense userExpense4 = new UserExpense();
            userExpense4.setUser(user3);
            userExpense4.setAmount(10);
            userExpense4.setExpense(expense2);
            userExpense4.setUserExpenseType(UserExpenseType.PAID_FOR);

        expense2.setUserExpenses(List.of(userExpense3, userExpense4));
        expense2.setGroup(group);
        expense2.setExpenseType(ExpenseType.EXPENDITURE);
        expenseRepository.save(expense2);

        Expense expense3 = new Expense();
        expense3.setDescription("Breakfast day 1");
        expense3.setTotalAmount(10);
            UserExpense userExpense5 = new UserExpense();
            userExpense5.setUser(user3);
            userExpense5.setAmount(10);
            userExpense5.setExpense(expense3);
            userExpense5.setUserExpenseType(UserExpenseType.PAID_BY);

            UserExpense userExpense6 = new UserExpense();
            userExpense6.setUser(user1);
            userExpense6.setAmount(10);
            userExpense6.setExpense(expense3);
            userExpense6.setUserExpenseType(UserExpenseType.PAID_FOR);

        expense3.setUserExpenses(List.of(userExpense5, userExpense6));
        expense3.setGroup(group);
        expense3.setExpenseType(ExpenseType.EXPENDITURE);
        expenseRepository.save(expense3);

        Expense expense4 = new Expense();
        expense4.setDescription("Lunch day 2");
        expense4.setTotalAmount(10);
            UserExpense userExpense7 = new UserExpense();
            userExpense7.setUser(user1);
            userExpense7.setAmount(10);
            userExpense7.setExpense(expense4);
            userExpense7.setUserExpenseType(UserExpenseType.PAID_BY);

            UserExpense userExpense8 = new UserExpense();
            userExpense8.setUser(user4);
            userExpense8.setAmount(10);
            userExpense8.setExpense(expense4);
            userExpense8.setUserExpenseType(UserExpenseType.PAID_FOR);

        expense4.setUserExpenses(List.of(userExpense7, userExpense8));
        expense4.setGroup(group);
        expense4.setExpenseType(ExpenseType.EXPENDITURE);
        expenseRepository.save(expense4);






    }
}
