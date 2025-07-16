package com.example.splitWiser.commands;

import com.example.splitWiser.models.Group;
import com.example.splitWiser.models.User;
import com.example.splitWiser.repositories.GroupRepository;
import com.example.splitWiser.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Component
public class RegisterGroupCommand implements Command {

    @Autowired
    GroupRepository groupRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public boolean matches(String input) { // "register-group admin_user_id 'group_name' 'group_description' "
        // Logic to check if the input matches the command pattern
        return input.startsWith("register-group");//basic validation
    }

    @Override
    public void execute(String input) {
        // Regular expression to match the input format
        /*
        This regular expression is designed to match and extract specific parts of a string that follows a particular pattern. Let's break it down piece by piece:
            register-group: This is a literal string and will match the characters "register-group" exactly.
            (space): Matches a literal space character.
            ( and ): These parentheses define a capturing group. Anything matched within these parentheses can be extracted separately as a "group" from the overall match.
            \\S: This is a special character that matches any non-whitespace character. This includes letters, numbers, punctuation, and symbols, according to W3Schools.
            +: This is a quantifier that means "one or more" of the preceding element. So, \\S+ will match one or more non-whitespace characters.Combining these, (\\S+) is the first capturing group and will extract the first sequence of non-whitespace characters following "register-group" and a space.
            (space): Matches another literal space character.
            ': Matches a single quote literally. Since single quotes don't have special meaning in regex, they can be included directly in the pattern.
            ( and ): This is the second capturing group.
            [^']: This is a character class or "bracket expression". The ^ inside the square brackets negates the set, so [^'] matches any single character that is not a single quote.
            +: Again, this quantifier means "one or more". So, [^']+ will match one or more characters that are not single quotes.Combined, '([^']+)' is the second capturing group and will extract the string between the single quotes.
            (space): Matches another literal space character.
            ': Matches a single quote literally.
            ( and ): This is the third capturing group.
            [^']+: Similar to the second group, this will match one or more characters that are not single quotes.
            ': Matches the final single quote literally.
         */
        String regex = "register-group (\\S+) '([^']+)' '([^']+)'";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            Long adminUserId = Long.parseLong(matcher.group(1)); // Extract admin_user_id
            String groupName = matcher.group(2);   // Extract group_name
            String groupDescription = matcher.group(3); // Extract group_description

            Group group = new Group();
            Optional<User> optAdmin = userRepository.findById(adminUserId);
            if(optAdmin.isEmpty()) {
                System.out.println("Admin user with ID " + adminUserId + " does not exist.");
                return;
            }
            else{
                group.setAdmin(optAdmin.get());
                group.setName(groupName);
                group.setDescription(groupDescription);

                groupRepository.save(group);
                System.out.println("Group '" + groupName + "' registered successfully with ID: " + group.getId());
            }


        } else {
            System.out.println("Invalid command format. Usage: register-group admin_user_id 'group_name' 'group_description'");
        }
    }
}
