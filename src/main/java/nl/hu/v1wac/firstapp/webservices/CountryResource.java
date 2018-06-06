package nl.hu.v1wac.firstapp.webservices;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import nl.hu.v1wac.firstapp.persistence.Country;
import nl.hu.v1wac.firstapp.persistence.ServiceProvider;

@Path("/countries/{countrycode: [A-Z][A-Z]}")
public class CountryResource {
	private WorldService service = ServiceProvider.getWorldService();
	

	@GET
	@Produces("application/json")
	public String getCountryInfo(@PathParam("countrycode") String code) throws SQLException {
		WorldService service = ServiceProvider.getWorldService();
		Country world = service.getCountryByCode(code);
		JsonObjectBuilder job = null;
		if(world!=null) {
		job = buildJsonCountry(world);
		}
		return job.build().toString();

	}

	
	
	@DELETE
	@RolesAllowed("user")
	@Produces("application/json")
	public Response deleteCountry( @Context SecurityContext sc, @PathParam("countrycode") String code) throws SQLException {
		boolean role = sc.isUserInRole("user");
		System.out.println("User " + role + " (in countryresource @delete)");
		if (role) {
			if (service.deleteCountry(code)) {
				//return Response.status(404).build();
				return Response.ok().build();
			}
		}
		Map<String, String> messages = new HashMap<String, String>();
		messages.put("error", "Account is niet gemachtigd");
		System.out.println("Account is niet gemachtigd");
		return Response.status(409).entity(messages).build();
	}

	@PUT
	@RolesAllowed("user")
	@Produces("application/json")
	public Response updateCountry(@Context SecurityContext sc, @PathParam("countrycode") String code,
								   @FormParam("name") String name,
								   @FormParam("capital") String capital,
								   @FormParam("region") String region,
								   @FormParam("surface") double surface,
								   @FormParam("population") int population) throws SQLException {
		boolean role = sc.isUserInRole("user");
		System.out.println("HEEFT ROL USER: " + role + " (in countryresource @put)");
		if (role) {
			Country country = service.updateCountry(code, name, capital, region,  surface,  population);
			return Response.ok(country).build();
			
		}
		Map<String, String> messages = new HashMap<String, String>();
		messages.put("error", "Account is niet gemachtigd");
		System.out.println("Account is niet gemachtigd");
		return Response.status(409).entity(messages).build();
		
		
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
