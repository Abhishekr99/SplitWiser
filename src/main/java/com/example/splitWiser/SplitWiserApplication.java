package com.example.splitWiser;

import com.example.splitWiser.DTOs.SettleUpRequestDto;
import com.example.splitWiser.DTOs.SettleUpResponseDto;
import com.example.splitWiser.commands.CommandExecutor;
import com.example.splitWiser.commands.SettleUpCommand;
import com.example.splitWiser.controllers.SettleUpController;
import com.example.splitWiser.services.SettleUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@SpringBootApplication
public class SplitWiserApplication implements CommandLineRunner {

	@Autowired
	DataGenerator dataGenerator;

	@Autowired
	SettleUpController settleUpController;

	@Autowired
	CommandExecutor commandExecutor;


	public static void main(String[] args) {
		SpringApplication.run(SplitWiserApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//dataGenerator.generateData();

//		SettleUpRequestDto settleUpRequestDto = new SettleUpRequestDto();
//		settleUpRequestDto.setGroupId(1L);
//
//		SettleUpResponseDto settleUpResponseDto = settleUpController.settleUp(settleUpRequestDto);
//		System.out.println(settleUpResponseDto);

		// Read commands from a file and execute them
		//commandExecutor.registerCommand(new SettleUpCommand()); // Register the SettleUpCommand

		String filePath = "src/main/resources/command_input.txt"; // Path to the file containing commands [note file shd be in resources folder]
		Path path = Paths.get(filePath);

		try {
			List<String> lines = Files.readAllLines(path); //
			for (String line : lines) {
				commandExecutor.execute(line); // Process or print each line
			}
		} catch (IOException e) {
			e.printStackTrace(); // Handle any potential exceptions
		}


	}
}
