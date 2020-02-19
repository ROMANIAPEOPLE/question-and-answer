package net.skhu.web;

import javax.servlet.http.HttpSession;

import net.skhu.domain.User;

public class HttpSessionUtils {

	public static final String USER_SESSION_KEY = "sessionedUser";

	public static boolean isLoginUser(HttpSession session) {
		Object sessionedUser = session.getAttribute(USER_SESSION_KEY);
		if(sessionedUser ==null) {
			return false;
		}
		return true;
	}
	
	public static User getUserSession(HttpSession session) {
		if(!isLoginUser(session)) { // isLoginUser == false면, sessio이 존재하지 않는다는 것
			return null;
		}
		return (User)session.getAttribute(USER_SESSION_KEY);
	}
	
	
	
	
}
