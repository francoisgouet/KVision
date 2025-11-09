package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "maindom")
public class MainDom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String lib;
    // Garantit que deux domaines ne peuvent avoir le même lib
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLib() {
		return lib;
	}
	public void setLib(String lib) {
		this.lib = lib;
	}

    // Getters and setters
}
