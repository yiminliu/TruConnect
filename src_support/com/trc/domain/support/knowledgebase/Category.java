package com.trc.domain.support.knowledgebase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name="swkbcategories")
public class Category implements java.io.Serializable{
	private static final long serialVersionUID = 1495544695293668738L;

	@Id
	@Column(name="kbcategoryid", updatable=false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	@Column(name="title")
	String title;
	@Transient
	int totalArticles;
	@Column(name="categorytype")
    int categoryType;
	@Column(name="displayorder")
	int displyOrder;
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "categories")
	private Set<Article> articles = new HashSet<Article>();
	
	public Category(){}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getTotalArticles() {
		if(articles == null || articles.isEmpty())
		   return 0;	
		else
	  	   return articles.size();
	}

	public void setTotalArticles(){
		this.totalArticles = articles.size();
	}

	public int getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(int categoryType) {
		this.categoryType = categoryType;
	}

	public int getDisplyOrder() {
		return displyOrder;
	}

	public void setDisplyOrder(int displyOrder) {
		this.displyOrder = displyOrder;
	}

	public Set<Article> getArticles() {
		return articles;
	}
	
	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}
	
	public void addArticle(Article article) {
	    article.addCategory(this);
		articles.add(article);	
	}
	/*	
	public class CategoryIdComparator<Category> implements Comparator<Category>{  
		  public int compare( Object c1, Object c2) {
              Integer id1 = ((Category)c1).getId();
              Integer id2 = ((Category)c2).getId();
              return id1.compareTo(id2);
        }
	} 
    */   
}
