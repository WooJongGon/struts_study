package struts.board.action;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import struts.board.dao.BoardViewDAO;
import struts.board.form.BoardForm;
import struts.util.PostgresqlConnector;

public class BoardViewAction extends Action {
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Connection conn = PostgresqlConnector.getConnection();
		BoardViewDAO dao = new BoardViewDAO(conn);
		int boardNo = Integer.parseInt(request.getParameter("post"));
		
		BoardForm item = dao.selectPost(boardNo);
		dao.updateHitCount(boardNo);
		
		request.setAttribute("item", item);
		request.setAttribute("pageName", "게시글 조회");
		
		PostgresqlConnector.close();
		
		return mapping.findForward("view");
	}
}

