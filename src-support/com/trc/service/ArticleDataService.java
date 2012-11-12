package com.trc.service;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.trc.dao.ArticleDao;
import com.trc.domain.support.knowledgebase.Article;
import com.trc.domain.support.knowledgebase.ArticleData;

@Service
public class ArticleDataService {

	@Autowired
	static ArticleDao articleDao;
	
	public int saveArticle(Article article){
		  return articleDao.saveArticle(article);
	  }
	  
	  public ArticleData getArticleDataById(int id){
		  
		  return articleDao.getArticleDataById(id);
	  }
	  
	  //public static void main(String[] arg){
		//  ArticleDataService as = new ArticleDataService();
		//  as.initForTest();
		//  articleDao.getArticle(2);
	  //}
	  
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
