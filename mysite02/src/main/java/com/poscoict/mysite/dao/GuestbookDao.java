package com.poscoict.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.poscoict.mysite.vo.GuestbookVo;


public class GuestbookDao {

	Connection conn = null;
	PreparedStatement psmt = null;
	ResultSet rs = null;
	public List<GuestbookVo> findAll() {
		List<GuestbookVo> list = new ArrayList<>();
		try {
			conn = getConnection();
			// 3. sql
			psmt = conn.prepareStatement(
					"select no, name, date_format(reg_date, '%Y/%m/%d %H:%i:%s') as reg_date, message from guestbook order by reg_date desc;");
			// 4. binding

			// 5. sql execute
			rs = psmt.executeQuery();
			while (rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String reg_date = rs.getString(3);
				String message = rs.getString(4);

				GuestbookVo vo = new GuestbookVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setReg_date(reg_date);
				vo.setMessage(message);

				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {

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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

	public boolean insert(GuestbookVo vo) {
		boolean result = false;

		try {
			conn = getConnection();

			// 3. sql
			psmt = conn.prepareStatement("insert into guestbook values(null, ?, ?, ?, now());");

			// 4. binding
			psmt.setString(1, vo.getName());
			psmt.setString(2, vo.getPassword());
			psmt.setString(3, vo.getMessage());

			// 5. sql execute
			psmt.executeUpdate();
			result = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}
	
	public boolean delete(String no, String password) {
		boolean result = false;
		
		try {
			conn = getConnection();
			psmt = conn.prepareStatement("delete from guestbook where no=? and password=?;");
			
			psmt.setString(1, no);
			psmt.setString(2, password);
			
			psmt.executeUpdate();
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {

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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	
	private Connection getConnection() throws SQLException { // 자기가 처리해야하는 exception을 회피하는 것 위로 던지는 것임
		Connection conn = null;
		try {
			// 1. JDBC 드라이버 로딩
			Class.forName("org.mariadb.jdbc.Driver");

			// 2. 연결하기
			String url = "jdbc:mysql://192.168.0.197:3307/webdb?characterEncoding=UTF-8&serverTimezone=UTC";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패 : " + e);
		}

		return conn;
	}
}
