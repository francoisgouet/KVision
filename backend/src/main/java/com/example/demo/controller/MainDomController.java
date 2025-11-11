package com.example.demo.controller;

import com.example.demo.model.MainDomDAO;
import com.example.demo.model.MainDomDTO;
import com.example.demo.repository.MainDomRepository;
import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maindom/")
public class MainDomController {

	private final MainDomRepository repo;

	public MainDomController(MainDomRepository repo) {
		this.repo = repo;
	}

	public MainDomRepository getRepo() {
		return repo;
	}

	@GetMapping
	public List<MainDomDAO> getAllMainDom() {
		return repo.findAll();
	}

	@PostMapping
	public MainDomDAO addUser(@RequestBody MainDomDAO mainDom) {
		return repo.save(mainDom);
	}

	@GetMapping("/D3")
	public JsonNode getAllMainDomD3() {
		List<MainDomDAO> mainDomList = repo.findAll();
		// parseJSonForD3(mainDomList);
		return parseJSonForD3(mainDomList);
	}

	private JsonNode parseJSonForD3(List<MainDomDAO> mainDomDAOList) {
		MainDomDTO mainDomDTO = new MainDomDTO();
		return mainDomDTO.toD3format(mainDomDAOList);
	}
}
