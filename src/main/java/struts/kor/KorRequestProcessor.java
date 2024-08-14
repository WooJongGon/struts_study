package struts.kor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.RequestProcessor;

public class KorRequestProcessor extends RequestProcessor {

	protected boolean processPreprocess(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			request.setCharacterEncoding("utf-8");
			return true;
		} catch (Exception e) {
			System.out.println("Exception is created : " + e.getMessage());
			return false;
		}
	}

}
