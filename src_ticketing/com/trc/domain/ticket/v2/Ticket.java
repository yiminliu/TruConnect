package com.trc.domain.ticket.v2;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.trc.domain.ticket.TicketCategory;
import com.trc.domain.ticket.TicketNote;
import com.trc.domain.ticket.TicketPriority;
import com.trc.domain.ticket.TicketStatus;
import com.trc.domain.ticket.TicketType;

@Entity
@Table(name = "TICKET")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE")
abstract class Ticket implements Serializable {
	private static final long serialVersionUID = -1651076728127823433L;
	private int id;
	private TicketType type;
	private TicketPriority priority;
	private TicketCategory category;
	private TicketStatus status;
	private DateTime createdDate;
	private DateTime lastModifiedDate;
	private String title;
	private String description;
	private List<TicketNote> notes;

	@Id
	@Column(name = "ticket_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	public TicketType getType() {
		return type;
	}

	public void setType(TicketType type) {
		this.type = type;
	}

	@Column(name = "priority")
	@Enumerated(EnumType.STRING)
	public TicketPriority getPriority() {
		return priority;
	}

	public void setPriority(TicketPriority priority) {
		this.priority = priority;
	}

	@Column(name = "category")
	@Enumerated(EnumType.STRING)
	public TicketCategory getCategory() {
		return category;
	}

	public void setCategory(TicketCategory category) {
		this.category = category;
	}

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	public TicketStatus getStatus() {
		return status;
	}

	public void setStatus(TicketStatus status) {
		this.status = status;
	}

	@Column(name = "created_date")
	@Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
	public DateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(DateTime createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "last_modified_date")
	@Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
	public DateTime getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(DateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	@Column(name = "title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "ticket")
	@LazyCollection(LazyCollectionOption.FALSE)
	public List<TicketNote> getNotes() {
		return notes;
	}

	public void setNotes(List<TicketNote> notes) {
		this.notes = notes;
	}

	@Transient
	public String getNoteMessages() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		StringBuilder sb = new StringBuilder();
		for (TicketNote note : Collections.synchronizedCollection(notes)) {
			if (note.getNote() != null && note.getNote().length() > 0) {
				sb.append(note.getNote());
				sb.append("(");
				if (note.getCreatedDate() != null)
					sb.append(formatter.format(note.getCreatedDate()) + ", ");
				if (note.getAuthor() != null)
					sb.append("by " + note.getAuthor().getUsername());
				sb.append(")");
				sb.append("\n");
			}
		}
		return sb.toString();
	}
}
