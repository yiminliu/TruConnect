package com.trc.domain.support.ticket;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Tuplizer;

//@Entity
//@Table(name="ticket_status")
//@Tuplizer(imp = EnumTuplizer.class)
public enum TicketStatus {
  OPEN, CLOSED, RESOLVED, ON_HOLD, UNRESOLVED, REJECTED;
  
  @Id
  protected String name = toString();
  
}
