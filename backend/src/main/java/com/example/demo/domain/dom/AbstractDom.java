package com.example.demo.domain.dom;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class AbstractDom {

	@JsonIgnore
	public String lib;
	
	@JsonIgnore
	public List<? extends AbstractDom> dom = new ArrayList<>();

}