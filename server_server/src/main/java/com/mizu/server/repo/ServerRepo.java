package com.mizu.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mizu.server.domain.Server;

public interface ServerRepo extends JpaRepository<Server, Long> {

	Server findByIpAddress(String ipAddress);
	
}
