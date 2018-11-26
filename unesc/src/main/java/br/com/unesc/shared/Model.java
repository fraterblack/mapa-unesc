package br.com.unesc.shared;

import com.j256.ormlite.field.DatabaseField;

abstract public class Model {
	@DatabaseField(generatedId = true)
	private int id;
	
	public int getId() {
		return id;
	}
}
