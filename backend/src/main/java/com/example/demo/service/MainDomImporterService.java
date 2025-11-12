package com.example.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.dom.daos.AbstractDomDAO;
import com.example.demo.domain.dom.daos.MainDomDAO;
import com.example.demo.domain.dom.subdom.daos.SubDomDAO;
import com.example.demo.repository.DomRepository;

@Transactional
@Service
public class MainDomImporterService {
	// Service dédié à la lecture et au traitement des données

	private final DomRepository<AbstractDomDAO> domRepository;

	public MainDomImporterService(@Qualifier("dom")DomRepository domRepository) {
		this.domRepository = domRepository;
	}

	public void importFromText(Path pathToFile) {
		try (Stream<String> lines = Files.lines(pathToFile)) {
			lines.forEach(line -> {
				if (!line.isEmpty()) {
					if (line.matches(".*\\d.*")) {
						// Pattern pattern = Pattern.compile("[^\\*+]");

						String[] fields = line.split("\\*");
						String number = line.replaceAll("^[0-9]", line);
						
						MainDomDAO mainDom = new MainDomDAO();
						mainDom.setLib(fields[2]);
						saveIfNotExists(mainDom);
					} else {
						String subDomLib = line.split("\\*")[1];
						SubDomDAO subDom = new SubDomDAO();
						subDom.setLib(subDomLib);
						saveIfNotExists(subDom);
						
					}
				}
			});
		} catch (IOException e) {
			throw new RuntimeException("Erreur lecture fichier text", e);
		}
	}

	@Transactional
	public Long saveIfNotExists(AbstractDomDAO mainDom) {
		// Vérifie si un client avec cet email existe déjà (email est unique)
		Optional<AbstractDomDAO> md_check = domRepository.findByLib(mainDom.getLib());
		String key = mainDom.getLib().toString().intern();
		AbstractDomDAO md_exists;
		// synchronized (key) {
		// Exists already?
		if (md_check.isEmpty()) {
			// New MainDomDAO
			md_exists = domRepository.save(mainDom);
		} else {
			// Update existing md
			md_exists = md_check.get();
			// md_exists.setId(mainDom.getId());
			md_exists.setLib(mainDom.getLib());
			domRepository.save(md_exists);
		}
		// }

		return md_exists.getId();
	}
}