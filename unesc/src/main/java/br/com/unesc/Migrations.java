package br.com.unesc;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.table.TableUtils;

import br.com.unesc.path.Excerpt;
import br.com.unesc.path.Path;
import br.com.unesc.place.Place;
import br.com.unesc.shared.DataBaseManager;

public class Migrations {

	public static void main(String[] args) {
		try {
			// Drop tables
			TableUtils.dropTable(DataBaseManager.connectionSource(), Excerpt.class, true);
			TableUtils.dropTable(DataBaseManager.connectionSource(), Place.class, true);
			TableUtils.dropTable(DataBaseManager.connectionSource(), Path.class, true);

			// Create tables
			TableUtils.createTableIfNotExists(DataBaseManager.connectionSource(), Place.class);
			TableUtils.createTableIfNotExists(DataBaseManager.connectionSource(), Excerpt.class);
			TableUtils.createTableIfNotExists(DataBaseManager.connectionSource(), Path.class);

			// Seed
			Dao<Place, ?> daoPlace = DaoManager.createDao(DataBaseManager.connectionSource(), Place.class);
			Dao<Path, ?> daoPath = DaoManager.createDao(DataBaseManager.connectionSource(), Path.class);
			Dao<Excerpt, ?> daoExcerpt = DaoManager.createDao(DataBaseManager.connectionSource(), Excerpt.class);

			ObjectMapper objectMapper = new ObjectMapper();

			try {
				ObjectMapper mapper = new ObjectMapper();

				// Place
				JsonParser placeParser = mapper.getFactory().createParser(new File("data/places.json"));

				if (placeParser.nextToken() != JsonToken.START_ARRAY) {
					throw new IllegalStateException("Expected an array");
				}

				while (placeParser.nextToken() == JsonToken.START_OBJECT) {
					Place place = objectMapper.readValue(placeParser, Place.class);

					daoPlace.create(place);
				}

				placeParser.close();

				// Path
				JsonParser pathParser = mapper.getFactory().createParser(new File("data/paths.json"));

				if (pathParser.nextToken() != JsonToken.START_ARRAY) {
					throw new IllegalStateException("Expected an array");
				}

				while (pathParser.nextToken() == JsonToken.START_OBJECT) {
					ObjectNode node = mapper.readTree(pathParser);

					String originName = node.get("originNode").get("name").asText();
					String destinationName = node.get("destinationNode").get("name").asText();

					if (originName != null && destinationName != null) {
						List<Place> listOrigin = daoPlace.queryForEq("name", originName);
						List<Place> listDestination = daoPlace.queryForEq("name", destinationName);

						if (listOrigin.size() > 0 && listDestination.size() > 0) {

							Path path = new Path();

							path.setOriginPlace(listOrigin.get(0));
							path.setDestinationPlace(listDestination.get(0));
							path.setIsAccessibleReducedMobility(node.get("reducedMobility").asBoolean());
							path.setDistance(node.get("distance").asInt());

							daoPath.create(path);

							// Excerpts
							node.get("excerpts").forEach(e -> {
								Excerpt excerpt = new Excerpt();

								excerpt.setPath(path);
								excerpt.setPosX(e.get("x").asInt());
								excerpt.setPosY(e.get("y").asInt());
								if (e.has("description")) {
									excerpt.setDescription(e.get("description").asText());
								}

								try {
									daoExcerpt.create(excerpt);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
							});
						} else {
							System.out.println("Não foi possível importar: " + originName + " -> " + destinationName);
						}
					}
				}

				pathParser.close();

			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
