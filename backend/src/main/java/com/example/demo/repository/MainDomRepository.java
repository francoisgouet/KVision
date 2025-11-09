package com.example.demo.repository;

import com.example.demo.model.MainDom;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MainDomRepository extends JpaRepository<MainDom, Long> {

	Optional<MainDom> findByLib(String lib);

	boolean existsByLib(String lib);
}
