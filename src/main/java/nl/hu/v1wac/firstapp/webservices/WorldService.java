package nl.hu.v1wac.firstapp.webservices;

import java.sql.SQLException;
import java.util.List;

import nl.hu.v1wac.firstapp.persistence.Country;
import nl.hu.v1wac.firstapp.persistence.CountryDao;
import nl.hu.v1wac.firstapp.persistence.CountryPostgresDaoImpl;

public class WorldService {
	private CountryDao cDAO = new CountryPostgresDaoImpl();
	
	
	
	public List<Country> getAllCountries() throws SQLException {
		return cDAO.findAll();
	}
	
	public List<Country> get10LargestPopulations() throws SQLException {
		return cDAO.find10LargestPopulations();
	}

	public List<Country> get10LargestSurfaces() throws SQLException {
		return cDAO.find10LargestPopulations();
	}
	
	public Country getCountryByCode(String code) throws SQLException {
		Country result = null;
		
		for (Country c : cDAO.findAll()) {
			if (c.getCode().equals(code)) {
				result = c;
				break;
			}
		}
		
		return result;
	}
	
	public Country updateCountry(String code, String name, String capital, String region, double surface, int population) throws SQLException {
		Country c = cDAO.findByCode(code);
		
			c.setName(name);
			c.setCapital(capital);
			c.setRegion(region);
			c.setSurface(surface);
			c.setPopulation(population);

			if(cDAO.update(c)) {
				return cDAO.findByCode(code);
			}
			
		
		return c;
	}
	
	public boolean deleteCountry(String code) throws SQLException {
		boolean resultaat = false;
		Country c = cDAO.findByCode(code);
		if (c != null) {
			resultaat = cDAO.delete(c);
		} else {
			throw new IllegalArgumentException("Country bestaat niet!");
		}
		return resultaat;
	}
	
	public Country saveCountry (String code, String iso3, String name, String continent, String region, 
			double surface, int indepyear, int population, double lifeexpectancy, 
			double gnp, double gnpoid, String localname, String governmentform,
			String headofstate, double latitude, double longitude, String capital) throws SQLException {
		Country c = new Country(code, iso3, name, continent, region, surface, indepyear, population, 
				lifeexpectancy, gnp, gnpoid, localname, governmentform, 
				headofstate, latitude, longitude, capital);
		c.setCode(code);
		c.setIso3(iso3);
		c.setName(name);
		c.setContinent(continent);
		c.setRegion(region);
		c.setSurface(surface);
		c.setIndepyear(indepyear);
		c.setPopulation(population);
		c.setLifeexpectancy(lifeexpectancy);
		c.setGnpoid(gnpoid);
		c.setGnpoid(gnpoid);
		c.setLocalname(localname);
		c.setGovernment(governmentform);
		c.setHeadofstate(headofstate);
		c.setLatitude(latitude);
		c.setLongitude(longitude);
		c.setCapital(capital);
		
		
		if(cDAO.save(c)) {
			return c;
		}
		return c;
	}

}
