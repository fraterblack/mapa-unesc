package br.com.unesc;

import static spark.Spark.*;

import br.com.unesc.path.PathController;
import br.com.unesc.place.PlaceController;

public class Application {
    public static void main(String[] args) {
    	// Configure Spark
    	staticFiles.location("/public");
    	
    	notFound((req, res) -> {
    	    res.type("application/json");
    	    return res.body();
    	});
    	
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
    	    	get("/:originId/para/:destinationId", (req, res) -> { return PathController.getSmallestRoute(req, res); });
    	    });
    	});
    }
}
