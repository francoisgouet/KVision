package com.example.demo.domain.dom.subdom.daos;

import com.example.demo.domain.dom.daos.AbstractDomDAO;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = SubDomDAO.tableName)
public class SubDomDAO extends AbstractDomDAO {
	
	// TODO pourquoi pas annotationprocessor pour eviter de mettre en dur le nom des tables alors que 
	// JPA le fait mais dans mon cas avec sub_dom_dao ou quelque chose du style
	public static final String tableName = "subdom";
}
