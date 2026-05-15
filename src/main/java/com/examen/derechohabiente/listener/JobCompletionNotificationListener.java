package com.examen.derechohabiente.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.examen.derechohabiente.entity.DerechoHabiente;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class JobCompletionNotificationListener implements JobExecutionListener {


	  private final JdbcTemplate jdbcTemplate;

	  public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
	    this.jdbcTemplate = jdbcTemplate;
	  }

	  @Override
	  public void afterJob(JobExecution jobExecution) {
	    if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
	      log.info("Carga finalizada");
	      jdbcTemplate
	          .query("SELECT id,nombre,ciudad,importe,cuenta FROM derecho_habiente", new DataClassRowMapper<>(DerechoHabiente.class))
	          .forEach(derechoHambiente -> log.info("Found <{}> in the database.", derechoHambiente));
	    }
	  }	  
	}
