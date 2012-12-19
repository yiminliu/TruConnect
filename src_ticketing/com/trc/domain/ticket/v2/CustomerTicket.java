package com.trc.domain.ticket.v2;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.trc.user.User;

@Entity
@Table(name = "TICKET")
//@DiscriminatorColumn(name = "TYPE")
@DiscriminatorValue(value = "CUSTOMER")
public class CustomerTicket extends Ticket {
	private static final long serialVersionUID = -8249415459905630871L;
	private User customer;
	private User asignee;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "customer", insertable = true, updatable = false, nullable = true)
	public User getCustomer() {
		return customer;
	}
	public void setCustomer(User customer) {
		this.customer = customer;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "assignee", insertable = true, updatable = false, nullable = true)
	public User getAsignee() {
		return asignee;
	}
	public void setAsignee(User asignee) {
		this.asignee = asignee;
	}
	
	
}
