package com.trc.domain.support.ticket;

public enum TicketStatus {
	OPEN, CLOSED, RESOLVED, ON_HOLD, UNRESOLVED, REJECTED, REOPEN;
	String key;
	TicketStatus(){}
	
	TicketStatus(String key){
		this.key = key;
	}
	
}