package br.com.unesc.place;

import spark.Request;
import spark.Response;
import br.com.unesc.shared.Controller;

public class PlaceController extends Controller {
	public static String getAll(Request request, Response response) {
		
		try {
			return dataToJson(getDAO(Place.class).queryForAll());
		} catch (Exception e) {
			response.status(501);
			
			e.printStackTrace();
		}
		
		return null;
    }
}
