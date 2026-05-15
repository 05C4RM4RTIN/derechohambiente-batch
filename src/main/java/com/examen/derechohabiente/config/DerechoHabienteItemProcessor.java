package com.examen.derechohabiente.config;

import org.springframework.batch.infrastructure.item.ItemProcessor;

import com.examen.derechohabiente.dto.DerechoHabienteDTO;
import com.examen.derechohabiente.entity.DerechoHabiente;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class DerechoHabienteItemProcessor implements ItemProcessor<DerechoHabienteDTO, DerechoHabiente> {
	@Override
	public DerechoHabiente process(final DerechoHabienteDTO derechohambienteDTO) {

	    final DerechoHabiente transformedDerechoHambiente = new DerechoHabiente(derechohambienteDTO.getId(),derechohambienteDTO.getNombre(),
	    		derechohambienteDTO.getCiudad(),derechohambienteDTO.getImporte(),derechohambienteDTO.getCuenta());
	    log.info("Transformando ({}) -> ({})", derechohambienteDTO, transformedDerechoHambiente);
	    return transformedDerechoHambiente;
	  }

}
