package com.trc.domain.ticket;

public enum TicketStatus {
	OPEN, IN_PROCESS, IN_PROCESS_LOCKED, CLOSED, RESOLVED, ON_HOLD, UNRESOLVED, REJECTED, REOPEN;
	String key;
	TicketStatus(){}
	
	TicketStatus(String key){
		this.key = key;
	}
	
}