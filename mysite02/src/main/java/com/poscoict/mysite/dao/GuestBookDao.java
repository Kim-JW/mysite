package com.poscoict.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.poscoict.mysite.vo.GuestBookVo;

public class GuestBookDao {
	private Connection getConnection() throws SQLException{
		Connection conn = null;
		
		try {
			// 1. JDBC 드라이버 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 2. 연결하기
			String url = "jdbc:mysql://localhost:3306/webdb?characterEncoding=UTF-8&serverTimezone=UTC";
			String user = "webdb";
			String passwd = "webdb";
			conn = DriverManager.getConnection(url, user, passwd);
		} catch(ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패 : " + e);
		} 
		
		return conn;
	}
	
	public boolean delete(GuestBookVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			// 3. SQL 준비
			String sql = "delete from guestbook where no = ? and password = ?;";
			psmt = conn.prepareStatement(sql);
			
			// 4. 바인딩(binding
			psmt.setLong(1, vo.getNo());
			psmt.setString(2, vo.getPassword());
			
			int deleteNum = psmt.executeUpdate();
			
			if(deleteNum == 1)	
				return true;
			
		} catch (SQLException e) {
			System.out.println("error : " + e);
		} finally {
			// 자원 정리
			try {
				if(rs != null) {
					rs.close();
				}
				if(psmt != null) {
					psmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
		
		
	}
	
	public List<GuestBookVo> findAll(){
		List<GuestBookVo> result = new ArrayList<GuestBookVo>();
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		try {
			// 1. JDBC 드라이버 로딩
			conn = getConnection();
			
			// 3. SQL 준비
			String sql = "select no, name, date_format(reg_date, '%Y/%m/%d %H:%i:%s') as reg_date, message \n"
					+ "from guestbook \n"
					+ "order by reg_date desc;";
			psmt = conn.prepareStatement(sql);
			
			// 4. 바인딩(binding
			
			// 5. SQL 실행
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String reg_date = rs.getString(3);
				String message = rs.getString(4);
				
				GuestBookVo vo = new GuestBookVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setMessage(message);
				vo.setReg_date(reg_date);
				
				result.add(vo);
			}
			
		} catch (SQLException e) {
			System.out.println("error : " + e);
		} finally {
			// 자원 정리
			try {
				if(rs != null) {
					rs.close();
				}
				if(psmt != null) {
					psmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public boolean insert(GuestBookVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			// 3. SQL 준비
			String sql = "insert into guestbook values(null, ?, ?, ?, now());";
			psmt = conn.prepareStatement(sql);
			
			// 4. 바인딩(binding
			psmt.setString(1, vo.getName());
			psmt.setString(2, vo.getPassword());
			psmt.setString(3, vo.getMessage());
			
			// 5. SQL 실행
			result = psmt.executeUpdate()== 1;
			
			
		} catch (SQLException e) {
			System.out.println("error : " + e);
		} finally {
			// 자원 정리
			try {
				if(rs != null) {
					rs.close();
				}
				if(psmt != null) {
					psmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return result;
	}
}
