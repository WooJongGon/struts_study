package struts.board.action;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import struts.board.dao.BoardDAO;
import struts.board.form.BoardForm;
import struts.util.PostgresqlConnector;

public class BoardDeleteAction extends DispatchAction {
	
	@Override
	protected ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return validatePassword(mapping, form, request, response);
	}
	
	
	private ActionForward validatePassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception{
		Connection conn = PostgresqlConnector.getConnection();
		BoardDAO dao = new BoardDAO(conn);
		
		BoardForm param = (BoardForm)form;
		int boardNo = param.getBoardNo();
		
		if(param.getBoardNo() == 0) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('잘못된 접근입니다.'); history.go(-1);</script>");
			out.flush();
			out.close();
			return null;
		}
		
		boolean validate = dao.validatePassword(param);
		
		PostgresqlConnector.close();
		
		if(validate) {
            return deletePost(boardNo, request, response, mapping);
		}else {
			response.setContentType("text/html; charset=UTF-8");
		    PrintWriter out = response.getWriter();
		    out.println("<script>alert('비밀번호가 틀렸습니다.'); history.go(-1);</script>");
		    out.flush();
		    out.close();
            return null;
		}
	}
	
	private ActionForward deletePost(int boardNo, HttpServletRequest request, 
									HttpServletResponse response, ActionMapping mapping) 
									throws Exception{
		Connection conn = PostgresqlConnector.getConnection();
		BoardDAO dao = new BoardDAO(conn);
		
		dao.deletePost(boardNo);
		
		PostgresqlConnector.close();
		
		ActionForward forward = new ActionForward();
		
		String page = "1";
		String title = "";
		HttpSession session = request.getSession();
		
		if(session.getAttribute("page") != null) {
			page = (String)session.getAttribute("page");
		}
		if(session.getAttribute("title") != null) {
			title = (String)session.getAttribute("title");
		}
		System.out.println(page + title);
		
		String encodedTitle = URLEncoder.encode(title, "UTF-8");
	
        forward.setRedirect(true);
        forward.setPath("/board.do" + "?page=" + page + "&title=" + encodedTitle);
		
        return forward;
	}

}
