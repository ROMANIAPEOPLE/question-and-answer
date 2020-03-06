//로그인 여부 확인하는 클래스
package net.skhu.web;

import javax.servlet.http.HttpSession;

import net.skhu.domain.User;

public class HttpSessionUtils {

	public static final String USER_SEESION_KEY = "sessionedUser";
	
	public static boolean isLoginUser(HttpSession session) {
		Object sessionedUser = session.getAttribute(USER_SEESION_KEY);
		if(sessionedUser == null) {
			return false;
		}
		return true;
	}
	
	public static User getUserSession(HttpSession session) {
		if(!isLoginUser(session)) {
			return null;
		}
		return (User)session.getAttribute(USER_SEESION_KEY);
	}
	
}
