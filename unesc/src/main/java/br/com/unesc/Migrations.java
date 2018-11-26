package br.com.unesc;

import java.sql.SQLException;

import com.j256.ormlite.table.TableUtils;

import br.com.unesc.path.Excerpt;
import br.com.unesc.path.Path;
import br.com.unesc.place.Place;
import br.com.unesc.shared.DataBaseManager;

public class Migrations {

	public static void main(String[] args) {
		try {
			//Drop tables
			TableUtils.dropTable(DataBaseManager.connectionSource(), Excerpt.class, true);
			TableUtils.dropTable(DataBaseManager.connectionSource(), Place.class, true);
			TableUtils.dropTable(DataBaseManager.connectionSource(), Path.class, true);
			
			//Create tables
			TableUtils.createTableIfNotExists(DataBaseManager.connectionSource(), Place.class);
			TableUtils.createTableIfNotExists(DataBaseManager.connectionSource(), Excerpt.class);
			TableUtils.createTableIfNotExists(DataBaseManager.connectionSource(), Path.class);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
