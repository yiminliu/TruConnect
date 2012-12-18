package com.trc.domain.ticket.v2;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.trc.user.User;

@Entity
@Table(name = "TICKET")
@DiscriminatorColumn(name = "TYPE")
@DiscriminatorValue(value = "AGENT")
public class AgentTicket extends CustomerTicket {
	private static final long serialVersionUID = 6566960793806943444L;
	private User creator;

}