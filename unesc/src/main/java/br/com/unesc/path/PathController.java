package br.com.unesc.path;

import java.util.List;
import java.util.TreeMap;

import com.j256.ormlite.dao.Dao;

import br.com.unesc.algorithm.PathFounder;
import br.com.unesc.shared.Controller;
import spark.Request;
import spark.Response;

public class PathController extends Controller {
	public static String getAll(Request request, Response response) {
		
		try {
			return dataToJson(getDAO(Path.class).queryForAll());
		} catch (Exception e) {
			response.status(501);
			
			e.printStackTrace();
		}
		
		return null;
    }
	
	public static String getById(Request request, Response response) {
		
		try {
			Path path = getDAO(Path.class).queryForId(request.params("id"));
			
			//Not found
			if (path == null) {
				response.status(404);
				
				return "";
			}
			
			return dataToJson(path);
		} catch (Exception e) {
			response.status(501);
			
			e.printStackTrace();
		}

		return null;
    }
	
	public static String getSmallestRoute(Request request, Response response) {
		//Validate params
		if (request.queryParams("origem") == null || request.queryParams("destino") == null) {
			response.status(404);
			
			return "";
		}
		
		try {
			Dao<Path, String> pathDAO = getDAO(Path.class);
			pathDAO.setObjectCache(true);
			
			List<Path> paths = request.queryParams("mobilidadeReduzida") != null && request.queryParams("mobilidadeReduzida").equals("1")
					? pathDAO.queryForEq("isAccessibleReducedMobility", 1)
					: pathDAO.queryForAll();
			
			//Instantiate class that found small path
			PathFounder pathFounder = new PathFounder(paths);
			
			//Found small route from origin to destination
			TreeMap<Integer, Path> routePaths = pathFounder.findSmallestRoute(Integer.parseInt(request.queryParams("origem")), Integer.parseInt(request.queryParams("destino")));
			
			//If none path is returned, the route is impossible
			if (routePaths.size() == 0) {
				response.status(404);
				
				return "{\"message\":\"Não existe rota para a origem e o destino informados\"}";
			}
			
			int totalDistance = routePaths.entrySet().stream()
				.mapToInt(r -> r.getValue().getDistance()).sum();
			
			return "{"
					+ "\"total_distance\":\"" + totalDistance + "\","
					+ "\"total_paths\":\"" + routePaths.size() + "\","
					+ "\"paths\":" + dataToJson(routePaths) 
					+ "}";
		} catch (Exception e) {
			response.status(501);
			
			e.printStackTrace();
		}
		
		return null;
	}
}
