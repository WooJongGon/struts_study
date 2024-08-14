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
	
	public int insertData(BoardForm boardForm) {
		ResultSet rs = null;
		int boardNo = 0;
		
		PreparedStatement pstmt = null;
		String sql;
		LocalDate now = LocalDate.now();
		
		try {
			sql = "insert into board(name, pwd, title, content, created, hit_count)";
			sql += "values (?, ?, ?, ?, ?, 0)";
			
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setString(1, boardForm.getName());
			pstmt.setString(2, boardForm.getPwd());
			pstmt.setString(3, boardForm.getTitle());
			pstmt.setString(4, boardForm.getContent());
			pstmt.setString(5, now.toString());
			
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

}
