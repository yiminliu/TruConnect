package com.trc.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.domain.support.knowledgebase.Article;
import com.trc.domain.support.knowledgebase.Category;
import com.trc.service.ArticleService;

@Service
public class SupportManager {

	@Autowired
	private ArticleService articleService;
	
	public List<Article> getAllArticles(){
		return articleService.getAllArticles();
	}
	
	public Article getArticleById(int articleId){
		return articleService.getArticleById(articleId);
	}
	
	public List<Article> getArticlesByCategory(int categoryId){
		return articleService.getArticlesByCategory(categoryId);
	}
	
	public List<Article> searchArticlesByKeyword(String keyword){
		return articleService.searchArticlesByKeyword(keyword);
	}
	
	public List<Category> getAllCategories(){
		return articleService.getAllCategories();
	}
		
	public Category getCategoryById(int categoryId){
		return articleService.getCategoryById(categoryId);
	}
	
	public int insertArticle(Article article){
		return articleService.saveArticle(article);
	}
	
	public int createCategory(Category category){
		return articleService.createCategory(category);
	}
}
