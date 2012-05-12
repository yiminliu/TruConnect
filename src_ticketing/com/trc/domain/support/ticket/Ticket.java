package com.trc.domain.support.ticket;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

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
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
//import org.hibernate.annotations.CascadeType;

import com.trc.user.User;

@Entity
@Table(name="ticket")
public class Ticket implements Serializable {
  private static final long serialVersionUID = 1495544695293668738L;
  
  @Id
  @Column(name="ticket_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int ticketId;
  
  @Column(name="title")
  private String title;
  
  @Column(name="importance")
  private int importance;
  
  @Enumerated(EnumType.STRING)
  private TicketCategory category;
  
  @Enumerated(EnumType.STRING)
  private TicketStatus status;
  
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "customer_id", nullable = false, insertable = false, updatable = false)
  private User customer;
  
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "owner_id", nullable = false, insertable = true, updatable = true)
  private User owner;   

  @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL, mappedBy = "ticket")
  private List<TicketNote> notes;

 
  public int getTicketId() {
    return ticketId;
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

  public User getCustomer() {
    return customer;
  }
	  
  public int getImportance() {
    return importance;
  }

  public List<TicketNote> getNotes() {
    return notes;
  }

  public User getOwner() {
    return owner;
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

  public void setTicketId(int ticketId) {
    this.ticketId = ticketId;
  }

  public void setImportance(int importance) {
    this.importance = importance;
  }

  public void setNotes(List<TicketNote> notes) {
    this.notes = notes;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

  public void setStatus(TicketStatus status) {
    this.status = status;
  }
  
  public void addTicketNote(TicketNote note){
	  notes.add(note);	  
  }

  @Override
  public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((category == null) ? 0 : category.hashCode());
	result = prime * result + ((customer == null) ? 0 : customer.hashCode());
	result = prime * result + importance;
	result = prime * result + ((notes == null) ? 0 : notes.hashCode());
	result = prime * result + ((owner == null) ? 0 : owner.hashCode());
	result = prime * result + ((status == null) ? 0 : status.hashCode());
	result = prime * result + ticketId;
	return result;
  }

  @Override
  public String toString() {
	 return "Ticket [ticketId=" + ticketId + ", importance=" + importance
			+ ", category=" + category + ", status=" + status + 
			", customer=" + (customer == null ? "" : customer.getEmail()) + 
			", owner=" + (owner ==null ? "" : owner.getEmail()) + 
			/*", notes=" + notes +*/ "]";
  }
}
