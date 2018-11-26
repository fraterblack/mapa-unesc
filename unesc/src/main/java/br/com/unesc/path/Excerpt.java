package br.com.unesc.path;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.unesc.shared.Model;

@DatabaseTable(tableName = "excerpts")
public class Excerpt extends Model {
	@DatabaseField(canBeNull = false)
	private int posX;
	
	@DatabaseField(canBeNull = false)
	private int posY;
	
	@DatabaseField
	private String description;
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "path_id", index = true)
	private Path path;
	
	public Excerpt() {
	}
	
	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
