package com.trc.domain.support.ticket;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.trc.user.User;
import com.trc.domain.support.ticket.TicketStatus;


@Entity
@Table(name="ticket")
public class Ticket implements Serializable {
  private static final long serialVersionUID = 1495544695293668738L;
  private static final String DEFALT_TITLE = "Truconnect Issue With ";
  @Id
  @Column(name="ticket_id", updatable=false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  
  @Column(name="title")
  private String title = DEFALT_TITLE;
  
  @Enumerated(EnumType.STRING)
  private TicketPriority priority;
    
  @Enumerated(EnumType.STRING)
  private TicketStatus status;
  
  @Enumerated(EnumType.STRING)
  private TicketCategory category;
  
  @Column(name="description")
  String description;
  
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "customer", insertable = true, updatable = true)
  private User customer;
  
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "creator", insertable = true, updatable = false)
  private User creator;  
  
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "assignee", insertable = true, updatable = true)
  private User assignee;
    
  @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL, mappedBy = "ticket")
  @Fetch(value = FetchMode.SUBSELECT)
  private Collection<TicketNote> notes = new ArrayList<TicketNote>();
 
  @Column(name = "created_date", updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  Date createdDate;
  
  @Column(name = "last_modified_date", updatable = true)
  @Temporal(TemporalType.TIMESTAMP)
  Date lastModifiedDate;
  
  @Transient
  private String noteMessages;
 
  
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
 
  public TicketCategory getCategory() {
	 return category;
  }

  public String getDescription() {
	return description;
  }

  public void setDescription(String description) {
	this.description = description;
  }
	
  public User getCustomer() {
    return customer;
  }

  public Collection<TicketNote> getNotes() {
	 return notes;
  }

  public TicketStatus getStatus() {
    return status;
  }

  public void setCategory(TicketCategory category) {
    this.category = category;
  }

  public void setCustomer(User customer) {
    this.customer = customer;
  }

  public TicketPriority getPriority() {
	return priority;
  }

  public void setPriority(TicketPriority priority) {
	this.priority = priority;
  }

  private void setNotes(Collection<TicketNote> notes) {
    this.notes = notes;
  }
      
  public void addNote(TicketNote note){
	  if(note == null) {
		  throw new IllegalArgumentException("Note cannot be null");
      }	  
	  //note.getTicket().getNotes().remove(note);
	  note.setTicket(this);
	  notes.add(note);
  }  
  

  public void setStatus(TicketStatus status) {
    this.status = status;
  }
 	
  public Date getCreatedDate() {
	 return createdDate;
  }

  public User getCreator() {
	return creator;
  }

  public void setCreator(User creator) {
	this.creator = creator;
  }

  public User getAssignee() {
	return assignee;
  }

  public void setAssignee(User assignee) {
	this.assignee = assignee;
  }

  public void setCreatedDate(Date createdDate) {
	 this.createdDate = createdDate;
  }
  
  public Date getLastModifiedDate() {
	return lastModifiedDate;
  }

  public void setLastModifiedDate(Date lastModifiedDate) {
	this.lastModifiedDate = lastModifiedDate;
  }
  
  public String getNoteMessages(){
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	  StringBuilder sb = new StringBuilder();
	  for(TicketNote note : Collections.synchronizedCollection(notes)){
		  sb.append(note.getNote());
		  sb.append("(");
		  if(note.getCreatedDate() != null)
			 sb.append(formatter.format(note.getCreatedDate()) + ", ");		  
		  if(note.getAuthor() != null)			 
		     sb.append("by " + note.getAuthor().getUsername());
		  sb.append(")");
		  sb.append("\n");		  
	  }	  
	  noteMessages = sb.toString();	  
	  return noteMessages;
  }
  
  public void setNoteMessages(String noteMessages){
	  this.noteMessages = noteMessages;
  }  

  @Override
  public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((category == null) ? 0 : category.hashCode());
	result = prime * result + ((customer == null) ? 0 : customer.hashCode());
	result = prime * result + priority.hashCode();
	result = prime * result + ((notes == null) ? 0 : notes.hashCode());
	result = prime * result + ((creator == null) ? 0 : creator.hashCode());
	result = prime * result + ((status == null) ? 0 : status.hashCode());
	result = prime * result + id;
	return result;
  }

@Override
public String toString() {
	return "Ticket [id=" + id + ", title=" + title + ", priority="
			+ priority + ", status=" + status + ", category=" + category
			+ ", customer=" + (customer == null ? "" : customer.getEmail()) + 
			", owner=" + (creator ==null? "" : creator.getEmail()) + 
			", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate 
			//+ ", notes=" + (notes == null? "" : (notes.size() <= 0 ? "" : notes.get(0))) 
			+ "]";	
}

 
}
