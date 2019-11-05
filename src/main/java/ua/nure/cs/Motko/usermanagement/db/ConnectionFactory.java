package ua.nure.cs.Motko.usermanagement.db;

import java.sql.Connection;

public interface ConnectionFactory {
	Connection createConnection() throws DatabaseException;
	
}
