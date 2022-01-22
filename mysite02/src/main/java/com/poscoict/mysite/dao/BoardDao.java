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
	public List<BoardVo> findAll(String kwd, String limit) {
		List<BoardVo> list = new ArrayList<BoardVo>();
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		if(kwd==null || kwd.equals("")) {
			kwd = "";
		}
		try {
			conn = getConnection();
//			psmt = conn.prepareStatement("select b.no, b.title, b.hit, b.contents, b.reg_date, b.depth, a.name, a.no \n"
//					+ " from user a, board b \n" + " where a.no = b.user_no \n" + " order by b.g_no desc, b.o_no asc ;");
			psmt = conn.prepareStatement("select b.no, b.title, b.hit, b.contents, b.reg_date, b.depth, a.name, a.no \n"
					+ " from user a, board b \n" + " where a.no = b.user_no and title like '%"+kwd+"%'\n" + " order by b.g_no desc, b.o_no asc " + limit + ";");
			rs = psmt.executeQuery();
			while (rs.next()) {
				BoardVo vo = new BoardVo();
				vo.setNo(rs.getLong(1));
				vo.setTitle(rs.getString(2));
				vo.setHit(rs.getInt(3));
				vo.setContents(rs.getString(4));
				vo.setRegDate(rs.getString(5));
				vo.setDepth(rs.getInt(6));
				vo.setUserName(rs.getString(7));
				vo.setUserNo(rs.getLong(8));
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

	@SuppressWarnings("resource")
	public boolean insert(BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement psmt = null;

		try {
			if (vo.getGroupNo()!=null) {
				conn = getConnection();
				psmt = conn.prepareStatement("update board set o_no = o_no+1 where o_no > ? and g_no = ?;");
				psmt.setInt(1, vo.getOrderNo());
				psmt.setInt(2, vo.getGroupNo());
				psmt.executeUpdate();
				psmt = conn.prepareStatement("insert into board values(null, ?, ?, 0, ?, ?, ?, now(), ?);");
				// no, title, contents, hit, g_no, o_no, depth, reg_date, user_no
				psmt.setString(1, vo.getTitle());
				psmt.setString(2, vo.getContents());
				psmt.setInt(3, vo.getGroupNo());
				psmt.setInt(4, vo.getOrderNo() + 1);
				psmt.setInt(5, vo.getDepth() + 1);
				psmt.setLong(6, vo.getUserNo());
			} else {
				conn = getConnection();
				psmt = conn.prepareStatement(
						"insert into board values (null, ? , ?, 0, ifnull((select max(g_no)+1 from board a),1), 1, 1, now(), ?);");
				psmt.setString(1, vo.getTitle());
				psmt.setString(2, vo.getContents());
				psmt.setLong(3, vo.getUserNo());
			}

			int count = psmt.executeUpdate();
			result = count == 1;
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
			result = count == 1;
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

	public BoardVo findByBno(String bno) {
		BoardVo result = null;
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();

			// 3. sql
			psmt = conn.prepareStatement(
					"select no, title, contents, hit, g_no, o_no, depth, reg_date, user_no from board where no = ?;");

			// 4. binding
			psmt.setString(1, bno);

			// 5. sql execute
			rs = psmt.executeQuery();
			if (rs.next()) {
				result = new BoardVo();
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				int hit = rs.getInt(4);
				int g_no = rs.getInt(5);
				int o_no = rs.getInt(6);
				int depth = rs.getInt(7);
				String reg_date = rs.getString(8);
				Long user_no = rs.getLong(9);

				result = new BoardVo();
				result.setNo(no);
				result.setTitle(title);
				result.setContents(contents);
				result.setHit(hit);
				result.setGroupNo(g_no);
				result.setOrderNo(o_no);
				result.setDepth(depth);
				result.setRegDate(reg_date);
				result.setUserNo(user_no);

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
	
	public Integer getTotalRows(String kwd) {
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		Integer count = 0;
		try {
			conn = getConnection();
			psmt = conn.prepareStatement("select count(no) from board where title like '%"+kwd+"%';");
			rs = psmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}

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
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return count;
	}
	public boolean update(String title, String contents, String no) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = getConnection();
			psmt = conn.prepareStatement("update board set title =?, contents=? where no = ? ;");
			psmt.setString(1, title);
			psmt.setString(2, contents);
			psmt.setString(3, no);
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
	
	public boolean hitUp(Integer bno) {
		boolean result = false;
		Connection conn= null;
		PreparedStatement psmt = null;
		try {
			conn = getConnection();
			psmt = conn.prepareStatement("update board set hit=hit+1 where no = ?");
			
			psmt.setInt(1, bno);
			int count = psmt.executeUpdate();
			result = count==1;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
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
