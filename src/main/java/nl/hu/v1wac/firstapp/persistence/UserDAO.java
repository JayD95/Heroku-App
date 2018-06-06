package nl.hu.v1wac.firstapp.persistence;

import java.sql.SQLException;

public interface UserDAO {
	public String findRoleForUser(String username, String password) throws SQLException;
	public boolean create(Account account) throws SQLException;

}
