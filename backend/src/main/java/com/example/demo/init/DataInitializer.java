package com.example.demo.init;

import java.nio.file.Paths;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
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