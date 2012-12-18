package com.trc.domain.ticket.v2;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.trc.user.User;

@Entity
@Table(name = "TICKET")
@DiscriminatorColumn(name = "TYPE")
@DiscriminatorValue(value = "CUSTOMER")
public class CustomerTicket extends Ticket {
	private static final long serialVersionUID = -8249415459905630871L;
	private User customer;
	private User asignee;
}
