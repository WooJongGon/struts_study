package struts.board.action;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import struts.board.dao.BoardDAO;
import struts.board.form.BoardForm;
import struts.board.form.FileForm;
import struts.util.FileManger;
import struts.util.PostgresqlConnector;

public class BoardUpdateAction extends DispatchAction {
	
	@Override
	protected ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		BoardForm param = (BoardForm)form;
		String mode = "";
		
		if(param.getMode() != null) {
			mode = param.getMode();
		}
		
		System.out.println(mode);
		if(mode.equals("R")) {
			return validatePassword(mapping, param, request, response);
		}else if(mode.equals("U")) {
			return updatePost(param);
		}else {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('잘못된 접근입니다.'); history.go(-1);</script>");
			out.flush();
			out.close();
			return null;
		}
	}
	
	private ActionForward validatePassword(ActionMapping mapping, BoardForm param,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception{
		Connection conn = PostgresqlConnector.getConnection();
		BoardDAO dao = new BoardDAO(conn);
		
		boolean validate = dao.validatePassword(param);
		
		PostgresqlConnector.close();
		
		if(validate) {
			return viewPost(mapping, param, request, response);
		}else {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('비밀번호가 틀렸습니다.'); history.go(-1);</script>");
			out.flush();
			out.close();
			return null;
		}
	}
	
	private ActionForward viewPost(ActionMapping mapping, BoardForm param,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		Connection conn = PostgresqlConnector.getConnection();
		BoardDAO dao = new BoardDAO(conn);
		FileForm file = new FileForm();
		
		int boardNo = param.getBoardNo();
		
		BoardForm item = dao.selectPost(boardNo);
		
		if(item.getFileNo() > 0) {
			file = dao.selectFile(item.getFileNo());
		}
		
		request.setAttribute("item", item);
		request.setAttribute("pageName", "게시글 수정");
		request.setAttribute("file", file);
		
		PostgresqlConnector.close();
		
		return mapping.findForward("update");
	}
	
	
	private ActionForward updatePost(BoardForm param) throws Exception{
		Connection conn = PostgresqlConnector.getConnection();
		BoardDAO dao = new BoardDAO(conn);
		int fileNo = param.getFileNo();
		int oldFileNo = dao.checkFile(param.getBoardNo());
		System.out.println(fileNo);
		System.out.println(oldFileNo);
		
		FormFile imageFile = param.getImage(); 
		
		if (fileNo == 0 && oldFileNo > 0) {
			FileForm file = dao.selectFile(oldFileNo);
			
			String filePath = getServlet().getServletContext().getRealPath("/") + "/uploads/" + file.getFileName();
			FileManger.delete(filePath);
			
			dao.deleteFile(oldFileNo);
		}
		
		if (imageFile != null && !imageFile.getFileName().isEmpty()) {
		    Map<String, String> imageInfo = null;
		    String filePath = getServlet().getServletContext().getRealPath("/") + "uploads";
		    	
		    imageInfo = FileManger.upload(imageFile, filePath);
		    fileNo = dao.insertFile(imageInfo);
		}
		
		dao.updatePost(param, fileNo);
		
		PostgresqlConnector.close();
		
		ActionForward forward = new ActionForward();
        forward.setRedirect(true);
        forward.setPath("/board/view.do?post=" + param.getBoardNo());
		
		return forward;
	}

}
