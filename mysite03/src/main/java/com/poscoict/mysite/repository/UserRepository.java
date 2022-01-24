package com.poscoict.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.poscoict.mysite.vo.UserVo;

@Repository
public class UserRepository {
	public boolean insert(UserVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = getConnection();

			// 3. sql
			psmt = conn.prepareStatement("insert into user values(null, ?, ?, ?, ?, now());");

			// 4. binding
			psmt.setString(1, vo.getName());
			psmt.setString(2, vo.getEmail());
			psmt.setString(3, vo.getPassword());
			psmt.setString(4, vo.getGender());

			// 5. sql execute
			int count = psmt.executeUpdate();
			result = count == 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}
	
	public boolean update(String name, String password,String gender, Long no) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = getConnection();
			if(password.isBlank()) {
				psmt = conn.prepareStatement("update user set name=?, gender=? where no = ?;");
				psmt.setString(1, name);
				psmt.setString(2, gender);
				psmt.setLong(3, no);
			} else {
				psmt = conn.prepareStatement("update user set name=?, gender=?, password=? where no = ?;");
				psmt.setString(1, name);
				psmt.setString(2, gender);
				psmt.setString(3, password);
				psmt.setLong(4, no);
			}
			int count = psmt.executeUpdate();
			
			result = count == 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
				try {
					if(conn!=null) {
					conn.close();
					}
					if(psmt!=null) {
						psmt.close();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		return result;
	}

	public UserVo findByEmailAndPassword(String email, String password) {
		UserVo result = null;
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();

			// 3. sql
			psmt = conn.prepareStatement("select no, name from user where email = ? and password = ?;");

			// 4. binding
			psmt.setString(1, email);
			psmt.setString(2, password);

			// 5. sql execute
			rs = psmt.executeQuery();
			if (rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);

				result = new UserVo();
				result.setName(name);
				result.setNo(no);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
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

	public UserVo findByNo(Long no) {
		UserVo result = null;
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			psmt = conn.prepareStatement("select name, email, gender from user where no = ?;");

			psmt.setLong(1, no);

			rs = psmt.executeQuery();

			if (rs.next()) {
				result = new UserVo();

				result.setName(rs.getString(1));
				result.setEmail(rs.getString(2));
				result.setGender(rs.getString(3));
			}
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
				if(psmt != null) {
					psmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}
}
