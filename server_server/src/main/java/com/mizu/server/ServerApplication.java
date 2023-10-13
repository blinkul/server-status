package com.mizu.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.mizu.server.domain.Server;
import com.mizu.server.enumeration.Status;
import com.mizu.server.repo.ServerRepo;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}
	
	@Bean
	CommandLineRunner run(ServerRepo serverRepo) {
		return args -> {
			serverRepo.save(new Server(
						null,
						"192.168.1.160",
						"Ubuntu Linux",
						"16 GB",
						"Personal PC",
						"http://localhost:8080/server/image/server1.png", 
						Status.SERVER_UP
			));
			
			serverRepo.save(new Server(
					null,
					"142.251.214.142",
					"Google",
					"Over 9999 GB",
					"Google web page",
					"http://localhost:8080/server/image/server2.png", 
					Status.SERVER_UP
			));
			
			serverRepo.save(new Server(
					null,
					"192.168.1.185",
					"Fedore Linux",
					"32 GB",
					"Dell tower",
					"http://localhost:8080/server/image/server3.png", 
					Status.SERVER_DOWN
			));
			
			serverRepo.save(new Server(
					null,
					"192.168.1.64",
					"MS 2008",
					"64 GB",
					"Web Server",
					"http://localhost:8080/server/image/server4.png", 
					Status.SERVER_DOWN
			));
		};
	}

}
