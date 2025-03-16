package com.yulken.discord_clone.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class DiscordCloneUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscordCloneUsersApplication.class, args);
	}

}
