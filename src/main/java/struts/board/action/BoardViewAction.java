package struts.board.action;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import struts.board.dao.BoardDAO;
import struts.board.form.BoardForm;
import struts.board.form.FileForm;
import struts.util.PostgresqlConnector;

public class BoardViewAction extends Action {
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Connection conn = PostgresqlConnector.getConnection();
		BoardDAO dao = new BoardDAO(conn);
		int boardNo = Integer.parseInt(request.getParameter("post"));
		FileForm file = new FileForm();
		
		BoardForm item = dao.selectPost(boardNo);
		if(item.getFileNo() > 0) {
			file = dao.selectFile(item.getFileNo());
		}
		
		dao.updateHitCount(boardNo);
		
		request.setAttribute("item", item);
		request.setAttribute("pageName", "게시글 조회");
		request.setAttribute("file", file);
		
		PostgresqlConnector.close();
		
		return mapping.findForward("view");
	}
}

