package nl.hu.v1wac.firstapp.persistence;
import nl.hu.v1wac.firstapp.persistence.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;




public class CountryPostgresDaoImpl extends PostgresBaseDao implements CountryDao{

	
	private List<Country> selectCountry(String query){
		List<Country> results = new ArrayList<Country>();
	    try (Connection con = super.getConnection()) {
	    	PreparedStatement pstmt = con.prepareStatement(query);
	    	ResultSet dbrs = pstmt.executeQuery();
	    	while (dbrs.next()) {
	    		String code = dbrs.getString("code");
	    		String iso = dbrs.getString("iso3");
	    		String name = dbrs.getString("name");
	    		String capital = dbrs.getString("capital");
	    		String continent = dbrs.getString("continent");
	    	    String region = dbrs.getString("region");
	    	    double surface = dbrs.getDouble("surfacearea");
	    	    int population = dbrs.getInt("population");
	    	    String gov = dbrs.getString("governmentform");
	    	    double lat = dbrs.getDouble("latitude");
	    	    double longi = dbrs.getDouble("longitude");
	    	    Country newCountry = new Country(code, iso, name, capital, continent, region, surface, population, gov, lat, longi);
	    		results.add(newCountry);
	    	}
		}catch (SQLException sqle) {sqle.printStackTrace();}
	    return results;
	}
	    	
	
	public List<Country> findAll() throws SQLException {
		return selectCountry("SELECT code, iso3, name, capital, continent, region, surfacearea, population, governmentform, latitude, longitude FROM country");
				
	}

	
	public Country findByCode(String code) throws SQLException {
		return selectCountry("SELECT code, iso3, name, continent, region, surfacearea, population, governmentform, latitude, longitude, capital from country WHERE code = '"+code+"'").get(0);
	}


	public List<Country> find10LargestPopulations() {
		return selectCountry("SELECT code, iso3, name, continent, region, surfacearea, population, governmentform, latitude, longitude, capital from country ORDER BY population DESC limit 10");
	}

	
	public List<Country> find10LargestSurfaces() {
		return selectCountry("SELECT code, iso3, name, continent, region, surfacearea, population, governmentform, latitude, longitude, capital from country ORDER BY SURFACEAREA DESC limit 10");
	}

	
	public boolean update(Country country) throws SQLException {
		boolean updated  = false;
		
		boolean countryBestaat = findByCode(country.getCode()) != null;
		if (countryBestaat) {
			String query = "UPDATE COUNTRY SET NAME = ?, CAPITAL = ?, REGION = ?, SURFACEAREA = ?, POPULATION = ? WHERE CODE = '" + country.getCode() + "'";
			try (Connection con = super.getConnection()) {
				PreparedStatement pstmt = con.prepareStatement(query);
				System.out.println(country.getName() + " " + country.getCapital());
				
				pstmt.setString(1, country.getName());
				pstmt.setString(2, country.getCapital());
				pstmt.setString(3, country.getRegion());
				pstmt.setDouble(4, country.getSurface());
				pstmt.setInt(5, country.getPopulation());
				
				int result = pstmt.executeUpdate();
				pstmt.close();
				
				if (result == 0)
					return false;
				else
					updated = true;
			}
		}
		
		return updated;
	}
	

	
	public boolean delete(Country country) throws SQLException {
		boolean deleted = false;
		boolean countryExists = findByCode(country.getCode()) != null;

		if (countryExists) {
			String query = "DELETE FROM country WHERE code = '" + country.getCode()+"'";

			try (Connection con = getConnection()) {
				System.out.println("Connectie gemaakt (Delete)!");
				Statement stmt = con.createStatement();
				
				if (stmt.executeUpdate(query) == 1) { 
					deleted = true;
				}
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}

		return deleted;
	}

	
	public boolean save(Country country) throws SQLException {
		boolean saved  = false;
		
		
		String query = "INSERT INTO COUNTRY (code, iso3, name, continent, region, surfacearea"
				+ ", indepyear, population, lifeexpectancy, gnp, gnpold, localname"
				+ ", governmentform, headofstate, latitude, longitude, capital) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
		try (Connection con = super.getConnection()) {
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, country.getCode());
			pstmt.setString(2, country.getIso3());
			pstmt.setString(3, country.getName());
			pstmt.setString(4, country.getContinent());
			pstmt.setString(5, country.getRegion());
			pstmt.setDouble(6, country.getSurface());
			pstmt.setInt(7, country.getIndepyear());
			pstmt.setInt(8, country.getPopulation());
			pstmt.setDouble(9, country.getLifeexpectancy());
			pstmt.setDouble(10, country.getGnp());
			pstmt.setDouble(11, country.getGnpoid());
			pstmt.setString(12, country.getLocalname());
			pstmt.setString(13, country.getGovernment());
			pstmt.setString(14, country.getHeadofstate());
			pstmt.setDouble(15, country.getLatitude());
			pstmt.setDouble(16, country.getLongitude());
			pstmt.setString(17, country.getCapital());
			
			int result = pstmt.executeUpdate();
			pstmt.close();
			
			if (result == 0)
				return false;
			else
				saved = true;
		}
		
		
		return saved;
	}

}
