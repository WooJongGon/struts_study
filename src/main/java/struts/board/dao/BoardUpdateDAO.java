package struts.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import struts.board.form.BoardForm;
import struts.board.form.FileForm;

public class BoardUpdateDAO {
	
	private Connection conn;
	
	public BoardUpdateDAO(Connection conn) {
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
			
			if(rs.next()) {
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
	
	public void updatePost(BoardForm param) {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "update board ";
			sql += "set title = ?, content = ? ";
			sql += "where board_no = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, param.getTitle());
			pstmt.setString(2, param.getContent());
			pstmt.setInt(3, param.getBoardNo());
			
			pstmt.execute();
			
			pstmt.close();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
	}

}
