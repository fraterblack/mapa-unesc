package br.com.unesc.place;

import spark.Request;
import spark.Response;

import br.com.unesc.shared.Controller;

public class PlaceController extends Controller {
	public static String getByQuery(Request request, Response response) {
		
		try {
			return dataToJson(getDAO(Place.class).queryForEq("isPlace", 1));
		} catch (Exception e) {
			response.status(501);
			
			e.printStackTrace();
		}
		
		return null;
    }
}
