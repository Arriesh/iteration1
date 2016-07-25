package com.icare.commons.exceptions;

import java.sql.SQLException;

public class TestException {
	public void throwException() throws SQLException{
		throw new SQLException();
	}
}
