package br.com.unesc;

import static spark.Spark.*;

import br.com.unesc.admin.AdminController;
import br.com.unesc.path.PathController;
import br.com.unesc.place.PlaceController;

public class Application {
    public static void main(String[] args) {
    	// Configure Spark
    	staticFiles.location("/public");
    	
    	/*notFound((req, res) -> {
    	    res.type("application/json");
    	    return "{\"message\":\"Endpoint not found\"}";
    	});*/
    	
    	internalServerError((req, res) -> {
    	    res.type("application/json");
    	    return "{\"message\":\"Erro interno\"}";
    	});
    	
    	
    	// Routes
    	path("/api", () -> {
    	    path("/lugar", () -> {
    	    	get("", (req, res) -> { return PlaceController.getAll(req, res); });
    	    });
    	    
    	    path("/caminho", () -> {
    	    	get("", (req, res) -> { return PathController.getAll(req, res); });
    	    	get("/:id", (req, res) -> { return PathController.getById(req, res); });
    	    	get("/:originId/to/:destinationId", (req, res) -> { return PathController.getSmallestRoute(req, res); });
    	    });
    	});
    	
    	path("/restricted-api", () -> {
    		get("/importar-rotas", (req, res) -> { return AdminController.importRoutes(req, res); });
    	});
    }
}
