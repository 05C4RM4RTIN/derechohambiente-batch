package com.examen.derechohabiente.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DerechoHabiente {	
	
		@Id
		private Long id;
		private String nombre;
		private String ciudad;
		private float importe;
		private String cuenta;

}
