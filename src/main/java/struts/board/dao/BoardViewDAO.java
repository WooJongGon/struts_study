package struts.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import struts.board.form.BoardForm;
import struts.board.form.FileForm;

public class BoardViewDAO {
	
	private Connection conn;
	
	public BoardViewDAO(Connection conn) {
		this.conn = conn;
	}
	
	public BoardForm selectPost(int boardNo) {
		BoardForm result = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "select * from board where board_no = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				BoardForm dto = new BoardForm();
				
				dto.setBoardNo(rs.getInt("board_no"));
				dto.setContent(rs.getString("content"));
				dto.setName(rs.getString("name"));
				dto.setTitle(rs.getString("title"));
				dto.setCreated(rs.getString("created"));
				dto.setHitCount(rs.getInt("hit_count"));
				dto.setFileNo(rs.getInt("file_no"));
				
				result = dto;
			}
			
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	public FileForm selectFile(int fileNo) {
		FileForm result = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "select * from file where file_no = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, fileNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				FileForm dto = new FileForm();
				
				dto.setFileNo(rs.getInt("file_no"));
				dto.setFileName(rs.getString("file_name"));
				dto.setFileOrigin(rs.getString("file_origin"));
				dto.setLocalPath(rs.getString("local_path"));
				dto.setExtension(rs.getString("extension"));
				
				result = dto;
			}
			
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		
		return result;
	}
	
	public void updateHitCount(int boardNo) {
		PreparedStatement pstmt = null;		
		String sql; 		
		try { 			
			sql = "update board set hit_count = hit_count+1 where board_no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

}
