package net.skhu.web;

import javax.servlet.http.HttpSession;

import net.skhu.domain.User;

public class HttpSessionUtils {
	public static final String USER_SESSION_KEY = "sessionUser";
//session에 로그인 정보가 들어있는지 확인할 고정변수

	public static boolean isLoginUser(HttpSession session) {
		Object sessionedUser = session.getAttribute(USER_SESSION_KEY);
		if(sessionedUser == null) {
			return false;
		}
		return true;
	}
//	sessionedUser 가 false면 로그인 X   true면 로그인OK
	public static User getUserFromSession(HttpSession session) {
		if(!isLoginUser(session)) {
			return null;
		}
		return (User) session.getAttribute(USER_SESSION_KEY);
	}
	
}
