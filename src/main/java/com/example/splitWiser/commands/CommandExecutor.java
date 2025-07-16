package com.example.splitWiser.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandExecutor {
    @Autowired
    List<Command> commands;

//    @Autowired -- even this works, to inject objects for registry pattern
//    public CommandExecutor(List<Command> commands) {
//        this.commands = commands;
//    }

    public void registerCommand(Command command){
        commands.add(command);
    }

    public void deregisterCommmand(Command command){
        commands.remove(command);
    }

    public void execute(String input){
        boolean commandFound = false;
        for(Command command : commands){
            if(command.matches(input)){
                commandFound = true;
                command.execute(input);
            }
//            else{
//                System.out.println("Command not recognized: " + input);
//            }
        }
        if(!commandFound){
            System.out.println("Command not recognized: " + input);
        }
    }
}
