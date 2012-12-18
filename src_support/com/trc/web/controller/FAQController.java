package com.trc.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.trc.domain.support.knowledgebase.Article;
import com.trc.domain.support.knowledgebase.Category;
import com.trc.exception.management.SupportManagementException;
import com.trc.manager.SupportManager;
import com.trc.web.model.ResultModel;

@Controller
@RequestMapping("/support/faq")
public class FAQController {
	@Autowired
	private SupportManager supportManager;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getAllCategories() {
		List<Category> categoryList = null;
		ResultModel resultModel = new ResultModel("support/faq/faq");
		try {
			categoryList = supportManager.getAllCategories();
		} catch (SupportManagementException te) {
			return resultModel.getAccessException();
		}
		resultModel.addObject("categoryList", categoryList);
		return resultModel.getSuccess();
	}

	@RequestMapping(value = "/category/{categoryId}", method = RequestMethod.GET)
	public ModelAndView getArticlesByCategory(@PathVariable int categoryId) {
		List<Article> articleList = null;
		Category category = null;
		ResultModel resultModel = new ResultModel("support/faq/article");
		try {
			category = supportManager.getCategoryById(categoryId);
			articleList = supportManager.getArticlesByCategory(categoryId);
		} catch (SupportManagementException te) {
			return resultModel.getAccessException();
		}
		resultModel.addObject("category", category);
		resultModel.addObject("articleList", articleList);
		return resultModel.getSuccess();
	}

	/**
	 * This method is used to retrieve an article by using article id
	 * 
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/article/{articleId}", method = RequestMethod.GET)
	public ModelAndView getArticleById(@PathVariable int articleId) {
		List<Article> articleList = new ArrayList<Article>();
		Article article;
		ResultModel resultModel = new ResultModel("support/faq/article");
		try {
			article = supportManager.getArticleById(articleId);
			articleList.add(article);
		} catch (SupportManagementException te) {
			return resultModel.getAccessException();
		}
		resultModel.addObject("articleList", articleList);
		return resultModel.getSuccess();
	}

	/**
	 * This method is used to insert an article
	 * 
	 * @return String
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/create/article", method = RequestMethod.GET)
	public String insertArticle(Model model) {
		model.addAttribute("article", new Article());
		try {
			model.addAttribute("categoryList", supportManager.getAllCategories());
		} catch (SupportManagementException e) {

		}
		return "/admin/support/faq/createArticle";
	}

	/**
	 * This method is used to insert an article to the database
	 * 
	 * @return ModelAndView
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/create/article", method = RequestMethod.POST)
	public ModelAndView processInsertArticle(@ModelAttribute("article") Article article) {
		ResultModel resultModel = new ResultModel("support/faq/faq");
		try {
			int articleId = supportManager.insertArticle(article);
			resultModel.setSuccessViewName("support/faq/article/" + articleId);
			return resultModel.getSuccess();
		} catch (SupportManagementException te) {
			return resultModel.getAccessException();
		}

	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/create/category", method = RequestMethod.GET)
	public String insertCategory(Model model) {
		model.addAttribute("category", new Category());
		try {
			model.addAttribute("categoryList", supportManager.getAllCategories());
		} catch (SupportManagementException e) {

		}
		return "/admin/support/faq/createArticle";
	}

	/**
	 * This method is used to create a category in db
	 * 
	 * @return ModelAndView
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/create/category", method = RequestMethod.POST)
	public ModelAndView processInsertCategory(@RequestParam(value = "categoryName", required = true) String categoryName) {
		ResultModel resultModel = new ResultModel("support/faq/faq");
		Category category = new Category();
		category.setTitle(categoryName);
		try {
			int categoryId = supportManager.createCategory(category);
			resultModel.setSuccessViewName("support/faq/category/" + categoryId);
			try {
				resultModel.addObject("categoryList", supportManager.getAllCategories());
			} catch (SupportManagementException e) {

			}
			return resultModel.getSuccess();
		} catch (SupportManagementException te) {
			return resultModel.getAccessException();
		}
	}
}
