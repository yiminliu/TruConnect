package com.trc.dao;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import com.trc.coupon.contract.Contract;
import com.trc.coupon.hibernate.HibernateUtil;

@Service
public class ContractDao {

	public int insertContract(Contract contract) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			Serializable result = session.save(contract);
			transaction.commit();
			return (Integer) result;
		} catch (RuntimeException e) {
			e.printStackTrace();
			transaction.rollback();
			return -1;
		} finally {
			HibernateUtil.closeSession(session);
		}
	}

	public void deleteContract(Contract contract) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.delete(contract);
			transaction.commit();
		} catch (RuntimeException e) {
			e.printStackTrace();
			transaction.rollback();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}

	public void updateContract(Contract contract) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.update(contract);
			transaction.commit();
		} catch (RuntimeException e) {
			e.printStackTrace();
			transaction.rollback();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}

	public Contract getContract(int contractId) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			Contract contract = (Contract) session.get(Contract.class, contractId);
			return contract;
		} catch (RuntimeException e) {
			transaction.rollback();
			e.printStackTrace();
			return null;
		} finally {
			HibernateUtil.closeSession(session);
		}
	}
}
