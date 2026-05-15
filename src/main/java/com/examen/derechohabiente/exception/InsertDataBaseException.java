package com.examen.derechohabiente.exception;

public class InsertDataBaseException extends RuntimeException {
    private static final long serialVersionUID = 844208175785002145L;

	public InsertDataBaseException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
