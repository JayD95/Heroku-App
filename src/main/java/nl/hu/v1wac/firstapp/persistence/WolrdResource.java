//package nl.hu.v1wac.firstapp.persistence;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.security.RolesAllowed;
//import javax.json.Json;
//import javax.json.JsonArray;
//import javax.json.JsonArrayBuilder;
//import javax.json.JsonObjectBuilder;
//import javax.ws.rs.DELETE;
//import javax.ws.rs.FormParam;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.PUT;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.Response;
//
//import nl.hu.v1wac.firstapp.webservices.WorldService;
//
//@Path("/countries/{countrycode}")
//public class WolrdResource {
//	private WorldService service = ServiceProvider.getWorldService();
//	private JsonObjectBuilder buildJsonCountry(Country c) {
//		JsonObjectBuilder job = Json.createObjectBuilder();
//
//		job.add("capital", c.getCapital());
//		job.add("code", c.getCode());
//		job.add("continent", c.getContinent());
//		job.add("government", c.getGovernment());
//		job.add("iso3", c.getIso3());
//		job.add("latitude", c.getLatitude());
//		job.add("longitude", c.getLongitude());
//		job.add("name", c.getName());
//		job.add("population", c.getPopulation());
//		job.add("region", c.getRegion());
//		job.add("surface", c.getSurface());
//		return job;
//	}
//	
//	@GET
//	@Produces("application/json")
//	public String getCountry(@PathParam("countrycode") String code) throws SQLException  {
//		Country world = service.getCountryByCode(code);
//		JsonObjectBuilder job = null;
//		if(world!=null) {
//		job = buildJsonCountry(world);
//		}
//		return job != null ? job.build().toString(): Json.createObjectBuilder().add("error", "not there").build().toString();
//		Country country = service.getCountryByCode(code);
//		
//		if (country == null) {
//			Map<String, String> messages = new HashMap<String, String>();
//			messages.put("error", "Country bestaat niet!");
//			return Response.status(409).entity(messages).build();
//		}
//		
//		return Response.ok(country).build();
//	}
	
//	@POST
//	@RolesAllowed("user")
//	@Produces("application/json")
//	public Response addCountry( @FormParam("countrycode") String code,
//								   @FormParam("iso3") String iso3,
//								   @FormParam("name") String name,
//								   @FormParam("continent") String continent,
//								   @FormParam("region") String region,
//								   @FormParam("surface") double surface,
//								   @FormParam("population") int population,
//								   @FormParam("governmentform") String governmentform,
//								   @FormParam("headofstate") String headofstate,
//								   @FormParam("latitude") double latitude,
//								   @FormParam("longitude") double longitude,
//								   @FormParam("capital") String capital) throws SQLException {
//		
//		Country country = service.saveCountry(code, iso3, name, capital, continent,  region,  surface,  population,  governmentform,  latitude,  longitude);
//		if (country == null) {
//			Map<String, String> messages = new HashMap<String, String>();
//			messages.put("error", "Country does not exist!");
//			return Response.status(409).entity(messages).build();
//		}
//		
//		return Response.ok(country).build();
//	}
	
//	@PUT
//	@RolesAllowed("user")
//	@Produces("application/json")
//	public Response updateCountry( @PathParam("countrycode") String code,
//								   @FormParam("name") String name,
//								   @FormParam("capital") String capital,
//								   @FormParam("region") String region,
//								   @FormParam("surface") double surface,
//								   @FormParam("population") int population) throws SQLException {
//		
//		Country country = service.updateCountry(code, name, capital, region,  surface,  population);
//		if (country == null) {
//			Map<String, String> messages = new HashMap<String, String>();
//			messages.put("error", "Country does not exist!");
//			return Response.status(409).entity(messages).build();
//		}
//		
//		return Response.ok(country).build();
//	}
	
//	@DELETE
//	@RolesAllowed("user")
//	@Produces("application/json")
//	public Response deleteCountry( @PathParam("countrycode") String code) throws SQLException {
//		
//		if (!service.deleteCountry(code)) {
//			Map<String, String> messages = new HashMap<String, String>();
//			messages.put("error", "Country does not exist!");
//			return Response.status(409).entity(messages).build();
//		}
//		
//		return Response.ok().build();
//	}
	
	
	
	
//}
	


