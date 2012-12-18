package com.trc.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.trc.domain.support.knowledgebase.Article;
import com.trc.exception.management.SupportManagementException;
import com.trc.manager.SupportManager;
import com.trc.web.model.ResultModel;

@Controller
@RequestMapping("/support")
public class SupportController {
	@Autowired
	private SupportManager supportManager;

	Logger logger = LoggerFactory.getLogger("devLogger");

	@RequestMapping(method = RequestMethod.GET)
	public String showSupport() {
		return "support/support";
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSearchArticles(@RequestParam(value = "keyword", required = true) String keyword) {
		ResultModel resultModel = new ResultModel("support/faq/article");
		List<Article> articleList = new ArrayList<Article>();
		if (keyword != null && !keyword.equals("")) {
			try {
				articleList = supportManager.searchArticlesByKeyword(keyword);
			} catch (SupportManagementException te) {
				return resultModel.getAccessException();
			}
			resultModel.addObject("articleList", articleList);
			return resultModel.getSuccess();
		} else
			throw new IllegalArgumentException("No keyword specified.");
	}

}