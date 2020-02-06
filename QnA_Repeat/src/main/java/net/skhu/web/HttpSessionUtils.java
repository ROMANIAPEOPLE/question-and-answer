package net.skhu.web;

import javax.servlet.http.HttpSession;

import net.skhu.domain.User;

public class HttpSessionUtils {
	public static final String USER_SESSION_KEY = "sessionedUser";

	
	//session에 로그인 정보가 들어있는지 확인하는 메서드. null이라면 로그인이 되지 않은 상태임.
	public static boolean isLoginUser(HttpSession session) {
		Object sessionedUser = session.getAttribute(USER_SESSION_KEY);
		if (sessionedUser == null) {
			return false;
		}
		return true;
	}

	
	public static User getUserFromSession(HttpSession session) {
		if (!isLoginUser(session)) {
			return null;
		}
		return (User) session.getAttribute(USER_SESSION_KEY);

	}
}