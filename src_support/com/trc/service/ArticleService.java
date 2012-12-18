package com.trc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.trc.dao.ArticleDao;
import com.trc.domain.support.knowledgebase.Article;
import com.trc.domain.support.knowledgebase.Category;
import com.trc.exception.service.SupportServiceException;

@Service
public class ArticleService {

	@Autowired
	ArticleDao articleDao;

	public ArticleService() {
	}

	/****************************************************************************************/
	/********** Article Operations **************/
	/****************************************************************************************/

	public int saveArticle(Article article) throws SupportServiceException {
		try {
			return articleDao.saveArticle(article);
		} catch (DataAccessException e) {
			throw new SupportServiceException("Error creating article from DAO layer: " + e.getMessage());
		}
	}

	public Article getArticleById(int id) throws SupportServiceException {
		try {
			return articleDao.getArticleById(id);
		} catch (DataAccessException e) {
			throw new SupportServiceException("Error retrieving article from DAO layer: " + e.getMessage());
		}
	}

	public List<Article> getAllArticles() throws SupportServiceException {
		try {
			return articleDao.getAllArticles();
		} catch (DataAccessException e) {
			throw new SupportServiceException("Error retrieving articles from DAO layer: " + e.getMessage());
		}
	}

	public List<Article> searchArticlesByKeyword(String keyword) throws SupportServiceException {
		try {
			return articleDao.searchArticlesByKeyword(keyword);
		} catch (DataAccessException e) {
			throw new SupportServiceException("Error searching article from DAO layer: " + e.getMessage());
		}
	}

	/****************************************************************************************/
	/********** Category Operations **************/
	/****************************************************************************************/
	public List<Category> getAllCategories() throws SupportServiceException {
		return articleDao.getAllCategories();
	}

	public List<Article> getArticlesByCategory(int categoryId) throws SupportServiceException {
		try {
			return articleDao.getArticlesByCategory(categoryId);
		} catch (DataAccessException e) {
			throw new SupportServiceException("Error retrieving article category from DAO layer: " + e.getMessage());
		}
	}

	public Category getCategoryById(int categoryId) throws SupportServiceException {
		try {
			return articleDao.getCategoryById(categoryId);
		} catch (DataAccessException e) {
			throw new SupportServiceException("Error retrieving category from DAO layer: " + e.getMessage());
		}
	}

	public int createCategory(Category category) throws SupportServiceException {
		try {
			return articleDao.createCategory(category);
		} catch (DataAccessException e) {
			throw new SupportServiceException("Error creating category from DAO layer: " + e.getMessage());
		}
	}

}
