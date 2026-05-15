package com.examen.derechohabiente.config;

import javax.sql.DataSource;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.FlatFileParseException;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;
import com.examen.derechohabiente.dto.DerechoHabienteDTO;
import com.examen.derechohabiente.entity.DerechoHabiente;
import com.examen.derechohabiente.exception.InsertDataBaseException;
import com.examen.derechohabiente.listener.CompletedStepListener;
import com.examen.derechohabiente.listener.JobCompletionNotificationListener;
import com.examen.derechohabiente.listener.RegistroErroresListener;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class BeanBatchConfiguration {
	private static final String FIRST_STEP = "carga de derecho hambiente";
    
    @Bean
    FlatFileItemReader<DerechoHabienteDTO> reader() {
    	log.info(FIRST_STEP);
      return new FlatFileItemReaderBuilder<DerechoHabienteDTO>()
        .name("derechoHambienteItemReader")
        .resource(new ClassPathResource("sample-data.csv"))
        .delimited().delimiter(";")
        .names("id", "nombre","ciudad","importe","cuenta")
        .targetType(DerechoHabienteDTO.class)
        .strict(true)
        .build();
      
    }
    
    @Bean
    DerechoHabienteItemProcessor processor() {
      return new DerechoHabienteItemProcessor();
    }

    @Bean
    CustomJdbcBatchItemWriter<DerechoHabiente> writer(DataSource dataSource) {
    	CustomJdbcBatchItemWriter<DerechoHabiente> writer = new CustomJdbcBatchItemWriter<>();
        writer.setDataSource(dataSource);
        writer.setSql("INSERT INTO derecho_habiente (id, nombre ,ciudad,importe,cuenta) "
        		+" VALUES (:id, :nombre, :ciudad, :importe, :cuenta)"
        		+" ON CONFLICT (id) DO NOTHING");
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.afterPropertiesSet(); 
        return writer;
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
            ItemReader<DerechoHabienteDTO> productItemReader, ItemProcessor<DerechoHabienteDTO, DerechoHabiente> productItemProcessor, CustomJdbcBatchItemWriter<DerechoHabiente> writer,
            DerechoHabienteItemProcessor productItemWriter) {
        return new StepBuilder(FIRST_STEP, jobRepository)
                .<DerechoHabienteDTO, DerechoHabiente>chunk(2)
                .transactionManager(transactionManager)
                .reader(productItemReader)
                .processor(productItemProcessor)
                .writer(writer)
                .faultTolerant() 
                .skipLimit(10) 
                .listener(new RegistroErroresListener())
                .skip(FlatFileParseException.class) 
                .skip(InsertDataBaseException.class)
                .listener(new CompletedStepListener())
                .build();
    }

}
