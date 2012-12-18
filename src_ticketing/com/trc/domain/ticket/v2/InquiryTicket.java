package com.trc.domain.ticket.v2;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TICKET")
@DiscriminatorColumn(name = "TYPE")
@DiscriminatorValue(value = "INQUIRY")
public class InquiryTicket extends Ticket {
	private static final long serialVersionUID = -3788997727517144764L;
	private String contactEmail;
	private String contactPhone;
}
