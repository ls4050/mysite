package com.poscoict.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.poscoict.mysite.vo.BoardVo;

public class BoardDao {
	public List<BoardVo> findAll() {
		List<BoardVo> list = new ArrayList<BoardVo>();
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
		conn = getConnection();
			psmt = conn.prepareStatement("select b.no, b.title, b.hit, b.contents, b.reg_date, a.name \n"
					+ " from user a, board b \n"
					+ " where a.no = b.user_no \n"
					+ " order by b.g_no desc, b.o_no asc;");
		rs = psmt.executeQuery();
		while(rs.next()) {
			BoardVo vo = new BoardVo();
			vo.setNo(rs.getLong(1));
			vo.setTitle(rs.getString(2));
			vo.setHit(rs.getInt(3));
			vo.setContents(rs.getString(4));
			vo.setRegDate(rs.getString(5));
			vo.setUserName(rs.getString(6));
			list.add(vo);
		}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (psmt != null) {
					psmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	
	public boolean insert(BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = getConnection();
			psmt = conn.prepareStatement("insert into board values (null, ? , ?, 0, ifnull((select max(g_no)+1 from board a),1), 1, 1, now(), ?);");
			psmt.setString(1, vo.getTitle());
			psmt.setString(2, vo.getContents());
			psmt.setLong(3, vo.getUserNo());
			
			int count = psmt.executeUpdate();
			result = count==1;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (psmt != null) {
					psmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public boolean delete(String no) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = getConnection();
			psmt = conn.prepareStatement("delete from board where no=?");
			psmt.setString(1, no);
			
			int count = psmt.executeUpdate();
			result = count==1;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (psmt != null) {
					psmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	private Connection getConnection() throws SQLException { // 자기가 처리해야하는 exception을 회피하는 것 위로 던지는 것임
		Connection conn = null;
		try {
			// 1. JDBC 드라이버 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 연결하기
			String url = "jdbc:mysql://localhost:3306/webdb?characterEncoding=UTF-8&serverTimezone=UTC";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패 : " + e);
		}

		return conn;
	}
}
