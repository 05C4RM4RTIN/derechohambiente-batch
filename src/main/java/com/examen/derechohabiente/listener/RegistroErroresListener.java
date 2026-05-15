package com.examen.derechohabiente.listener;

import org.springframework.batch.core.listener.SkipListener;

import com.examen.derechohabiente.entity.DerechoHabiente;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RegistroErroresListener implements SkipListener<DerechoHabiente, DerechoHabiente> {

    @Override
    public void onSkipInRead(Throwable t) {
    	log.error("Error al leer",t);
        
    }

    @Override
    public void onSkipInWrite(DerechoHabiente item, Throwable t) {
    	log.error("Error al insertar el registro: " + item.toString() + " - Causa: " + t.getMessage(), t);
    }

    @Override
    public void onSkipInProcess(DerechoHabiente item, Throwable t) {
    	log.error("Error al procesar el registro: " + item.toString() + " - Causa: " + t.getMessage(), t);
        
    }
 }
