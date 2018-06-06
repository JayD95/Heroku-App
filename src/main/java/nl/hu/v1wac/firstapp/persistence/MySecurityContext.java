package nl.hu.v1wac.firstapp.persistence;

import java.security.Principal;
import javax.ws.rs.core.SecurityContext;

public class MySecurityContext implements SecurityContext {
	private String name;
	private String role;
	private boolean isSecure;

	public MySecurityContext(String name, String role, boolean isSecure) {
		this.name = name;
		this.role = role;
		this.isSecure = isSecure;
	}

	@Override
	public Principal getUserPrincipal() {
		return new Principal() {
			public String getName() {
				return name;
			}
		};
	}

	@Override
	public boolean isSecure() {
		return isSecure;
	}

	@Override
	public String getAuthenticationScheme() {
		return "Bearer";
	}

	@Override
	public boolean isUserInRole(String role) {
		return this.role.equals(role);
	}

}
