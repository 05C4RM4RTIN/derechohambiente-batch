package com.examen.derechohabiente.config;

import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.database.JdbcBatchItemWriter;

import com.examen.derechohabiente.exception.InsertDataBaseException;

public class CustomJdbcBatchItemWriter <T> extends JdbcBatchItemWriter<T> {
    
    @Override
    public void write(Chunk<? extends T> chunk) throws Exception {
        try {
            super.write(chunk);
        } catch (Exception e) {
            throw new InsertDataBaseException("Error al insertar lote", e);
        }
    }
}
