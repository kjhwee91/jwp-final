package next.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import core.mvc.Controller;

public class AddAnswerController implements Controller {
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AnswerDao answerDao = new AnswerDao();
		QuestionDao questionDao = new QuestionDao();

		String writer = request.getParameter("writer");
		String contents = request.getParameter("contents");
		String questionId = request.getParameter("questionId");
		
		Answer answer = new Answer(writer, contents, Long.parseLong(questionId));
		answerDao.insert(answer);
		questionDao.updateAnswerCount(answer);
		
		return "/show.next";
	}

}
