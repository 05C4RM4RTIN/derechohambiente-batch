package com.examen.derechohabiente.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DerechoHambienteDTO {
	
	private Long id;
	private String nombre;
	private String ciudad;
	private float importe;
	private String cuenta;

}
