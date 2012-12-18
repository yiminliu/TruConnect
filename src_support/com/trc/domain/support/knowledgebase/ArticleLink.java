package com.trc.domain.support.knowledgebase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="swkbarticlelinks")
public class ArticleLink {

	@Id
	Integer id;
	
	@Column
	Integer articleId;
	
	@Column
	int linkType;
	
	@Column
	int linkTypeId;
	
	public ArticleLink(){}
	
}
