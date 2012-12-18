package com.trc.domain.ticket;

public enum TicketCategory {
	DEVICE_ACTIVATION ("Device activation"),
	DEVICE_INACTIVE ("Device not working"),
	ACCOUNT_SUSPENSION ("Account suspension"),
	ACCOUNT_SETTINGS ("Account settings"),
	BALANCE_REFUND ("Refund / Dispute Usage"),
	USAGE ("Usage"),
	COUPON ("Coupons"),
	OTHER ("Other");
	
	private String description;
	
	private TicketCategory(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
