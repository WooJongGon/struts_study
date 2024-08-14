package struts.board.action;

import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import struts.board.dao.BoardWriteDAO;
import struts.board.form.BoardForm;
import struts.util.PostgresqlConnector;

public class BoardWriteAction extends DispatchAction {
	
	@Override
	protected ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BoardForm param = (BoardForm)form;
		
		if(param.getMode() == null) {
			return write(mapping, param, request, response);
		}else if(param.getMode().equals("I")) {
			return save(mapping, param, request, response);
		}else {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('잘못된 접근입니다.'); history.go(-1);</script>");
			out.flush();
			out.close();
			return null;
		}
		
	}
	
	private ActionForward write(ActionMapping mapping, BoardForm param,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		request.setAttribute("pageName", "게시글 작성");
		
		return mapping.findForward("write");
	}
	
	private ActionForward save(ActionMapping mapping, BoardForm param,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		Connection conn = PostgresqlConnector.getConnection();
		BoardWriteDAO dao = new BoardWriteDAO(conn);
		
		int boardNo = dao.insertData(param);
		
		PostgresqlConnector.close();
		
		ActionForward forward = new ActionForward();
        forward.setRedirect(true);
        forward.setPath("/board/view.do?post=" + boardNo);
        
        return forward;
	}
}
