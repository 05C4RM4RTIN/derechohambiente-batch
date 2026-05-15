package com.examen.derechohabiente.config;

import javax.sql.DataSource;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.database.JdbcBatchItemWriter;
import org.springframework.batch.infrastructure.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;
import com.examen.derechohabiente.dto.DerechoHambienteDTO;
import com.examen.derechohabiente.entity.DerechoHambiente;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class BeanBatchConfiguration {
	private static final String FIRST_STEP = "carga de derecho hambiente";
    
    @Bean
    FlatFileItemReader<DerechoHambienteDTO> reader() {
    	log.info(FIRST_STEP);
      return new FlatFileItemReaderBuilder<DerechoHambienteDTO>()
        .name("derechoHambienteItemReader")
        .resource(new ClassPathResource("sample-data.csv"))
        .delimited().delimiter(";")
        .names("id", "nombre","ciudad","importe","cuenta")
        .targetType(DerechoHambienteDTO.class)
        .strict(false)
        .build();
      
    }
    
    @Bean
    DerechoHambienteItemProcessor processor() {
      return new DerechoHambienteItemProcessor();
    }

    @Bean
    JdbcBatchItemWriter<DerechoHambiente> writer(DataSource dataSource) {
      return new JdbcBatchItemWriterBuilder<DerechoHambiente>()
        .sql("INSERT INTO derecho_hambiente (id, nombre ,ciudad,importe,cuenta) VALUES (:id, :nombre, :ciudad, :importe, :cuenta)")
        .dataSource(dataSource)
        .beanMapped()
        .build();
    }
    
    @Bean
    Job importUserJob(JobRepository jobRepository, Step step1, JobCompletionNotificationListener listener) {
      return new JobBuilder(jobRepository)
        .listener(listener)
        .start(step1)
        .build();
    }

    @Bean
    Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager,
            ItemReader<DerechoHambienteDTO> productItemReader, ItemProcessor<DerechoHambienteDTO, DerechoHambiente> productItemProcessor, JdbcBatchItemWriter<DerechoHambiente> writer,
            DerechoHambienteItemProcessor productItemWriter) {
        return new StepBuilder(FIRST_STEP, jobRepository)
                .<DerechoHambienteDTO, DerechoHambiente>chunk(8)
                .transactionManager(transactionManager)
                .reader(productItemReader)
                .processor(productItemProcessor)
                .writer(writer)
                .build();
    }

}
