package com.trc.domain.support.ticket;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.trc.user.User;

@Entity
@Table(name="ticket_note")
public class TicketNote implements Serializable {
  private static final long serialVersionUID = 2820727502811757522L;
  
  @Id
  @Column(name="ticket_note_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int ticketNoteId;  
  
  @Column(name="note")
  private String note;
  
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name="author_id", nullable = false, insertable = false, updatable = false)
  private User author;
  
  @ManyToOne(fetch= FetchType.EAGER)
  @JoinColumn(name="ticket_id", nullable = false, insertable = false, updatable = false)
  private Ticket ticket;
  
  public int getTicketNoteId() {
    return ticketNoteId;
  }

  public void setTicketNoteId(int ticketNoteId) {
    this.ticketNoteId = ticketNoteId;
  }
  
  public User getAuthor() {
    return author;
  }

  
  public void setAuthor(User author) {
    this.author = author;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  @Override
  public int hashCode() {
  	final int prime = 31;
  	int result = 1;
  	result = prime * result + ((author == null) ? 0 : author.hashCode());
  	result = prime * result + ((note == null) ? 0 : note.hashCode());
  	result = prime * result + ticketNoteId;
  	return result;
  }

  @Override
  public String toString() {
  	return "TicketNote [ticketNoteId=" + ticketNoteId + ", note=" + note + "]";
  }
}
