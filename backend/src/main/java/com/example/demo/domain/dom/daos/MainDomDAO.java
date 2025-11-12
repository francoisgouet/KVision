package com.example.demo.domain.dom.daos;

import jakarta.persistence.*;

@Entity
@Table(name = MainDomDAO.tableName)
public class MainDomDAO extends AbstractDomDAO {

	public static final String tableName = "maindom";
}
