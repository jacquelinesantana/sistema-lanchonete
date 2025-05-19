package com.lanchonete.items.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lanchonete.items.model.Items;

@Repository
public interface ItemRepository extends JpaRepository<Items, Long>{

}
