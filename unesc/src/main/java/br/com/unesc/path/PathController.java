package br.com.unesc.path;

import java.util.List;

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
		if (request.params("originId") == null || request.params("destinationId") == null) {
			response.status(404);
			
			return "";
		}
		
		try {
			List<Path> paths = getDAO(Path.class).queryForAll();
			
			//Instantiate class that found small path
			PathFounder pathFounder = new PathFounder(paths);
			
			//Found small route from origin to destination
			List<Path> routePaths = pathFounder.findSmallestRoute(Integer.parseInt(request.params("originId")), Integer.parseInt(request.params("destinationId")));
			
			//If none path is returned, the route is impossible
			if (routePaths.size() == 0) {
				response.status(404);
				
				return "{\"message\":\"Não existe rota para a origem e o destino informados\"}";
			}
			
			return dataToJson(routePaths);
		} catch (Exception e) {
			response.status(501);
			
			e.printStackTrace();
		}
		
		return null;
	}
}
