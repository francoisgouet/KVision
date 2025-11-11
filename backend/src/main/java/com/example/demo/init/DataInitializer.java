package com.example.demo.init;

import java.nio.file.Paths;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.demo.service.MainDomImporterService;

//Classe de démarrage pour lancer l'import au démarrage
@Component
public class DataInitializer implements ApplicationRunner {

	private final MainDomImporterService importService;

	public DataInitializer(MainDomImporterService importService) {
		this.importService = importService;
	}

	@Override
	public void run(ApplicationArguments args) {
		importService.importFromText(Paths.get("../domaines.txt"));
	}
}