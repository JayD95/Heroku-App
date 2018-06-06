package nl.hu.v1wac.firstapp.persistence;

import nl.hu.v1wac.firstapp.webservices.WorldService;

public class ServiceProvider {
	private static WorldService worldService = new WorldService();

	public static WorldService getWorldService() {
		return worldService;
	}
}