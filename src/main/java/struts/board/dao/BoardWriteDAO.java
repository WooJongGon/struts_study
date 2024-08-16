package struts.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

import struts.board.form.BoardForm;

public class BoardWriteDAO {
	
	private Connection conn;
	
	public BoardWriteDAO(Connection conn) {
		this.conn = conn;
	}
	
	public int insertData(BoardForm boardForm, int fileNo) {
		ResultSet rs = null;
		int boardNo = 0;
		
		PreparedStatement pstmt = null;
		String sql;
		LocalDate now = LocalDate.now();
		
		try {
			if(fileNo == -1) {
				sql = "insert into board(name, pwd, title, content, created, hit_count)";
				sql += "values (?, ?, ?, ?, ?, 0)";
				
				pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				
				pstmt.setString(1, boardForm.getName());
				pstmt.setString(2, boardForm.getPwd());
				pstmt.setString(3, boardForm.getTitle());
				pstmt.setString(4, boardForm.getContent());
				pstmt.setString(5, now.toString());
			}else {
				sql = "insert into board(name, pwd, title, content, created, hit_count, file_no)";
				sql += "values (?, ?, ?, ?, ?, 0, ?)";
				
				pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				
				pstmt.setString(1, boardForm.getName());
				pstmt.setString(2, boardForm.getPwd());
				pstmt.setString(3, boardForm.getTitle());
				pstmt.setString(4, boardForm.getContent());
				pstmt.setString(5, now.toString());
				pstmt.setInt(6, fileNo);
			}
			
			int result = pstmt.executeUpdate();
			
			if (result > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    boardNo = rs.getInt(1); 
                }
            }
			
			pstmt.close();
			rs.close();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return boardNo;
	}
	
	public int insertFile(String fileName, String fileOrigin, String filePath) {
		
		ResultSet rs = null;
		int fileNo = 0;
		int extensionIdx = fileOrigin.lastIndexOf(".");
		
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "insert into file(file_name, file_origin, local_path, extension)";
			sql += "values (?, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setString(1, fileName);
			pstmt.setString(2, fileOrigin);
			pstmt.setString(3, filePath);
			pstmt.setString(4, fileOrigin.substring(extensionIdx + 1));
			
			int result = pstmt.executeUpdate();
			
			if (result > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    fileNo = rs.getInt(1); 
                }
            }
			
			pstmt.close();
			rs.close();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return fileNo;
	}

}
