package com.examen.derechohabiente.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.step.StepExecution;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class CompletedStepListener implements StepExecutionListener {
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        long fallidosEnEscritura = stepExecution.getWriteSkipCount();
        log.info("Registros fallidos en lectura: " + fallidosEnEscritura);
        
        long fallidosEnProceso = stepExecution.getProcessSkipCount();
        log.info("Registros fallidos en proceso: " + fallidosEnProceso);         
        
        long leidosExitosos = stepExecution.getReadCount();
        log.info("Registros exitosos en lectura: " + leidosExitosos);
        
        
        long totalFallidos = stepExecution.getSkipCount();        
        log.info("Registros fallidos en general: " + totalFallidos);
        
        long insertadosExitosos = stepExecution.getWriteCount();        
        log.info("Total procesados con éxito: " + insertadosExitosos);
        return stepExecution.getExitStatus();
    }
}
