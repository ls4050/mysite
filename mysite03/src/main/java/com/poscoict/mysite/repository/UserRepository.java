package com.poscoict.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscoict.mysite.exception.UserRepositoryException;
import com.poscoict.mysite.vo.UserVo;

@Repository
public class UserRepository {
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SqlSession sqlSession;
	
	public boolean insert(UserVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = dataSource.getConnection();

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
	
	public boolean update(UserVo userVo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = dataSource.getConnection();
			if(userVo.getPassword().isBlank()) {
				psmt = conn.prepareStatement("update user set name=?, gender=? where no = ?;");
				psmt.setString(1, userVo.getName());
				psmt.setString(2, userVo.getGender());
				psmt.setLong(3, userVo.getNo());
			} else {
				psmt = conn.prepareStatement("update user set name=?, gender=?, password=? where no = ?;");
				psmt.setString(1, userVo.getName());
				psmt.setString(2, userVo.getGender());
				psmt.setString(3, userVo.getPassword());
				psmt.setLong(4, userVo.getNo());
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

	public UserVo findByEmailAndPassword(String email, String password) throws UserRepositoryException {
		UserVo result = null;
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();

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
			throw new UserRepositoryException(e.toString());
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

	public UserVo findByNo(Long no) {
		UserVo result = null;
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		try {
			conn = dataSource.getConnection();
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
