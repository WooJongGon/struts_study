package struts.board.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

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
			return save(mapping, form, request, response);
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
	
	private ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		Connection conn = PostgresqlConnector.getConnection();
		BoardWriteDAO dao = new BoardWriteDAO(conn);
		int fileNo = -1;
		
		BoardForm param = (BoardForm)form;
		
		FormFile imageFile = param.getImage();
		String imagePath = null;
		
        if (imageFile != null && !imageFile.getFileName().isEmpty()) {
            String filePath = getServlet().getServletContext().getRealPath("/") + "uploads";
            System.out.println(filePath);

            File folder = new File(filePath);
            if (!folder.exists()) {
                folder.mkdir();
            }

            String fileName = System.currentTimeMillis() + "_" + imageFile.getFileName();
            File file = new File(filePath, fileName);
            try (InputStream stream = imageFile.getInputStream();
                 FileOutputStream out = new FileOutputStream(file)) {
                int bytesRead;
                byte[] buffer = new byte[1024];
                while ((bytesRead = stream.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
            imagePath = "uploads/" + fileName;
            fileNo = dao.insertFile(fileName, imageFile.getFileName(), imagePath);
            
        }
        
		int boardNo = dao.insertData(param, fileNo);
		
		PostgresqlConnector.close();
		
		ActionForward forward = new ActionForward();
        forward.setRedirect(true);
        forward.setPath("/board/view.do?post=" + boardNo);
        
        return forward;
	}
}
