package com.trc.domain.ticket;

import java.util.List;

import com.trc.util.Paginator;

public class TicketStorage extends Paginator{
	
List<Ticket> tickets;
	
    private String displayData = "ALL";
    
	public TicketStorage(){}
	
	public TicketStorage(List<Ticket> tickets) {
		 super.setRecords(tickets);
		 super.setSummarySize(3);
		 this.tickets = tickets;
   }

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public String getDisplayData() {
		return displayData;
	}

	public void setDisplayData(String displayData) {
		this.displayData = displayData;
	}		
}
