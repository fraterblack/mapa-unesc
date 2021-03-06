package br.com.unesc.shared;

import java.io.IOException;
import java.sql.SQLException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

public abstract class Controller {
	protected static <T> Dao<T, String> getDAO(Class<T> modelClass) throws SQLException {
		return DaoManager.createDao(DataBaseManager.connectionSource(), modelClass);
	}
	
	protected static String dataToJson(Object data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.enable(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED);
            
            return mapper.writeValueAsString(data);
        } catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
