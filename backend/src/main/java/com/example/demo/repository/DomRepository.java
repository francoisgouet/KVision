package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.dom.daos.AbstractDomDAO;
import com.example.demo.domain.dom.daos.MainDomDAO;
import com.example.demo.domain.dom.subdom.daos.SubDomDAO;

@Repository("dom")
public interface DomRepository<T extends AbstractDomDAO> extends JpaRepository<T, Long> {

	Optional<AbstractDomDAO> findByLib(String lib);
	
	//List findAll();
	//List<SubDomDAO> findByClass(Class<?>);

	boolean existsByLib(String lib);
}
