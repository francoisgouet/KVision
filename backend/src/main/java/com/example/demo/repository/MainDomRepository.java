package com.example.demo.repository;

import com.example.demo.model.MainDomDAO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MainDomRepository extends JpaRepository<MainDomDAO, Long> {

	Optional<MainDomDAO> findByLib(String lib);

	boolean existsByLib(String lib);
}
