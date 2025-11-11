package com.example.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.MainDomDAO;
import com.example.demo.repository.MainDomRepository;

@Transactional
@Service
public class MainDomImporterService {
	// Service dédié à la lecture et au traitement des données

	private final MainDomRepository mainDomRepository;

	public MainDomImporterService(MainDomRepository mainDomRepository) {
		this.mainDomRepository = mainDomRepository;
	}

	public void importFromText(Path pathToFile) {
		try (Stream<String> lines = Files.lines(pathToFile)) {
			lines.forEach(line -> {
				if (line.matches(".*\\d.*")) {
					// Pattern pattern = Pattern.compile("[^\\*+]");

					String[] fields = line.split("\\*");

					String number = line.replaceAll("^[0-9]", line);

					MainDomDAO mainDom = new MainDomDAO();
					mainDom.setLib(fields[2]);

					try {
						saveIfNotExists(mainDom);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});
		} catch (IOException e) {
			throw new RuntimeException("Erreur lecture fichier text", e);
		}
	}

	@Transactional
	public Long saveIfNotExists(MainDomDAO mainDom) {
		// Vérifie si un client avec cet email existe déjà (email est unique)
		Optional<MainDomDAO> md_check = mainDomRepository.findByLib(mainDom.getLib());
		String key = mainDom.getLib().toString().intern();
		MainDomDAO md_exists;
		// synchronized (key) {
		// Exists already?
		if (md_check.isEmpty()) {
			// New MainDomDAO
			md_exists = mainDomRepository.save(mainDom);
		} else {
			// Update existing md
			md_exists = md_check.get();
			// md_exists.setId(mainDom.getId());
			md_exists.setLib(mainDom.getLib());
			mainDomRepository.save(md_exists);
		}
		// }

		return md_exists.getId();
	}
}