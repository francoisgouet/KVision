package com.example.demo.repository.subdom;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.dom.subdom.daos.SubDomDAO;
import com.example.demo.repository.DomRepository;

@Repository("subdom")
public interface SubDomRepo extends DomRepository<SubDomDAO> {
	List<SubDomDAO> findAll();

}
