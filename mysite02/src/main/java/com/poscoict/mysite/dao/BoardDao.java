package com.poscoict.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.mysite.vo.GuestBookVo;
import com.poscoict.mysite.vo.UserVo;

public class BoardDao {
	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			// 1. JDBC 드라이버 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 연결하기
			String url = "jdbc:mysql://localhost:3306/webdb?characterEncoding=UTF-8&serverTimezone=UTC";
			String user = "webdb";
			String passwd = "webdb";
			conn = DriverManager.getConnection(url, user, passwd);
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패 : " + e);
		}

		return conn;
	}
	
	public long pageNum(int totalNum) {
		Long num = null;
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();

			// 3. SQL 준비
			String sql = "select count(no) from board;";
			psmt = conn.prepareStatement(sql);

			// 5. SQL 실행
			rs = psmt.executeQuery();
			if (rs.next()) {
				num = rs.getLong(1);
			}

		} catch (SQLException e) {
			System.out.println("error : " + e);
		} finally {
			// 자원 정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (psmt != null) {
					psmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if (num % totalNum > 0) {
			num = (num / totalNum) + 1;
		} else {
			num = num / totalNum;
		}
		
		return num;
	}
	
	public boolean update(Long no, String title, String contents) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			// 3. SQL 준비
			String sql = "update board set title = ?, contents = ? where no = ?;";
			psmt = conn.prepareStatement(sql);

			// 4. 바인딩(binding)
			psmt.setString(1, title);
			psmt.setString(2, contents);
			psmt.setLong(3, no);
			// 5. SQL 실행
			result = psmt.executeUpdate() == 1;

		} catch (SQLException e) {
			System.out.println("error : " + e);
		} finally {
			// 자원 정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (psmt != null) {
					psmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
	public boolean IncreaseCnt(BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			// 3. SQL 준비
			String sql = "update board set hit = hit+1 where no = ?;";
			psmt = conn.prepareStatement(sql);

			// 4. 바인딩(binding)
			psmt.setLong(1, vo.getNo());
			// 5. SQL 실행
			result = psmt.executeUpdate() == 1;

		} catch (SQLException e) {
			System.out.println("error : " + e);
		} finally {
			// 자원 정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (psmt != null) {
					psmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
		
	}

	public ArrayList<BoardVo> search(String keyword) {
		ArrayList<BoardVo> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			// 3. SQL 준비
			String sql = "select b.no, b.title, u.name, b.hit, b.reg_date, b.user_no, b.g_no, b.o_no, b.depth\n"
					+ "from board as b \n"
					+ "join user u\n"
					+ "on b.user_no = u.no\n"
					+ "where b.title like '%"+ keyword + "%' or u.name like '%" + keyword + "%' or b.contents like '%" + keyword + "%'\n"
					+ "order by g_no desc, o_no asc;";
			psmt = conn.prepareStatement(sql);
			
			// 5. SQL 실행
			rs = psmt.executeQuery();
		
			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String name = rs.getString(3);
				int hit = Integer.parseInt(rs.getString(4));
				String reg_date = rs.getString(5);
				Long userNo = rs.getLong(6);
				int groupNo = rs.getInt(7);
				int orderNo = rs.getInt(8);
				int depth = rs.getInt(9);

				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setUserName(name);
				vo.setHit(hit);
				vo.setRegDate(reg_date);
				vo.setUserNo(userNo);
				vo.setGroupNo(groupNo);
				vo.setOrderNo(orderNo);
				vo.setDepth(depth);

				list.add(vo);
				
			
			}
		} catch (SQLException e) {
			System.out.println("error : " + e);
		} finally {
			// 자원 정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (psmt != null) {
					psmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}

	public boolean replyUpdate(BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			// 3. SQL 준비
			String sql = "update board set o_no = o_no+1 where o_no > ? and g_no = ?;";
			psmt = conn.prepareStatement(sql);

			// 4. 바인딩(binding)
			psmt.setInt(1, vo.getOrderNo());
			psmt.setInt(2, vo.getGroupNo());
			// 5. SQL 실행
			result = psmt.executeUpdate() == 1;

		} catch (SQLException e) {
			System.out.println("error : " + e);
		} finally {
			// 자원 정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (psmt != null) {
					psmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public boolean delete(Long no) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			// 3. SQL 준비
			String sql = "delete from board where no = ?;";
			psmt = conn.prepareStatement(sql);

			// 4. 바인딩(binding
			psmt.setLong(1, no);

			int deleteNum = psmt.executeUpdate();

			if (deleteNum == 1)
				return true;

		} catch (SQLException e) {
			System.out.println("error : " + e);
		} finally {
			// 자원 정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (psmt != null) {
					psmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;

	}

	public BoardVo findBoardByNo(Long no) {
		BoardVo result = null;

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			// 3. SQL 준비
			String sql = "select title, contents, g_no, o_no, user_No, depth from board where no = ?;";
			psmt = conn.prepareStatement(sql);

			// 4. 바인딩(binding
			psmt.setLong(1, no);

			// 5. SQL 실행
			rs = psmt.executeQuery();
			if (rs.next()) {
				String title = rs.getString(1);
				String contents = rs.getString(2);
				int groupNo = rs.getInt(3);
				int orderNo = rs.getInt(4);
				Long userNo = rs.getLong(5);
				int depth = rs.getInt(6);

				result = new BoardVo();
				result.setNo(no);
				result.setTitle(title);
				result.setContents(contents);
				result.setUserNo(userNo);
				result.setGroupNo(groupNo);
				result.setOrderNo(orderNo);
				result.setDepth(depth);
			}

			return result;

		} catch (SQLException e) {
			System.out.println("error : " + e);
		} finally {
			// 자원 정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (psmt != null) {
					psmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public List<BoardVo> findAll(int startNum, int splitNum) {
		List<BoardVo> result = new ArrayList<BoardVo>();

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		try {
			// 1. JDBC 드라이버 로딩
			conn = getConnection();

			// 3. SQL 준비
			String sql = "select b.no, b.title, u.name, b.hit, b.reg_date, b.user_no, b.g_no, b.o_no, b.depth\n"
					+ "from board as b\n" + "join user u\n" + "on b.user_no = u.no\n" + "order by g_no desc, o_no asc limit " + startNum + ", " + splitNum +";";
			psmt = conn.prepareStatement(sql);

			// 4. 바인딩(binding)

			// 5. SQL 실행
			rs = psmt.executeQuery();

			while (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String name = rs.getString(3);
				int hit = Integer.parseInt(rs.getString(4));
				String reg_date = rs.getString(5);
				Long userNo = rs.getLong(6);
				int groupNo = rs.getInt(7);
				int orderNo = rs.getInt(8);
				int depth = rs.getInt(9);

				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setUserName(name);
				vo.setHit(hit);
				vo.setRegDate(reg_date);
				vo.setUserNo(userNo);
				vo.setGroupNo(groupNo);
				vo.setOrderNo(orderNo);
				vo.setDepth(depth);

				result.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("error : " + e);
		} finally {
			// 자원 정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (psmt != null) {
					psmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public boolean replyInsert(BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			// 3. SQL 준비
			String sql = "insert into board (no, title, contents, hit, g_no, o_no, depth, reg_date, user_no) "
					+ "values(null, ?, ?, ?, ?, ?, ? ,now(), ?);";
			psmt = conn.prepareStatement(sql);

			// 4. 바인딩(binding)
			psmt.setString(1, vo.getTitle());
			psmt.setString(2, vo.getContents());
			psmt.setLong(3, vo.getHit());
			psmt.setInt(4, vo.getGroupNo());
			psmt.setInt(5, vo.getOrderNo());
			psmt.setInt(6, vo.getDepth());
			psmt.setLong(7, vo.getUserNo());

			// 5. SQL 실행
			result = psmt.executeUpdate() == 1;

		} catch (SQLException e) {
			System.out.println("error : " + e);
		} finally {
			// 자원 정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (psmt != null) {
					psmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public boolean insert(BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			// 3. SQL 준비
			String sql = "insert into board (no, title, contents, hit, g_no, o_no, depth, reg_date, user_no) "
					+ "values(null, ?, ?, ?, (select ifnull(max(g_no) + 1, 1) from board as a_t), ?, ? ,now(), ?);";
			psmt = conn.prepareStatement(sql);

			// 4. 바인딩(binding)
			psmt.setString(1, vo.getTitle());
			psmt.setString(2, vo.getContents());
			psmt.setLong(3, vo.getHit());
			psmt.setInt(4, vo.getOrderNo());
			psmt.setInt(5, vo.getDepth());
			psmt.setLong(6, vo.getUserNo());

			// 5. SQL 실행
			result = psmt.executeUpdate() == 1;

		} catch (SQLException e) {
			System.out.println("error : " + e);
		} finally {
			// 자원 정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (psmt != null) {
					psmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
}
