package com.softwify.library.constants;

public class DBConstants {
	public static final String GET_AUTHORS = "select * from author order by firstname, lastname";
	public static final String GET_AUTHOR_ID = "select id from where id = ?";
	public static final String DELETE_AUTHOR = "delete from author where id = ?";
}
