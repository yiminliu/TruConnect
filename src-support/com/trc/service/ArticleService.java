package com.trc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.dao.ArticleDao;
import com.trc.domain.support.knowledgebase.Article;
import com.trc.domain.support.knowledgebase.Category;

@Service
public class ArticleService {

	@Autowired
	ArticleDao articleDao;
	
	public ArticleService(){}
	
	/****************************************************************************************/
             /**********         Article Operations     **************/
    /****************************************************************************************/ 

	
	  public int saveArticle(Article article){
	      return articleDao.saveArticle(article);
      }
	  
	  public Article getArticleById(int id){
		  return articleDao.getArticleById(id);
	  }
	  
      public List<Article> getAllArticles(){
		  
		  return articleDao.getAllArticles();
	  }
      
      
    /****************************************************************************************/
         /**********         Category Operations     **************/
    /****************************************************************************************/ 
      public List<Category> getAllCategories(){
		  
		  return articleDao.getAllCategories();
	  }
      
      public List<Article> getArticlesByCategory(int categoryId){
    	  return articleDao.getArticlesByCategory(categoryId);
      }
      
      public List<Article> searchArticlesByKeyword(String keyword){
    	  return articleDao.searchArticlesByKeyword(keyword);
      }
      public Category getCategoryById(int categoryId){
  		return articleDao.getCategoryById(categoryId);
  	  }
	  
      public int createCategory(Category category){
    	  return articleDao.createCategory(category);
      }     
     	  
}
