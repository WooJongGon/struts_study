package struts.board.action;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import struts.board.dao.BoardListDAO;
import struts.board.form.BoardForm;
import struts.util.PostgresqlConnector;

public class BoardListAction extends Action {
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		int page = 1;
		int pageSize = 7;
		int totalpages = 1;
		String searchTitle = "";
		
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		if(request.getParameter("title") != null) {
			searchTitle = request.getParameter("title");
		}
		Connection conn = PostgresqlConnector.getConnection();
		BoardListDAO dao = new BoardListDAO(conn);
		
		
		List<BoardForm> lists = dao.getLists(page, pageSize, searchTitle);
		totalpages = dao.pagination(pageSize, searchTitle);
		
		request.setAttribute("lists", lists);
		request.setAttribute("totalPages", totalpages);
		request.setAttribute("currentPage", page);
		request.setAttribute("title", searchTitle);
		request.setAttribute("pageName", "게시글 목록");
		
		PostgresqlConnector.close();
		
		return mapping.findForward("list");
	}
	
}
