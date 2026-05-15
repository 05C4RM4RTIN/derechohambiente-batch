package com.examen.derechohabiente.config;

import org.springframework.batch.infrastructure.item.ItemProcessor;

import com.examen.derechohabiente.dto.DerechoHambienteDTO;
import com.examen.derechohabiente.entity.DerechoHambiente;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class DerechoHambienteItemProcessor implements ItemProcessor<DerechoHambienteDTO, DerechoHambiente> {
	@Override
	public DerechoHambiente process(final DerechoHambienteDTO derechohambienteDTO) {

	    final DerechoHambiente transformedDerechoHambiente = new DerechoHambiente(derechohambienteDTO.getId(),derechohambienteDTO.getNombre(),
	    		derechohambienteDTO.getCiudad(),derechohambienteDTO.getImporte(),derechohambienteDTO.getCuenta());
	    log.info("Transformando ({}) -> ({})", derechohambienteDTO, transformedDerechoHambiente);
	    return transformedDerechoHambiente;
	  }

}
