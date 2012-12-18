package com.trc.domain.ticket;

import java.util.List;

import com.trc.util.Paginator;

public class TicketStorage extends Paginator<Ticket> {

	public TicketStorage(List<Ticket> tickets) {
		super.setRecords(tickets);
		super.setSummarySize(3);
	}

}