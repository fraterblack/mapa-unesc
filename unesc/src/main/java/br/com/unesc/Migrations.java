package br.com.unesc;

import java.io.IOException;
import java.sql.SQLException;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
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
			//Drop tables
			TableUtils.dropTable(DataBaseManager.connectionSource(), Excerpt.class, true);
			TableUtils.dropTable(DataBaseManager.connectionSource(), Place.class, true);
			TableUtils.dropTable(DataBaseManager.connectionSource(), Path.class, true);
			
			//Create tables
			TableUtils.createTableIfNotExists(DataBaseManager.connectionSource(), Place.class);
			TableUtils.createTableIfNotExists(DataBaseManager.connectionSource(), Excerpt.class);
			TableUtils.createTableIfNotExists(DataBaseManager.connectionSource(), Path.class);
			
			//Seed
			Dao<Place, ?> daoPlace = DaoManager.createDao(DataBaseManager.connectionSource(), Place.class);
			
			JsonFactory factory = new JsonFactory();
			try {
				JsonParser  parser  = factory.createParser(placesSeeder());
				
				Place place = null;
				while(!parser.isClosed()){
				    JsonToken jsonToken = parser.nextToken();
				    
				    if(JsonToken.FIELD_NAME.equals(jsonToken)){
				        String fieldName = parser.getCurrentName();

				        jsonToken = parser.nextToken();

				        if(!"place".equals(fieldName) && !"name".equals(fieldName)) {
				        	if (place != null) {
				        		daoPlace.create(place);
				        	}
				        	
				        	place = new Place();
				        } else if ("name".equals(fieldName)){
				        	place.setName(parser.getValueAsString());
				        } else if ("place".equals(fieldName)){
				        	place.setIsPlace(parser.getValueAsBoolean());
				        }
				    }
				}
				
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static String placesSeeder() {
		return "{" + 
		"  \"xxia\": {" + 
		"    \"place\": \"true\"," + 
		"    \"name\": \"XXI-A\"" + 
		"  }," + 
		"  \"xxib\": {" + 
		"    \"place\": \"true\"," + 
		"    \"name\": \"XXI-B\"" + 
		"  }," + 
		"  \"xxic\": {" + 
		"    \"place\": \"true\"," + 
		"    \"name\": \"XXI-C\"" + 
		"  }," + 
		"  \"centac\": {" + 
		"    \"place\": \"true\"," + 
		"    \"name\": \"Centac\"" + 
		"  }," + 
		"  \"reitoria\": {" + 
		"    \"place\": \"true\"," + 
		"    \"name\": \"Bloco da Reitoria / Museu\"" + 
		"  }," + 
		"  \"biblioteca\": {" + 
		"    \"place\": \"true\"," + 
		"    \"name\": \"Biblioteca\"" + 
		"  }," + 
		"  \"blocoa\": {" + 
		"    \"place\": \"true\"," + 
		"    \"name\": \"Bloco A\"" + 
		"  }," + 
		"  \"blocob\": {" + 
		"    \"place\": \"true\"," + 
		"    \"name\": \"Bloco B\"" + 
		"  }," + 
		"  \"blococ\": {" + 
		"    \"place\": \"true\"," + 
		"    \"name\": \"Bloco C\"" + 
		"  }," + 
		"  \"blocod\": {" + 
		"    \"place\": \"true\"," + 
		"    \"name\": \"Bloco D\"" + 
		"  }," + 
		"  \"blocoe\": {" + 
		"    \"place\": \"true\"," + 
		"    \"name\": \"Bloco E\"" + 
		"  }," + 
		"  \"blocof\": {" + 
		"    \"place\": \"true\"," + 
		"    \"name\": \"Bloco F\"" + 
		"  }," + 
		"  \"blocog\": {" + 
		"    \"place\": \"true\"," + 
		"    \"name\": \"Bloco G\"" + 
		"  }," + 
		"  \"blocoh\": {" + 
		"    \"place\": \"true\"," + 
		"    \"name\": \"Bloco H\"" + 
		"  }," + 
		"  \"blocoi\": {" + 
		"    \"place\": \"true\"," + 
		"    \"name\": \"Bloco I\"" + 
		"  }," + 
		"  \"blocoj\": {" + 
		"    \"place\": \"true\"," + 
		"    \"name\": \"Bloco J\"" + 
		"  }," + 
		"  \"blocok\": {" + 
		"    \"place\": \"true\"," + 
		"    \"name\": \"Bloco K\"" + 
		"  }," + 
		"  \"blocom\": {" + 
		"    \"place\": \"true\"," + 
		"    \"name\": \"Bloco M\"" + 
		"  }," + 
		"  \"blocol\": {" + 
		"    \"place\": \"true\"," + 
		"    \"name\": \"Bloco L\"" + 
		"  }," + 
		"  \"blocon\": {" + 
		"    \"place\": \"true\"," + 
		"    \"name\": \"Bloco N\"" + 
		"  }," + 
		"  \"blocoo\": {" + 
		"    \"place\": \"true\"," + 
		"    \"name\": \"Bloco O\"" + 
		"  }," + 
		"  \"blocop\": {" + 
		"    \"place\": \"true\"," + 
		"    \"name\": \"Bloco P - Pós e Extensão\"" + 
		"  }," + 
		"  \"blocoq\": {" + 
		"    \"place\": \"true\"," + 
		"    \"name\": \"Bloco Q\"" + 
		"  }," + 
		"  \"blocoz\": {" + 
		"    \"place\": \"true\"," + 
		"    \"name\": \"Bloco Z\"" + 
		"  }," + 
		"  \"apoiologistico\": {" + 
		"    \"place\": \"true\"," + 
		"    \"name\": \"Bloco Apoio Logístico\"" + 
		"  }," + 
		"  \"hall\": {" + 
		"    \"place\": \"true\"," + 
		"    \"name\": \"Hall de Entrada\"" + 
		"  }," + 
		"  \"i_patio_blocoz\": {" + 
		"    \"place\": \"false\"," + 
		"    \"name\": \"Pátio bloco Z\"" + 
		"  }," + 
		"  \"i_escadaria_centac\": {" + 
		"    \"place\": \"false\"," + 
		"    \"name\": \"Escadaria Centac\"" + 
		"  }," + 
		"  \"i_entre_centac_blocos\": {" + 
		"    \"place\": \"false\"," + 
		"    \"name\": \"Entre Centac e Blocos\"" + 
		"  }," + 
		"  \"i_xxic_blocos\": {" + 
		"    \"place\": \"false\"," + 
		"    \"name\": \"Entre XXI-C e Blocos\"" + 
		"  }," + 
		"  \"i_chegada_ij\": {" + 
		"    \"place\": \"false\"," + 
		"    \"name\": \"Chegada I e J\"" + 
		"  }," + 
		"  \"i_entrada_k\": {" + 
		"    \"place\": \"false\"," + 
		"    \"name\": \"Entradas K\"" + 
		"  }," + 
		"  \"i_esquina_klj\": {" + 
		"    \"place\": \"false\"," + 
		"    \"name\": \"Esquina KLJ\"" + 
		"  }," + 
		"  \"i_entrada_ml\": {" + 
		"    \"place\": \"false\"," + 
		"    \"name\": \"Entradas L e M\"" + 
		"  }," + 
		"  \"i_chegada_lm\": {" + 
		"    \"place\": \"false\"," + 
		"    \"name\": \"Chegada L e M\"" + 
		"  }," + 
		"  \"i_esquina_nlm\": {" + 
		"    \"place\": \"false\"," + 
		"    \"name\": \"Esquina NLM\"" + 
		"  }," + 
		"  \"i_acesso_np\": {" + 
		"    \"place\": \"false\"," + 
		"    \"name\": \"Acesso N e P\"" + 
		"  }," + 
		"  \"i_entrada_n\": {" + 
		"    \"place\": \"false\"," + 
		"    \"name\": \"Entrada N\"" + 
		"  }," + 
		"  \"i_entrada_no\": {" + 
		"    \"place\": \"false\"," + 
		"    \"name\": \"Entradas N e O\"" + 
		"  }," + 
		"  \"i_esquina_onkl\": {" + 
		"    \"place\": \"false\"," + 
		"    \"name\": \"Esquina ONKL\"" + 
		"  }," + 
		"  \"i_esquina_lmj\": {" + 
		"    \"place\": \"false\"," + 
		"    \"name\": \"Esquina LMJ\"" + 
		"  }," + 
		"  \"i_entrada_j\": {" + 
		"    \"place\": \"false\"," + 
		"    \"name\": \"Entrada J\"" + 
		"  }," + 
		"  \"i_esquina_hgj\": {" + 
		"    \"place\": \"false\"," + 
		"    \"name\": \"Esquina HGJ\"" + 
		"  }," + 
		"  \"i_esquina_hij\": {" + 
		"    \"place\": \"false\"," + 
		"    \"name\": \"Esquina HIJ\"" + 
		"  }," + 
		"  \"i_entrada_h\": {" + 
		"    \"place\": \"false\"," + 
		"    \"name\": \"Entrada H\"" + 
		"  }," + 
		"  \"i_chegada_h\": {" + 
		"    \"place\": \"false\"," + 
		"    \"name\": \"Chegada H\"" + 
		"  }," + 
		"  \"i_esquina_deh\": {" + 
		"    \"place\": \"false\"," + 
		"    \"name\": \"Esquina DEH\"" + 
		"  }," + 
		"  \"i_esquina_efh\": {" + 
		"    \"place\": \"false\"," + 
		"    \"name\": \"Esquina EFH\"" + 
		"  }," + 
		"  \"i_entrada_f\": {" + 
		"    \"place\": \"false\"," + 
		"    \"name\": \"Entrada F\"" + 
		"  }," + 
		"  \"i_entrada_de\": {" + 
		"    \"place\": \"false\"," + 
		"    \"name\": \"Entrada DE\"" + 
		"  }," + 
		"  \"i_esquina_abde\": {" + 
		"    \"place\": \"false\"," + 
		"    \"name\": \"Esquina ABDE\"" + 
		"  }," + 
		"  \"i_esquina_bef\": {" + 
		"    \"place\": \"false\"," + 
		"    \"name\": \"Esquina BEF\"" + 
		"  }," + 
		"  \"i_entrada_b\": {" + 
		"    \"place\": \"false\"," + 
		"    \"name\": \"Entrada B\"" + 
		"  }," + 
		"  \"i_esquina_ab\": {" + 
		"    \"place\": \"false\"," + 
		"    \"name\": \"Esquina AB\"" + 
		"  }," + 
		"  \"i_esquina_bc\": {" + 
		"    \"place\": \"false\"," + 
		"    \"name\": \"Esquina BC\"" + 
		"  }," + 
		"  \"i_esquina_bce\": {" + 
		"    \"place\": \"false\"," + 
		"    \"name\": \"Esquina BCE\"" + 
		"  }," + 
		"  \"i_chegada_c\": {" + 
		"    \"place\": \"false\"," + 
		"    \"name\": \"Chegada C\"" + 
		"  }," + 
		"  \"i_saida_biblioteca_blocos\": {" + 
		"    \"place\": \"false\"," + 
		"    \"name\": \"Saída Biblioteca para Blocos\"" + 
		"  }," + 
		"  \"i_chegada_biblioteca_blocos\": {" + 
		"    \"place\": \"false\"," + 
		"    \"name\": \"Chegada Ciblioteca para Blocos\"" + 
		"  }," + 
		"  \"i_meio_caminho_centac\": {" + 
		"    \"place\": \"false\"," + 
		"    \"name\": \"Meio Caminho Centac\"" + 
		"  }" + 
		"}";
	}
}
