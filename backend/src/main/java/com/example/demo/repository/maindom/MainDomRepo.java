package com.example.demo.repository.maindom;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.dom.daos.AbstractDomDAO;
import com.example.demo.domain.dom.daos.MainDomDAO;
import com.example.demo.repository.DomRepository;

@Repository("maindom")
public interface MainDomRepo extends DomRepository<MainDomDAO> {

	//Optional<AbstractDomDAO> findByLib(String lib);
	
	List<MainDomDAO> findAll();

	//boolean existsByLib(String lib);
}