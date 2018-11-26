package br.com.unesc.place;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.unesc.shared.Model;

@DatabaseTable(tableName = "places")
public class Place extends Model {
	@DatabaseField(canBeNull = false)
	private String name;
	
	@DatabaseField
	private boolean isPlace = false;
	
	public Place() {
	}
	
	public Place(String name, boolean isPlace) {
		this.name = name;
		this.isPlace = isPlace;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isPlace() {
		return isPlace;
	}

	public void setIsPlace(boolean isPlace) {
		this.isPlace = isPlace;
	}
}
