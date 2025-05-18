package com.lanchonete.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lanchonete.client.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
	
}

