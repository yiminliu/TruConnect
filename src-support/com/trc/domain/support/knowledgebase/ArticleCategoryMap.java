package com.trc.domain.support.knowledgebase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="article_category")
public class ArticleCategoryMap implements java.io.Serializable{
	private static final long serialVersionUID = 1495544695293668738L;
	
	@Id
	@Column(name="articleid")
	private Integer articleId;
	
	@Id
	@Column(name="categoryid")
	private Integer categoryId;

	public Integer getArticalId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	
	
}
