package com.trc.domain.support.notification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CascadeType;


@Entity
@Table(name="notification_page")
public class NotificationPage implements Serializable{
	
	private static final long serialVersionUID = 1495544695293668738L;
	
	@Id
	@Column(name="page_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer pageId;
	
	@Column(name="name")
	private String name;
	
	//@Column(name="message")
	//private String message;
		
	//@ManyToMany(fetch = FetchType.EAGER)//, cascade = CascadeType.ALL)
	//@JoinTable(name = "notification_page_message", 
    //		   joinColumns = {@JoinColumn(name = "page_id", nullable = false, updatable = false)}, 
    //		   inverseJoinColumns = {@JoinColumn(name = "message_id", nullable = false, updatable = false)})
    //private List<NotificationMessage> messages = new ArrayList<NotificationMessage>(0);			
    			
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="schedule_id", insertable = true, updatable = true)
	private NotificationSchedule notificationSchedule;

	public Integer getPageId() {
		return pageId;
	}

	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	

	//public String getMessage() {
	//	return message;
	//}

	//public void setMessage(String message) {
	//	this.message = message;
	//}
	
	//public List<NotificationMessage> getMessages() {
	//	return messages;
	//}

	//public void setMessages(List<NotificationMessage> messages) {
	//	this.messages = messages;
	//}
		
	public NotificationSchedule getNotificationSchedule() {
		return notificationSchedule;
	}
	
	public void setNotificationSchedule(NotificationSchedule notificationSchedule) {
		this.notificationSchedule = notificationSchedule;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		//result = prime * result + ((pageId == null) ? 0 : pageId.hashCode());
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
		NotificationPage other = (NotificationPage) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		//if (pageId == null) {
		//	if (other.pageId != null)
		//		return false;
		//} else if (!pageId.equals(other.pageId))
		//	return false;
		return true;
	}

    
}
