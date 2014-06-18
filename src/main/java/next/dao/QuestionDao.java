package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import next.model.Answer;
import next.model.Question;
import next.support.db.ConnectionManager;

public class QuestionDao {

	public void updateAnswerCount(Answer answer) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionManager.getConnection();
			String sql = "update QUESTIONS set countOfComment = countOfComment + 1 where questionId = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, answer.getQuestionId());
			pstmt.executeUpdate();
			System.out.println("save complete");
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}

			if (con != null) {
				con.close();
			}
		}			
	}

	public void insert(Question question) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionManager.getConnection();
			String sql = "INSERT INTO QUESTIONS (writer, title, contents, createdDate, countOfComment) VALUES (?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, question.getWriter());
			pstmt.setString(2, question.getTitle());
			pstmt.setString(3, question.getContents());
			pstmt.setTimestamp(4, new Timestamp(question.getTimeFromCreateDate()));
			pstmt.setInt(5, question.getCountOfComment());
			
			pstmt.executeUpdate();
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}

			if (con != null) {
				con.close();
			}
		}		
	}
	
	public ResultSet connection(String sql) throws SQLException{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			return rs;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}
	}
	

	public List<Question> findAll() throws SQLException {
		String sql = "SELECT questionId, writer, title, createdDate, countOfComment FROM QUESTIONS " + 
				"order by questionId desc";
		Connection con = ConnectionManager.getConnection();
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		try{
			List<Question> questions = new ArrayList<Question>();
			Question question = null;
			while (rs.next()) {
				question = new Question(
						rs.getLong("questionId"),
						rs.getString("writer"),
						rs.getString("title"),
						null,
						rs.getTimestamp("createdDate"),
						rs.getInt("countOfComment"));
				questions.add(question);
			}

			return questions;
		} finally {
			finalConnection(con, pstmt, rs);
		}
 		
	}
	
	public void finalConnection(Connection con, PreparedStatement pstmt, ResultSet rs) throws SQLException{
		if (rs != null) {
			rs.close();
		}
		if (pstmt != null) {
			pstmt.close();
		}
		if (con != null) {
			con.close();
		}
	}

	public Question findById(long questionId) throws SQLException {
		String sql = "SELECT questionId, writer, title, contents, createdDate, countOfComment FROM QUESTIONS " + 
				"WHERE questionId = ?";

		Connection con = ConnectionManager.getConnection();
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		try {
			pstmt.setLong(1, questionId);

			Question question = null;
			if (rs.next()) {
				question = new Question(
						rs.getLong("questionId"),
						rs.getString("writer"),
						rs.getString("title"),
						rs.getString("contents"),
						rs.getTimestamp("createdDate"),
						rs.getInt("countOfComment"));
			}

			return question;
		} finally {
			finalConnection(con, pstmt, rs);
}
	}
}
