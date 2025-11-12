package com.example.demo.controller;

import com.example.demo.domain.dom.AbstractDom;
import com.example.demo.domain.dom.daos.AbstractDomDAO;
import com.example.demo.domain.dom.daos.MainDomDAO;
import com.example.demo.domain.dom.dtos.MainDomDTO;
import com.example.demo.domain.dom.subdom.daos.SubDomDAO;
import com.example.demo.repository.DomRepository;
import com.example.demo.repository.maindom.MainDomRepo;
import com.example.demo.repository.subdom.SubDomRepo;
import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dom/")
public class MainDomController {

	private final DomRepository<? extends AbstractDomDAO> domRepo;
	private final SubDomRepo subDomRepo;
	private final MainDomRepo mainDomRepo;

	public MainDomController(@Qualifier("dom") DomRepository repo, @Qualifier("maindom") MainDomRepo mainDomRepo,
			@Qualifier("subdom") SubDomRepo subDomRepo) {
		this.domRepo = repo;
		this.mainDomRepo = mainDomRepo;
		this.subDomRepo = subDomRepo;
	}

	public DomRepository getRepo() {
		return domRepo;
	}

	@GetMapping
	public List<? extends AbstractDomDAO> getAllMainDom() {
		return mainDomRepo.findAll();
	}

	/*
	 * @PostMapping public MainDomDAO addUser(@RequestBody MainDomDAO mainDom) {
	 * return domRepo.save(mainDom); }
	 */

	@GetMapping("/D3/dom")
	public JsonNode getAllMainDomD3() {
		// pas caster ici mais c'est pas grave
		// List<MainDomDAO> mainDomList = (List<MainDomDAO>) domRepo.findAll();
		List<MainDomDAO> mainDomList = mainDomRepo.findAll();
		// List<SubDomDAO> subDomList = (List<SubDomDAO>) domRepo.findAll();
		return parseJSonForD3(mainDomList);
	}
	
	@GetMapping("/D3/maindom")
	public JsonNode getAllDomD3() {
		// pas caster ici mais c'est pas grave
		// List<MainDomDAO> mainDomList = (List<MainDomDAO>) domRepo.findAll();
		List<? extends AbstractDomDAO> domList = domRepo.findAll();
		// List<SubDomDAO> subDomList = (List<SubDomDAO>) domRepo.findAll();
		return parseJSonForD3(domList);
	}

	private JsonNode parseJSonForD3(List<? extends AbstractDomDAO> mainDomDAOList) {
		MainDomDTO mainDomDTO = new MainDomDTO();
		return mainDomDTO.toD3format(mainDomDAOList);
	}
}
