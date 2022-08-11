package com.softwify.library.constants;

public class DBConstants {
	public static final String GET_AUTHORS = "select * from author order by firstname, lastname";
	public static final String DELETE_AUTHOR = "delete from author where id = ?";
	public static final String ADD_AUTHOR = "insert into author (`firstname`, `lastname`) values (?, ?)";
	public static final String CHECK_IF_AUTHOR_EXIST = "select count(*) from author where firstname = ? and lastname = ?";
	public static final String GET_TEXTBOOKS = "select * from author a, textbook t where a.id = t.author_id order by title";
}
