package com.example.demo.spring.batch;

import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.demo.domain.dom.daos.MainDomDAO;
import com.example.demo.repository.DomRepository;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

	// @Autowired
	// private JobBuilder jobBuilder;

	// @Autowired
	// private StepBuilder stepBuilder;

	/*private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;

	// le repo
	private final DomRepository mainDomRepository;

	public SpringBatchConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager,
			DomRepository mainDomRepository) {
		this.jobRepository = jobRepository;
		this.transactionManager = transactionManager;
		this.mainDomRepository = mainDomRepository;
	}

	@Bean
	public ItemReader<MainDomDAO> reader() {
		return new ListItemReader<>(mainDomRepository.findAll());
	}

	@Bean
	public FlatFileItemReader<String> fileReader() {
		FlatFileItemReader<String> r = new FlatFileItemReader<>();
		r.setResource(new FileSystemResource(Paths.get("../../domaines.txt"))); // fichier texte dans src/main/resources

		// LineMapper très simple qui retourne la ligne entière comme String
		r.setLineMapper((line, lineNumber) -> line);

		return r;
	}

	@Bean
	public ItemProcessor<MainDomDAO, MainDomDAO> processor(DomRepository mainDomRepo) {
		return item -> {
			if (mainDomRepo.existsByLib(item.getLib())) {
				return null; // ignorer les doublons
			}
			return item;
		};
	}

	@Bean
	public ItemWriter<MainDomDAO> writerLineByLine() {
		return items -> {
			for (MainDomDAO p : items) {
				try {
					mainDomRepository.save(p);

				} catch (DataIntegrityViolationException e) {
					// Gestion du doublon : on logue et on continue le batch
					System.out.println("Doublon ignoré : " + p.getLib());
				}
			}
		};
	}

	/*@Bean
	public ItemWriter<String> writerLineLineByLinev2() {
		/*return lines -> {
			// lines.forEach(null);
			lines.forEach(line -> {
				if (line.matches(".*\\d.*")) {
					// Pattern pattern = Pattern.compile("[^\\*+]");

					String[] fields = line.split("\\*");

					// line = line.replaceAll("[0-9]","")
					// .replaceAll("\\.","")
					// .trim();
					String number = line.replaceAll("^[0-9]", line);

					MainDomDAO mainDom = new MainDomDAO();
					mainDom.setLib(fields[2]);

					// saveProduct(mainDom);
					try {

						Optional<MainDomDAO> md_check = mainDomRepository.findByLib(mainDom.getLib());
						String key = mainDom.getLib().toString().intern();
						MainDomDAO md_exists;
						// synchronized (key) {
						// Exists already?
						if (md_check.isEmpty()) {
							// New MainDomDAO
							md_exists = (MainDomDAO)mainDomRepository.save(mainDom);
						} else {
							// Update existing md
							md_exists = md_check.get();
							// md_exists.setId(mainDom.getId());
							md_exists.setLib(mainDom.getLib());
							mainDomRepository.save(md_exists);
						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						// e.printStackTrace();
						System.out.println("sauver quand meme nn ?");
					}
				}
			});
		};
	}

	/*
	 * @Bean public ItemWriter<MainDomDAO> writer() { try (Stream<String> lines =
	 * Files.lines(Paths.get("../../domaines.txt"))) { lines.forEach(line -> { if
	 * (line.matches(".*\\d.*")) { // Pattern pattern = Pattern.compile("[^\\*+]");
	 * 
	 * String[] fields = line.split("\\*");
	 * 
	 * // line = line.replaceAll("[0-9]","") // .replaceAll("\\.","") // .trim();
	 * String number = line.replaceAll("^[0-9]", line);
	 * 
	 * MainDomDAO mainDom = new MainDomDAO(); mainDom.setLib(fields[2]);
	 * 
	 * //saveProduct(mainDom); try { mainDomRepository.save(mainDom); } catch
	 * (Exception e) { // TODO Auto-generated catch block e.printStackTrace(); }
	 * 
	 * } }); } catch (IOException e) { throw new
	 * RuntimeException("Erreur lecture fichier text", e); }
	 * 
	 * /*return items -> { for (Product p : items) { try {
	 * productRepository.save(p); } catch (DataIntegrityViolationException e) { //
	 * Gestion du doublon : on logue et on continue le batch
	 * System.out.println("Doublon ignoré : " + p.getName()); } } };
	 */
	// }

	/*@Bean
	public Step simpleStep() {
		return new StepBuilder("simpleStep", jobRepository).<String, String>chunk(5, transactionManager)
				.reader(fileReader()) // Exemple ItemReader simple
				// .processor(processor(mainDomRepository))
				.writer(writerLineLineByLinev2()).faultTolerant().skip(DuplicateKeyException.class).skipLimit(0)
				.build();
	}

	@Bean
	public Job simpleJob() {
		return new JobBuilder("simpleJob", jobRepository).start(simpleStep()).build();
	}*/
}
