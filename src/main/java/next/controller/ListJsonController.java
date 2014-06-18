package next.controller;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.QuestionDao;
import next.model.Question;

import com.google.gson.Gson;

import core.mvc.Controller;


public class ListJsonController implements Controller {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QuestionDao questionDao = new QuestionDao();
		List<Question> questions;
		questions = questionDao.findAll();
		int size = questions.size();
		List<String> list = new ArrayList<String>(size);
		
		for (int i=0 ; i<size ; i++){
			Gson gson = new Gson();
			String gjson = gson.toJson(questions.get(i));
			list.add(gjson);
		}

		PrintWriter writer = response.getWriter();
		writer.println(list);
		return "api";
	}
}
