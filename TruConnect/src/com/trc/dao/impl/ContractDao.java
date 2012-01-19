package com.trc.dao.impl;

import org.springframework.stereotype.Repository;

import com.trc.coupon.contract.Contract;

@Repository
public class ContractDao extends AbstractHibernateDao<Contract> {

  public ContractDao() {
    setClazz(Contract.class);
  }

}
