package com.trc.domain.support.notification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Proxy;
import org.springframework.context.annotation.Lazy;

@Entity
@Table(name="notification_schedule")
@Proxy(lazy=false)
public class NotificationSchedule implements Serializable{

	private static final long serialVersionUID = 1495544695293668738L;
	 
	@Id
	@Column(name="id", unique=true, nullable=false, updatable=false)
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private Integer id;
		
	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name = "message_id", nullable=false, insertable=true, updatable=true)
	//@PrimaryKeyJoinColumn
	private NotificationMessage message;
	
	@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL, mappedBy = "notificationSchedule")
    //@Fetch(value = FetchMode.SUBSELECT)
	private List<NotificationPage> targetPages = new ArrayList<NotificationPage>();
	
	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name = "date_time_id")
	private NotificationDateTime notificationDateTime;
	
	@Column(name="date_created", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	//public String getTitle() {
	//	return title;
	//}

	//public void setTitle(String title) {
	//	this.title = title;
	//}

	public NotificationMessage getMessage() {
		return message;
	}

	public void setMessage(NotificationMessage message) {
		this.message = message;
	}
	
	//public NotificationEvent getEvent() {
	//	return event;
	//}

	//public void setEvent(NotificationEvent event) {
	//	this.event = event;
	//}

	/*public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}	
		*/
	public List<NotificationPage> getTargetPages() {
		return targetPages;
	}

	public void setTargetPages(List<NotificationPage> targetPages) {
		this.targetPages = targetPages;
	}
	
	public void addTargetPage(NotificationPage targetPage){
		targetPage.setNotificationSchedule(this);
		targetPages.add(targetPage);		
	}	
	
	public NotificationDateTime getNotificationDateTime() {
		return notificationDateTime;
	}

	public void setNotificationDateTime(NotificationDateTime notificationDateTime) {
		this.notificationDateTime = notificationDateTime;
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		//result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime
				* result
				+ ((notificationDateTime == null) ? 0 : notificationDateTime
						.hashCode());
		result = prime * result
				+ ((targetPages == null) ? 0 : targetPages.hashCode());
		//result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		NotificationSchedule other = (NotificationSchedule) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (notificationDateTime == null) {
			if (other.notificationDateTime != null)
				return false;
		} else if (!notificationDateTime.equals(other.notificationDateTime))
			return false;
		if (targetPages == null) {
			if (other.targetPages != null)
				return false;
		} else if (!targetPages.equals(other.targetPages))
			return false;
		
		return true;
	}

	//@Override
	//public String toString() {
	//	return "NotificationSchedule [id=" + id 
				//+ ", message=" + message
				//+ ", targetPages=" + targetPages + ", notificationDateTime="
		//		+ notificationDateTime + "]";
	//}
	
}
