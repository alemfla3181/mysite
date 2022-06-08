package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.UserVo;

@Repository
public class UserRepository {
	
	@Autowired
	private DataSource dataSource;

	public boolean insert(UserVo vo) {
		boolean result = false;
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = dataSource.getConnection();
			
			String sql ="insert into user values(null, ?, ?, ?, ?, now())";
			pstmt = connection.prepareStatement(sql);

			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());			
			
			int count = pstmt.executeUpdate();
			result = count == 1;
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;		
	}

	public UserVo finByEmailAndPassword(UserVo vo) {
		UserVo result = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = dataSource.getConnection();			

			String sql = "select no, name from user where email=? and password=?";
			pstmt = connection.prepareStatement(sql);	
			
			pstmt.setString(1, vo.getEmail());
			pstmt.setString(2, vo.getPassword());

			rs = pstmt.executeQuery();			

			if(rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				
				result = new UserVo();
				result.setNo(no);
				result.setName(name);				
			}
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public UserVo findByNo(Long no) {
		UserVo result = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = dataSource.getConnection();			

			String sql = " select no, name, email, gender from user where no = ?";
			pstmt = connection.prepareStatement(sql);	
			pstmt.setLong(1, no);

			rs = pstmt.executeQuery();			

			if(rs.next()) {
				no = rs.getLong(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				String gender = rs.getString(4);
				
				result = new UserVo();
				result.setNo(no);		
				result.setName(name);
				result.setEmail(email);
				result.setGender(gender);
			}
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public void updateUser(UserVo vo) {
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = dataSource.getConnection();

			
			if("".equals(vo.getPassword())) {
				String sql = "update user set name=?, gender=? where no =?";
				pstmt = connection.prepareStatement(sql);

				pstmt.setString(1, vo.getName());
				pstmt.setString(2, vo.getGender());
				pstmt.setLong(3, vo.getNo());								
			}else {
				String sql = "update user set name=?, password=?, gender=? where no =?";
				pstmt = connection.prepareStatement(sql);

				pstmt.setString(1, vo.getName());
				pstmt.setString(2, vo.getPassword());
				pstmt.setString(3, vo.getGender());
				pstmt.setLong(4, vo.getNo());
			}
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null)
					connection.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

