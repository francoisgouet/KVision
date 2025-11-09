package com.example.demo.controller;

import com.example.demo.model.MainDom;
import com.example.demo.repository.MainDomRepository;
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
    public List<MainDom> getAllMainDom() {
        return repo.findAll();
    }

    @PostMapping
    public MainDom addUser(@RequestBody MainDom mainDom) {
        return repo.save(mainDom);
    }
}
