package com.trc.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.trc.domain.support.knowledgebase.Article;
import com.trc.domain.support.knowledgebase.ArticleData;
import com.trc.domain.support.knowledgebase.Category;

@Repository("articleDao")
@Transactional
public class ArticleDao extends HibernateDaoSupport{

	  public ArticleDao(){};
	  
	  @Autowired
	  public void init(HibernateTemplate hibernateTemplate) {
		 setHibernateTemplate(hibernateTemplate);
	  }
	  
	  /****************************************************************************************/
                  /**********         Article Operations     **************/
      /****************************************************************************************/ 

	  public int saveArticle(Article article){
		  if(article == null)
			 throw new IllegalArgumentException("The input Article is null."); 
		  else
		     return (Integer)getHibernateTemplate().save(article);
	  }
	  
	  @Transactional(readOnly=true)
	  public Article getArticleById(int id){		  
		  Object obj = getHibernateTemplate().get(Article.class, id);
		  if(obj != null)
		     return (Article)obj;
		  else 
			 return null;
	  }
	  
	  @Transactional(readOnly=true)
	  public List<Article> getArticlesByCategory(int categoryId){
		  String queryString = "select a from Article a, ArticleCategoryMap acm where acm.categoryId=? AND a.id = acm.articleId";
		  Object obj = getHibernateTemplate().find(queryString, categoryId);
		  if(obj != null)
		     return (List<Article>)obj;
		  else
			 return null; 
	  }
	  
	  @Transactional(readOnly=true)
	  public List<Article> getAllArticles(){		  
		  return (List<Article>)getHibernateTemplate().find("from Article");
	  }
	  
	  
	  @Transactional(readOnly=true)
	  public List<Article> searchArticleByTitle(String title) {
		 return  getHibernateTemplate().find("from Article a where a.title = ?", title);
	  }
	 
	  
	  @Transactional(readOnly=true)
	  public List<Article> searchArticlesByKeyword(String keyword){
		 List<Article> articleList = new ArrayList<Article>();
		 articleList = getHibernateTemplate().find("from Article a where a.subject like ? or a.articleData.contentsText like ?", wildCardKeyword(keyword), wildCardKeyword(keyword));
	     return articleList;
	  }
	  
	  public void updateArticle(Article article){
		  getHibernateTemplate().saveOrUpdate(article);
	  }
	  
	  public void deleteArticle(int id){
		  Article article = getHibernateTemplate().get(Article.class, id);
		  getHibernateTemplate().delete(article);
	  }
	  
	  public void deleteArticle(Article article){
		  getHibernateTemplate().delete(article);
	  }
	  
	  /****************************************************************************************/
               /**********         Category Operations     **************/
      /****************************************************************************************/  
	  	  
	  @Transactional(readOnly=true)
	  public List<Category> getAllCategories(){		  
		  return (List<Category>)getHibernateTemplate().find("from Category");
	  }	 

	  @Transactional(readOnly=true)
	  public Category getCategoryById(int categoryId){
		  return (Category)getHibernateTemplate().find("from Category").get(0);
	  }
	  
	  public int createCategory(Category category){
		  return (Integer)getHibernateTemplate().save(category);
	  }
	  	  
	  /****************************************************************************************/
	           /****************   ArticleData Operations   *****************/
	  /****************************************************************************************/  
	                   
	  public ArticleData getArticleDataById(int id){
	  	  return (ArticleData)getHibernateTemplate().get(ArticleData.class, id);
	  }            
	  
	  /****************************************************************************************/
             	  /***************  Utility Methods ******************/
	  /****************************************************************************************/  
		 
	  private String wildCardKeyword(String keyword){
		  if(keyword == null || keyword.equals(""))
			 return null;
		  else
			 return "%" + keyword + "%";
		 }
}

