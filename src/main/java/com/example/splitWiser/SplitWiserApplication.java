package com.example.splitWiser;

import com.example.splitWiser.DTOs.SettleUpRequestDto;
import com.example.splitWiser.DTOs.SettleUpResponseDto;
import com.example.splitWiser.controllers.SettleUpController;
import com.example.splitWiser.services.SettleUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SplitWiserApplication implements CommandLineRunner {

	@Autowired
	DataGenerator dataGenerator;

	@Autowired
	SettleUpController settleUpController;

	public static void main(String[] args) {
		SpringApplication.run(SplitWiserApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//dataGenerator.generateData();

		SettleUpRequestDto settleUpRequestDto = new SettleUpRequestDto();
		settleUpRequestDto.setGroupId(1L);

		SettleUpResponseDto settleUpResponseDto = settleUpController.settleUp(settleUpRequestDto);
		System.out.println(settleUpResponseDto);
	}
}
