package struts.board.action;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import struts.board.dao.BoardDAO;
import struts.board.form.BoardForm;
import struts.util.PostgresqlConnector;

public class BoardListAction extends Action {
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		int page = 1; //현재 페이지
		int pageSize = 5; //한 페이지에 보여질 게시글 수
		int totalRows = 1; //총 게시글 수
		int maxPage = 5; //페이지네이션 수
		String searchTitle = ""; //검색 키워드
		
		if(request.getParameter("page") != null) {
			try {
				page = Integer.parseInt(request.getParameter("page"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		if(request.getParameter("title") != null) {
			searchTitle = request.getParameter("title");
		}
		
		Connection conn = PostgresqlConnector.getConnection();
		BoardDAO dao = new BoardDAO(conn);
		
		List<BoardForm> lists = dao.getLists(page, pageSize, searchTitle);
		totalRows = dao.pagination(pageSize, searchTitle);
		
		int pageIdx = ((page - 1) / maxPage) + 1; //현재 페이지 블럭 인덱스
		if(pageIdx == 0) {
			pageIdx = 1;
		}
		
		//총 블럭 갯수
		int totalPages = totalRows % pageSize == 0 ? totalRows / pageSize : (totalRows / pageSize) + 1;
		if(totalPages == 0) {
			totalPages = 1;
		}
		
		int start = ((pageIdx - 1) * maxPage) + 1;
		if(start == 0) {
			start = 1;
		}
		
		int end = start + maxPage - 1;
		if(end > totalPages) {
			end = totalPages;
		}
		
		System.out.println("현재 페이지 >> " + page);
		
		request.setAttribute("pageName", "게시글 목록");
		request.setAttribute("lists", lists);
		request.setAttribute("currentPage", page);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("start", start);
		request.setAttribute("end", end);
		
		PostgresqlConnector.close();
		
		return mapping.findForward("list");
	}
	
}
