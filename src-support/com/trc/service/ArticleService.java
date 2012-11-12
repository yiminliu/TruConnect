package com.trc.service;

import java.util.List;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.trc.dao.ArticleDao;
import com.trc.domain.support.knowledgebase.Article;
import com.trc.domain.support.knowledgebase.Category;

@Service
public class ArticleService {

	@Autowired
	ArticleDao articleDao;
	
	public ArticleService(){}
	
	public int saveArticle(Article article){
		  return articleDao.saveArticle(article);
	  }
	  
	  public Article getArticleById(int id){
		  return articleDao.getArticleById(id);
	  }
	  
      public List<Article> getAllArticles(){
		  
		  return articleDao.getAllArticles();
	  }
      
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
	  public static void main(String[] arg){
		  ArticleService as = new ArticleService();
		  as.initForTest();
		 // articleDao.getArticle(2);
	  }
	  
	  private void initForTest() {
		  	
		  	//ApplicationContext appCtx = new ClassPathXmlApplicationContext("application-context.xml");
		  ApplicationContext appCtx = new ClassPathXmlApplicationContext("TruConnect-context.xml");
		 // try{	
		   	articleDao = (ArticleDao)appCtx.getBean("articleDao");
		  //}
		  //catch(NoSuchBeanDefinitionException nbe){
			//  if(articleDao == null)
		  		//articleDao = new ArticleDao();
		  //}  
	  }	  
}
