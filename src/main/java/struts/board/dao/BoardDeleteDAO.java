package struts.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import struts.board.form.BoardForm;

public class BoardDeleteDAO {
	
	private Connection conn;
	
	public BoardDeleteDAO(Connection conn) {
		this.conn = conn;
	}
	
	public boolean validatePassword(BoardForm boardForm) {
		boolean result = false;
		
		String pwd = boardForm.getPwd();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			
			sql = "select pwd from board where board_no = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardForm.getBoardNo());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				if(pwd.equals(rs.getString("pwd"))) {
					result = true;
				}
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	public void deletePost(int boardNo) {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "delete from board where board_no = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			pstmt.execute();
			
			pstmt.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

}
