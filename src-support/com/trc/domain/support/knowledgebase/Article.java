package com.trc.domain.support.knowledgebase;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="swkbarticles")
public class Article implements java.io.Serializable{
	private static final long serialVersionUID = 1495544695293668738L;
	@Id
	@Column(name="kbarticleid", updatable=false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	
	@Column(name="subject")
	String subject;
	@Column
	private int creatorid;		
	@Column
	private int views;
	@Column
	private int hasattachments;
	@Column
	private int dateline;
	@Column
	private String articlestatus;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(name = "article_category", 
			   joinColumns={@JoinColumn(name="articleid")}, 
			   inverseJoinColumns={@JoinColumn(name="categoryid")})
	private List<Category> categories = new ArrayList<Category>();
		
	//@OneToOne(mappedBy="article")
	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name = "kbarticleid", nullable=false, insertable=true, updatable=true)
	private ArticleData articleData;
		
	public Article(){}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getCreatorid() {
		return creatorid;
	}

	public void setCreatorid(int creatorid) {
		this.creatorid = creatorid;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public int getHasattachments() {
		return hasattachments;
	}

	public void setHasattachments(int hasattachments) {
		this.hasattachments = hasattachments;
	}

	public int getDateline() {
		return dateline;
	}

	public void setDateline(int dateline) {
		this.dateline = dateline;
	}

	public String getArticlestatus() {
		return articlestatus;
	}

	public void setArticlestatus(String articlestatus) {
		this.articlestatus = articlestatus;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	
	public void addCategory(Category category) {
		categories.add(category);
	}
	

	public ArticleData getArticleData() {
		return articleData;
	}

	public void setArticleData(ArticleData articleData) {
		this.articleData = articleData;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((categories == null) ? 0 : categories.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Article other = (Article) obj;
		if (categories == null) {
			if (other.categories != null)
				return false;
		} else if (!categories.equals(other.categories))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		return true;
	}	
	
}
