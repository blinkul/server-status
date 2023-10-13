package com.mizu.server.controller;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mizu.server.domain.Response;
import com.mizu.server.domain.Server;
import com.mizu.server.enumeration.Status;
import com.mizu.server.service.ServerServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
public class ServerController {

	private final ServerServiceImpl serverService;
	
	@GetMapping("/list")
	public ResponseEntity<Response> getServers() {
		return ResponseEntity.ok(
					Response.builder()
							.timeStamp(now())
							.data(Map.of("servers", serverService.list(30)))
							.message("Servers retrieved")
							.status(OK)
							.statusCode(OK.value())
							.build()
		);
	}
	
	@GetMapping("/ping/{ipAddress}")
	public ResponseEntity<Response> pingServer( @PathVariable("ipAddress") String ipAddress ) throws IOException {
		Server server = serverService.ping(ipAddress);
		
		return ResponseEntity.ok(
					Response.builder()
							.timeStamp(now())
							.data(Map.of("server", server))
							.message(server.getStatus() == Status.SERVER_UP ? "Ping success" : "Ping failed")
							.status(OK)
							.statusCode(OK.value())
							.build()
		);
	}
	
	@PostMapping("/save")
	public ResponseEntity<Response> saveServer( @RequestBody @Valid Server server ) {
		return ResponseEntity.ok(
					Response.builder()
							.timeStamp(now())
							.data(Map.of("server", serverService.create(server)))
							.message("Server created")
							.status(CREATED)
							.statusCode(CREATED.value())
							.build()
		);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<Response> getServer( @PathVariable("id") Long id ) {
		return ResponseEntity.ok(
					Response.builder()
							.timeStamp(now())
							.data(Map.of("server", serverService.get(id)))
							.message("Server retrieved")
							.status(OK)
							.statusCode(OK.value())
							.build()
		);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Response> deleteServer( @PathVariable("id") Long id ) {
		return ResponseEntity.ok(
					Response.builder()
							.timeStamp(now())
							.data(Map.of("deleted", serverService.delete(id)))
							.message("Server deleted")
							.status(OK)
							.statusCode(OK.value())
							.build()
		);
	}
	
	@GetMapping(path = "/image/{fileName}", 
				produces = MediaType.IMAGE_PNG_VALUE)
	public byte[] getServerImage( @PathVariable("fileName") String fileName ) throws IOException {
		Path filePath = Paths.get(System.getProperty("user.home") + "/Downloads/images/" + fileName);
		return Files.readAllBytes(filePath);
	}
	
}
