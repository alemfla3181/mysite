package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.exception.GuestbookRepositoryException;
import com.douzone.mysite.vo.GuestbookVo;

@Repository
public class GuestbookRepository {	
	@Autowired
	private DataSource dataSource;
	
	public boolean insert(GuestbookVo vo) {
		boolean result = false;
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = dataSource.getConnection();

			String sql = "insert into guestbook(no,name,password,message) values(null, ?, ?, ?)";
			pstmt = connection.prepareStatement(sql);

			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getMessage());

			int count = pstmt.executeUpdate();
			result = count == 1;
		} catch (SQLException e) {
//			System.out.println("드라이버 로딩 실패:" + e);
			throw new GuestbookRepositoryException(e.toString());
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public List<GuestbookVo> findAll(String sort) {
		List<GuestbookVo> result = new ArrayList<>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = dataSource.getConnection();
			String sql = null;
			if(sort.equals("1"))
				sql = "select * from guestbook order by count desc";
			else
				sql = "select * from guestbook order by no desc";
			pstmt = connection.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String password = rs.getString(3);
				String message = rs.getString(4);
				String dateTime = rs.getString(5);
				Long count = rs.getLong(6);

				GuestbookVo vo = new GuestbookVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setPassword(password);
				vo.setMessage(message);
				vo.setDateTime(dateTime);
				vo.setCount(count);

				result.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
	public boolean delete(Long no, String password) {
		
		GuestbookVo vo = new GuestbookVo();
		vo.setNo(no);
		vo.setPassword(password);
		return delete(vo);
	}
	

	public boolean delete(GuestbookVo vo) {
		boolean result = false;
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = dataSource.getConnection();

			String sql = "delete from guestbook where no = ? and password = ?";
			pstmt = connection.prepareStatement(sql);

			pstmt.setLong(1, vo.getNo());
			pstmt.setString(2, vo.getPassword());

			int count = pstmt.executeUpdate();

			result = count == 1;
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

		return result;
	}

	public Long findCount(Long no) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		Long result = null;
		ResultSet rs = null;
		try {
			connection = dataSource.getConnection();

			String sql = "select count from guestbook where no=?";
			pstmt = connection.prepareStatement(sql);

			pstmt.setLong(1, no);

			rs = pstmt.executeQuery();
			if (rs.next())
				no = rs.getLong(1);

			result = no;

		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public void recommend(GuestbookVo vo, String updown) {
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = dataSource.getConnection();
			
			String sql = "update guestbook set count=? where no =?";
			pstmt = connection.prepareStatement(sql);
			if(updown.equals("up"))
				pstmt.setLong(1, vo.getCount() + 1);
			else
				pstmt.setLong(1, vo.getCount() - 1);
			pstmt.setLong(2, vo.getNo());

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