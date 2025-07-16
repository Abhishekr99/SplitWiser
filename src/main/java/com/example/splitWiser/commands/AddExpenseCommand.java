package com.example.splitWiser.commands;

import com.example.splitWiser.models.Expense;
import com.example.splitWiser.models.Group;
import com.example.splitWiser.models.User;
import com.example.splitWiser.models.UserExpense;
import com.example.splitWiser.models.enums.ExpenseType;
import com.example.splitWiser.models.enums.UserExpenseType;
import com.example.splitWiser.repositories.ExpenseRepository;
import com.example.splitWiser.repositories.GroupRepository;
import org.hibernate.annotations.processing.Find;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class AddExpenseCommand implements Command {

    @Autowired
    GroupRepository groupRepository;
    @Autowired
    ExpenseRepository expenseRepository;

    @Override
    public boolean matches(String input) {//add-group-expense group_id 'exp desc' total_amount paid-by 1 2 3 for 1 2 3 4 5
        // Check if the input matches the command pattern for adding an expense
        return input.startsWith("add-group-expense");
    }

    /*
            Here is a breakdown of the regex:
            String regex = "add-expense\\s(\\d+)\\s'(.*?)'\\s(\\d+)\\s+paid-by\\s((?:\\d+\\s?)+)\\s+for\\s((?:\\d+\\s?)+)";

            ### Explanation of Each Part:

            1. **`add-expense\\s`**:
               - Matches the literal text `add-expense` followed by a single whitespace (`\\s`).

            2. **`(\\d+)`**:
               - Captures the `group_id` as a sequence of one or more digits.
               - `\\d`: Matches a digit (0-9).
               - `+`: Matches one or more occurrences of the preceding pattern.

            3. **`\\s'(.*?)'`**:
               - Matches a single whitespace (`\\s`) followed by a quoted string.
               - `'`: Matches the opening single quote.
               - `(.*?)`: Captures any characters (non-greedy) inside the quotes.
               - `'`: Matches the closing single quote.

            4. **`\\s(\\d+)`**:
               - Matches a single whitespace (`\\s`) followed by the `total_amount` as a sequence of one or more digits.

            5. **`\\s+paid-by\\s`**:
               - Matches one or more whitespace characters (`\\s+`), followed by the literal text `paid-by`, and then a single whitespace (`\\s`).

            6. **`((?:\\d+\\s?)+)`**:
               - Captures the list of IDs after `paid-by`.
               - `\\d+`: Matches one or more digits.
               - `\\s?`: Matches zero or one whitespace after each number.
               - `(?:...)`: A non-capturing group to group the pattern `\\d+\\s?`.
               - `+`: Matches one or more occurrences of the group (i.e., multiple numbers).

            7. **`\\s+for\\s`**:
               - Matches one or more whitespace characters (`\\s+`), followed by the literal text `for`, and then a single whitespace (`\\s`).

            8. **`((?:\\d+\\s?)+)`**:
               - Captures the list of IDs after `for`.
               - Same logic as the `paid-by` numbers group.

            ### Capturing Groups:
            1. **Group 1**: Captures the `group_id` (e.g., `2`).
            2. **Group 2**: Captures the expense description (e.g., `'day 1 breakfast'`).
            3. **Group 3**: Captures the `total_amount` (e.g., `2000`).
            4. **Group 4**: Captures the list of IDs after `paid-by` (e.g., `5 6`).
            5. **Group 5**: Captures the list of IDs after `for` (e.g., `5 6 7 8`).


     */
    @Override
    public void execute(String input) {
        // Logic to add an expense
        String regex = "add-group-expense\\s(\\d+)\\s'(.*?)'\\s(\\d+)\\s+paid-by\\s((?:\\d+\\s?)+)\\s+for\\s((?:\\d+\\s?)+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        Long groupId = 0L;
        String expenseDescription = null;
        int totalAmount = 0;
        List<Long> paidByIds = new ArrayList<>();
        List<Long> paidForIds = new ArrayList<>();

        if (matcher.find()) {
            groupId = Long.parseLong(matcher.group(1)); // Captures the group_id
            expenseDescription = matcher.group(2); // Captures the description
            totalAmount = Integer.parseInt(matcher.group(3)); // Captures the total amount
            paidByIds = Arrays.stream(matcher.group(4).trim().split("\\s+"))
                    .map(Long::parseLong)
                    .collect(Collectors.toList()); // Captures paid-by IDs
            paidForIds = Arrays.stream(matcher.group(5).trim().split("\\s+"))
                    .map(Long::parseLong)
                    .collect(Collectors.toList()); // Captures for IDs
        }
        else{
            System.out.println("Invalid command format. Please use: add-group-expense group_id 'exp desc' total_amount paid-by 1 2 3 for 1 2 3 4 5");
            return;
        }


        Optional<Group> optGroup = groupRepository.findById(groupId);
        if(optGroup.isEmpty()) {
            System.out.println("Group with ID " + groupId + " does not exist.");
            return;
        }
        Group group = optGroup.get();
        Expense expense = new Expense();
        expense.setDescription( expenseDescription);
        expense.setTotalAmount(totalAmount);
        //expense.setUserExpenses();
        expense.setGroup(group);
        expense.setExpenseType(ExpenseType.EXPENDITURE);

        List<UserExpense> userExpenses = new ArrayList<>();
        for(Long paidById : paidByIds) {
            Optional<User> paidByUser = group.getMembers().stream()
                    .filter(user -> user.getId().equals(paidById))
                    .findFirst();
            for(Long paidToId : paidForIds) {
                Optional<User> paidForUser = group.getMembers().stream()
                        .filter(user -> user.getId().equals(paidToId))
                        .findFirst();

                if(paidByUser.isEmpty() || paidForUser.isEmpty()) {
                    System.out.println("User with ID " + paidById + " or " + paidToId + " does not exist in the group.");
                    return;
                }

                UserExpense userExpenseBy = new UserExpense();
                userExpenseBy.setUser(paidByUser.get());
                double amountPerUser = totalAmount / (1.0*paidByIds.size()*paidForIds.size()); //assuming equal split
                userExpenseBy.setAmount(amountPerUser);
                userExpenseBy.setExpense(expense);
                userExpenseBy.setUserExpenseType(UserExpenseType.PAID_BY);

                UserExpense userExpenseFor = new UserExpense();
                userExpenseFor.setUser(paidForUser.get());
                userExpenseFor.setAmount(amountPerUser);
                userExpenseFor.setExpense(expense);
                userExpenseFor.setUserExpenseType(UserExpenseType.PAID_FOR);

                userExpenses.add(userExpenseBy);
                userExpenses.add(userExpenseFor);

            }
        }

        expense.setUserExpenses(userExpenses);
        expenseRepository.save(expense);

        System.out.println("Expense '"+ expense.getDescription()+"' with  added successfully to group " + group.getName() + " with ID " + groupId);

    }
}
