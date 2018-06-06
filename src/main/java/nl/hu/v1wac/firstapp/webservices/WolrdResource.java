package nl.hu.v1wac.firstapp.webservices;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import nl.hu.v1wac.firstapp.persistence.Country;
import nl.hu.v1wac.firstapp.persistence.ServiceProvider;

@Path("/countries")
public class WolrdResource {
	private WorldService service = ServiceProvider.getWorldService();

	@GET
	@Produces("application/json")
	public String getCountries() throws SQLException {
		WorldService service = ServiceProvider.getWorldService();
		JsonArray countryArray = buildJsonCountryArray(service.getAllCountries());

		return countryArray.toString();
	}

	@Path("/largestsurfaces")
	@GET
	@Produces("application/json")
	public String getGrootsteLanden() throws SQLException {
		WorldService service = ServiceProvider.getWorldService();
		JsonArray countryArray = buildJsonCountryArray(service.get10LargestSurfaces());

		return countryArray.toString();
	}

	@Path("/largestpopulations")
	@GET
	@Produces("application/json")
	public String getLandenMeesteInwoners() throws SQLException {
		WorldService service = ServiceProvider.getWorldService();
		JsonArray countryArray = buildJsonCountryArray(service.get10LargestPopulations());

		return countryArray.toString();
	}

	@POST
	@RolesAllowed("user")
	@Produces("application/json")
	public Response addCountry(@Context SecurityContext sc,
			@FormParam("countrycode") String code, 
			@FormParam("iso3") String iso3, 
			@FormParam("name") String name,
			@FormParam("continent") String continent, 
			@FormParam("region") String region,
			@FormParam("surface") double surface, 
			@FormParam("indepyear") int indepyear,
			@FormParam("population") int population, 
			@FormParam("lifeexpectancy") double lifeexpectancy,
			@FormParam("gnp") double gnp, 
			@FormParam("gnpoid") double gnpoid, 
			@FormParam("localname") String localname,
			@FormParam("governmentform") String governmentform, 
			@FormParam("headofstate") String headofstate,
			@FormParam("latitude") double latitude, 
			@FormParam("longitude") double longitude,
			@FormParam("capital") String capital) throws SQLException {
		System.out.println("worldresource test 1");
		boolean role = sc.isUserInRole("user");
		System.out.println("Heeft user rol?: " + role + " (in countryresource@POST)");
		 if (role) {
			 Country country = service.saveCountry(code, iso3, name, continent, region, surface, indepyear, population, lifeexpectancy, gnp, gnpoid, localname, governmentform, headofstate, latitude, longitude, capital);
			 return Response.ok(country).build();
		 }
		 Map<String, String> messages = new HashMap<String, String>();
		 messages.put("error", "Account is niet gemachtigd!");
		 return Response.status(409).entity(messages).build();
		 }
			
		 

	private JsonArray buildJsonCountryArray(List<Country> countries) {
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

		for (Country c : countries) {

			jsonArrayBuilder.add(buildJsonCountry(c));
		}

		return jsonArrayBuilder.build();
	}

	private JsonObjectBuilder buildJsonCountry(Country c) {
		JsonObjectBuilder job = Json.createObjectBuilder();

		job.add("capital", c.getCapital());
		job.add("code", c.getCode());
		job.add("continent", c.getContinent());
		job.add("government", c.getGovernment());
		job.add("iso3", c.getIso3());
		job.add("latitude", c.getLatitude());
		job.add("longitude", c.getLongitude());
		job.add("name", c.getName());
		job.add("population", c.getPopulation());
		job.add("region", c.getRegion());
		job.add("surface", c.getSurface());
		return job;
	}

}
