package struts.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import struts.board.form.BoardForm;
import struts.board.form.FileForm;

public class BoardDAO {
	
	private Connection conn;
	
	public BoardDAO(Connection conn) {
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
			
			result = rows;
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
	
	public int insertData(BoardForm boardForm, int fileNo) {
		ResultSet rs = null;
		int boardNo = 0;
		
		PreparedStatement pstmt = null;
		String sql;
		LocalDate now = LocalDate.now();
		
		try {
			if(fileNo == 0) {
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
	
	public int insertFile(Map<String, String> fileInfo) {
			
		ResultSet rs = null;
		int fileNo = 0;
		
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "insert into file(file_name, file_origin, local_path, extension)";
			sql += "values (?, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setString(1, fileInfo.get("fileName"));
			pstmt.setString(2, fileInfo.get("fileOrigin"));
			pstmt.setString(3, fileInfo.get("localPath"));
			pstmt.setString(4, fileInfo.get("extension"));
			
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
	
	public void updatePost(BoardForm param, int fileNo) {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			if(fileNo == 0) {
				sql = "update board ";
				sql += "set title = ?, content = ?, file_no = ? ";
				sql += "where board_no = ?";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, param.getTitle());
				pstmt.setString(2, param.getContent());
				pstmt.setNull(3, Types.NULL);
				pstmt.setInt(4, param.getBoardNo());
			}else {
				sql = "update board ";
				sql += "set title = ?, content = ?, file_no = ? ";
				sql += "where board_no = ?";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, param.getTitle());
				pstmt.setString(2, param.getContent());
				pstmt.setInt(3, fileNo);
				pstmt.setInt(4, param.getBoardNo());
			}
			
			pstmt.execute();
			pstmt.close();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
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
