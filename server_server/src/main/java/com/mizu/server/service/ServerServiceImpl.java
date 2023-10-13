package com.mizu.server.service;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mizu.server.domain.Server;
import com.mizu.server.enumeration.Status;
import com.mizu.server.repo.ServerRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {

	private final ServerRepo serverRepo;
	
	@Override
	public Server create(Server server) {
		log.info("Saving new server: {}", server.getName());
		server.setImageUrl(setServerImageUrl());
		return serverRepo.save(server);
	}

	@Override
	public Server ping(String ipAddress) throws IOException {
		log.info("Pinging server: {}", ipAddress);
		Server server = serverRepo.findByIpAddress(ipAddress);
		InetAddress address = InetAddress.getByName(ipAddress);
		server.setStatus(address.isReachable(2000) ? Status.SERVER_UP : Status.SERVER_DOWN);
		return serverRepo.save(server);
	}
	
	@Override
	public Collection<Server> list(int limit) {
		log.info("Fetching all servers");
		return serverRepo.findAll(PageRequest.of(0, limit)).toList();
	}

	@Override
	public Server get(Long id) {
		log.info("Fetching server by id: {}", id);
		return serverRepo.findById(id).get();
	}

	@Override
	public Server update(Server server) {
		log.info("Updating server: {}", server.getName());
		return serverRepo.save(server);
	}

	@Override
	public Boolean delete(Long id) {
		log.info("Deleting server by ID: {}", id);
		serverRepo.deleteById(id);
		return true;
	}
	
	//Image URL in browser
	private String setServerImageUrl() {
		String[] imageNames = {"server1.png", "server2.png", "server3.png", "server4.png"};
		int index = new Random().nextInt(4);
		return ServletUriComponentsBuilder.fromCurrentContextPath()
										  .path("/server/image/" + imageNames[index])
										  .toString();
	}

}
