package com.trc.domain.ticket.v2;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TICKET")
@DiscriminatorColumn(name = "TYPE")
@DiscriminatorValue(value = "ADMIN")
public class AdminTicket extends AgentTicket {
	private static final long serialVersionUID = 3545858776250871180L;

}
