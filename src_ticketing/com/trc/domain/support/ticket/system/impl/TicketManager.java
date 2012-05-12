package com.trc.domain.support.ticket.system.impl;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.trc.domain.support.ticket.Ticket;
import com.trc.domain.support.ticket.system.TicketManagerModel;

@Service
public class TicketManager implements TicketManagerModel {

  @Override
  public void closeTicket(Ticket ticket) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void createTicket(Ticket ticket) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Collection<Ticket> getTicketsByCustomer(int custId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Collection<Ticket> getTicketsByOwner(int ownerId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void rejectTicket(Ticket ticekt) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void resolveTicket(Ticket ticket) {
    // TODO Auto-generated method stub
    
  }



}
