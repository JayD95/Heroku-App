package nl.hu.v1wac.firstapp.persistence;

public class Account {
	private String Username;
	private String Password;
	private String role;
	
	public Account(String Username, String Password, String role) {
		this.Username = Username;
		this.Password = Password;
		this.role = role;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	

}


