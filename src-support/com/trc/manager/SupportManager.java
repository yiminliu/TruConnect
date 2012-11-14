package com.trc.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.domain.support.knowledgebase.Article;
import com.trc.domain.support.knowledgebase.Category;
import com.trc.exception.management.SupportManagementException;
import com.trc.exception.management.TicketManagementException;
import com.trc.exception.service.SupportServiceException;
import com.trc.exception.service.TicketServiceException;
import com.trc.service.ArticleService;

@Service
public class SupportManager {

	@Autowired
	private ArticleService articleService;
	
	public List<Article> getAllArticles() throws SupportManagementException{
		try{
		   return articleService.getAllArticles();
		}
		catch (SupportServiceException e) {
		   throw new SupportManagementException(e.getMessage(), e.getCause());
		}
	}
	
	public Article getArticleById(int articleId)throws SupportManagementException{
		try{
			return articleService.getArticleById(articleId);
		}
		catch (SupportServiceException e) {
		   throw new SupportManagementException(e.getMessage(), e.getCause());
		}
	}
	
	public List<Article> getArticlesByCategory(int categoryId)throws SupportManagementException{
		try{
			return articleService.getArticlesByCategory(categoryId);
		}
		catch (SupportServiceException e) {
		   throw new SupportManagementException(e.getMessage(), e.getCause());
		}		
	}
	
	public List<Article> searchArticlesByKeyword(String keyword)throws SupportManagementException{
		try{
			return articleService.searchArticlesByKeyword(keyword);
		}
		catch (SupportServiceException e) {
		   throw new SupportManagementException(e.getMessage(), e.getCause());
		}
	}
	
	public List<Category> getAllCategories()throws SupportManagementException{
		try{
			return articleService.getAllCategories();
		}
		catch (SupportServiceException e) {
		   throw new SupportManagementException(e.getMessage(), e.getCause());
		}
	}
		
	public Category getCategoryById(int categoryId)throws SupportManagementException{
		try{
			return articleService.getCategoryById(categoryId);
		}
		catch (SupportServiceException e) {
		   throw new SupportManagementException(e.getMessage(), e.getCause());
		}
	}
	
	public int insertArticle(Article article)throws SupportManagementException{
		try{
			List<Category> cl = article.getCategories(); 		
		    for(Category c : cl){
		        c.addArticle(article);
   	        }
		    return articleService.saveArticle(article);
		}
		catch (SupportServiceException e) {
		   throw new SupportManagementException(e.getMessage(), e.getCause());
		}    
	}
	
	public int createCategory(Category category)throws SupportManagementException{
		try{
			return articleService.createCategory(category);
		}
		catch (SupportServiceException e) {
		   throw new SupportManagementException(e.getMessage(), e.getCause());
		}
	}
}
