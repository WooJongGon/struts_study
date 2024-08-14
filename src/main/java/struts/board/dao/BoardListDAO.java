package struts.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import struts.board.form.BoardForm;

public class BoardListDAO{
	
	private Connection conn;
	
	public BoardListDAO(Connection conn) {
		this.conn = conn;
	}
	
	public List<BoardForm> getLists(int page, int pageSize, String searchTitle){
		List<BoardForm> lists = new ArrayList<BoardForm>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			int start = (page - 1) * pageSize;
			
			sql = "select * from board where title like ? ";
			sql += "order by board_no DESC limit ? offset ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + searchTitle + "%");
			pstmt.setInt(2, pageSize);
			pstmt.setInt(3, start);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardForm dto = new BoardForm();
				
				dto.setBoardNo(rs.getInt("board_no"));
				dto.setName(rs.getString("name"));
				dto.setTitle(rs.getString("title"));
				dto.setCreated(rs.getString("created"));
				dto.setHitCount(rs.getInt("hit_count"));
				
				lists.add(dto);
			}
			
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return lists;
	}
	
	public int pagination(int pageSize, String searchTitle){
		int result = 1;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "select count(*) from board where title like ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + searchTitle + "%");
			rs = pstmt.executeQuery();
			
			rs.next();
			int rows = rs.getInt(1);
			
			rs.close();
			pstmt.close();
			
			int totalPages = (int) Math.ceil(rows * 1.0 / pageSize);
			result = totalPages;
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		if (result == 0) {
			result = 1;
		}
		
		return result;
	}
	

}
