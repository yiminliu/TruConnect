package com.trc.domain.support.notification;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CascadeType;

@Entity
@Table(name="notification_message")
public class NotificationMessage implements Serializable{

	private static final long serialVersionUID = 1495544695293668738L;
	 
	@Id
	@Column(name="message_id", updatable=false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	//@Enumerated(EnumType.STRING)
	//private String category;
	@Column(name="title")
	private String title;
	
	@Column(name = "message")
	private String message;
		
	@OneToOne(mappedBy="message")
	private NotificationSchedule notification;
	
	
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	//public Set<NotificationPage> getNotificationPages() {
	//	return notificationPages;
	//}

	//public void setNotificationPages(Set<NotificationPage> notificationPages) {
	//	this.notificationPages = notificationPages;
	//}

	public NotificationSchedule getNotification() {
		return notification;
	}

	public void setNotification(NotificationSchedule notification) {
		this.notification = notification;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		//result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		NotificationMessage other = (NotificationMessage) obj;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NotificationMessage [id=" + id + ", title=" + title
				+ ", message=" + message + "]";
	}	
}
