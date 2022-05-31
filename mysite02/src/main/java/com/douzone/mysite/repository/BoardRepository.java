package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.PageVo;

public class BoardRepository {

	public boolean insert(BoardVo vo) {
		boolean result = false;
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = getConnection();
			String sql = null;
			if (vo.getG_no() == null) {
				sql = "insert into board(no, title, contents, hit, g_no, o_no, depth, user_no)"
						+ " values(null, ?, ?, 0, (select max(g_no) from board a) +1, ?, ?, ?)" ;
				pstmt = connection.prepareStatement(sql);
				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContents());
				pstmt.setLong(3, vo.getO_no());
				pstmt.setLong(4, vo.getDepth());
				pstmt.setLong(5, vo.getUser_no());
			}else {
				sql = "insert into board(no, title, contents, hit, g_no, o_no, depth, user_no)"
						+ " values(null, ?, ?, 0, ?, ?, ?, ?)";
				pstmt = connection.prepareStatement(sql);
				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContents());
				pstmt.setLong(3, vo.getG_no());
				pstmt.setLong(4, vo.getO_no());
				pstmt.setLong(5, vo.getDepth());
				pstmt.setLong(6, vo.getUser_no());
			}			

			int count = pstmt.executeUpdate();
			result = count == 1;
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
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

	public List<BoardVo> findAll(PageVo page, String query) {
		List<BoardVo> result = new ArrayList<>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection();
			String sql = null;
			
			if(query.equals("")) {
				sql = "select b.no, a.name, b.title, b.contents,b.hit,b.reg_date, b.g_no, b.o_no, b.depth, b.user_no"
					+ " from user a, board b" 
					+ " where a.no = b.user_no  order by g_no desc, o_no asc limit ?, 5";

				pstmt = connection.prepareStatement(sql);
				pstmt.setLong(1, (page.getPage()-1)*5);
			}
			else {
				sql = "select b.no, a.name, b.title, b.contents,b.hit,b.reg_date, b.g_no, b.o_no, b.depth, b.user_no"
						+ " from user a, board b"
						+ " where a.no = b.user_no"
						+ " and (a.name like concat('%',?,'%')"
						+ " or b.title like concat('%',?,'%'))"
						+ " order by g_no desc, o_no asc limit ?, 5";

				pstmt = connection.prepareStatement(sql);
				pstmt.setString(1, query);
				pstmt.setString(2, query);
				pstmt.setLong(3, (page.getPage()-1)*5);
			}	

			rs = pstmt.executeQuery();
			while (rs.next()) {
				Long no = rs.getLong(1);
				String user_name = rs.getString(2);
				String title = rs.getString(3);
				String contents = rs.getString(4);
				Long hit = rs.getLong(5);
				String reg_date = rs.getString(6);
				Long g_no = rs.getLong(7);
				Long o_no = rs.getLong(8);
				Long depth = rs.getLong(9);
				Long user_no = rs.getLong(10);

				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setUser_name(user_name);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setReg_date(reg_date);
				vo.setG_no(g_no);
				vo.setO_no(o_no);
				vo.setDepth(depth);
				vo.setUser_no(user_no);

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

	public BoardVo findByNo(Long no) {
		BoardVo result = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection();

			String sql = " select * from board where no = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, no);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				Long hit = rs.getLong(4);
				String reg_date = rs.getString(5);
				Long g_no = rs.getLong(6);
				Long o_no = rs.getLong(7);
				Long depth = rs.getLong(8);
				Long user_no = rs.getLong(9);

				result = new BoardVo();
				result.setNo(no);
				result.setTitle(title);
				result.setContents(contents);
				result.setHit(hit);
				result.setReg_date(reg_date);
				result.setG_no(g_no);
				result.setO_no(o_no);
				result.setDepth(depth);
				result.setUser_no(user_no);
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
	
	public Long findPage() {
		Long result = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection();

			String sql = " select count(*) from board";
			pstmt = connection.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getLong(1);
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
	
	public void update(BoardVo vo) {
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = getConnection();

			String sql = "update board set title=?, contents=? where no =?";
			pstmt = connection.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getNo());

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
	
	public void updateHit(BoardVo vo) {
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = getConnection();

			String sql = "update board set hit=? where no =?";
			pstmt = connection.prepareStatement(sql);

			pstmt.setLong(1, vo.getHit());
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

	public void updateO(BoardVo vo) {
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = getConnection();

			String sql = "Update board set o_no = o_no+1 where g_no = ? and o_no > ?";
			pstmt = connection.prepareStatement(sql);

			pstmt.setLong(1, vo.getG_no());
			pstmt.setLong(2, vo.getO_no());

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

	public void delete(Long no) {
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = getConnection();

			String sql = "delete from board where no = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, no);

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

	private Connection getConnection() throws SQLException {
		Connection connection = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String url = "jdbc:mysql://192.168.10.51:3306/webdb";
			connection = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}

		return connection;
	}
}
