package com.example.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
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

	private List<AbstractDomDAO> listEnfant;

	public MainDomImporterService(@Qualifier("dom") DomRepository domRepository) {
		this.domRepository = domRepository;
	}

	public void ajouterEnfant(AbstractDomDAO subDomDAO) {
		listEnfant.add(subDomDAO);
	}
	
	

	public void importFromText(Path pathToFile) throws IOException {
		
		
		// les elemens à persister
		Set<AbstractDomDAO> racines = new HashSet<>();
		
		Stream<String> lines = Files.lines(pathToFile);
		
		// AtomicReference permet de changer
		// de "courant" dans la lambda de forEach (car une variable locale doit être
		// finale ou effective-final).
		AtomicReference<AbstractDomDAO> current = new AtomicReference<>();
		lines.forEach(line -> {
			if (!line.isEmpty()) {
				if (line.matches(".*\\d.*")) {
					// Pattern pattern = Pattern.compile("[^\\*+]");
					String[] fields = line.split("\\*");
					String number = line.replaceAll("^[0-9]", line);
					MainDomDAO mainDom = new MainDomDAO();
					mainDom.setLib(fields[2]);
					//((AbstractDomDAO)mainDom).setParent(this);
					racines.add(mainDom);
					current.set(mainDom);
					//current.get().setParent(current.get());
					// saveIfNotExists(current);
				} else {
					String subDomLib = line.split("\\*")[1];
					SubDomDAO subDom = new SubDomDAO();
					subDom.setLib(subDomLib);
					subDom.setParent(current.get());
					current.get().addSubDom(subDom);
					//current.get().setParent(current.get());
					// saveIfNotExists(subDom);
				}
			}
		});
		try {
			domRepository.saveAll(racines);	
		} catch (Exception e) {
			// TODO: handle exception
		}
		racines.forEach(r -> r.afficher(""));
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