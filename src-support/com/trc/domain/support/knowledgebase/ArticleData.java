package com.trc.domain.support.knowledgebase;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="swkbarticledata")
public class ArticleData {
	
	@Id
	@Column(name="kbarticledataid", updatable=false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	
	
	//@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	//@JoinColumn(name = "kbarticleid", nullable=false, insertable=true, updatable=true)
	@OneToOne(mappedBy="articleData")
	private Article article;
	
	@Column(name="contents")
	String contents;
	
	@Column(name="contentsText")
	String contentsText;

	
	public ArticleData(){}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getContentsText() {
		return contentsText;
	}

	public void setContentsText(String contentsText) {
		this.contentsText = contentsText;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}	
	
}
