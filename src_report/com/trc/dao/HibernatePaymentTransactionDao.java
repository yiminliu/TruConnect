/**
 * 
 */
package com.trc.dao;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.trc.domain.support.report.payment.PaymentTransaction;

@Repository
public class HibernatePaymentTransactionDao extends HibernateDaoSupport implements PaymentTransactionDao{

	private int startRow = 0;
	
	@Autowired 
	@Qualifier("hibernateTemplateTSCPVMNE")
	public void init(HibernateTemplate hibernateTemplateTSCPMVNE) {
		 setHibernateTemplate(hibernateTemplateTSCPMVNE);
	}
		
	
	@Override
	public Collection<PaymentTransaction> getAllPaymentTransactions() {
		return getHibernateTemplate().find("from PaymentTransaction pt order by pt.paymentTransDate desc");
	}

	@Override
	public Collection<Integer> getAllAccountNumbers() {
	  return getHibernateTemplate().find("select pt.accountNo from PaymentTransaction pt where pt.accountNo is not null order by pt.paymentTransDate desc");
	}


	@Override
	@Transactional(readOnly=true)
	public Collection<PaymentTransaction> getFailedPaymentTransactions() {
		return getHibernateTemplate().find("from PaymentTransaction pt where pt.paymentUnitMessage like ? order by pt.paymentTransDate desc", "Unsuccessful%");
	}
	
	@Override
	@Transactional(readOnly=true)
	public Collection<PaymentTransaction> getFailedPaymentTransactionByCustId(int custId) {
		return getHibernateTemplate().find("from PaymentTransaction pt where pt.custId = ? and pt.paymentUnitMessage like ? order by pt.paymentTransDate, pt.accountNo desc", custId, "Unsuccessful%");		
	}
	
	@Override
	@Transactional(readOnly=true)
	public PaymentTransaction getFailedPaymentTransactionByAccountByTransId(int transId) {
		return (PaymentTransaction)getHibernateTemplate().get(PaymentTransaction.class, transId);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Collection<PaymentTransaction> getFailedPaymentTransactionByAccountNo(int accountNo) {
		return getHibernateTemplate().find("from PaymentTransaction pt where pt.accountNo = ? and pt.paymentUnitMessage like ? order by pt.accountNo, pt.paymentTransDate desc", accountNo, "Unsuccessful%");		
	}

	@Override
	@Transactional(readOnly=true)
	public Collection<PaymentTransaction> getFailedPaymentTransactionByDate(Date startDate, Date endDate) {		    
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
	    java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
    	return getHibernateTemplate().find("from PaymentTransaction pt where pt.paymentUnitMessage like ? and pt.paymentTransDate between ? and ? order by pt.paymentTransDate, pt.accountNo desc", "Unsuccessful%", sqlStartDate, sqlEndDate);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Collection<PaymentTransaction> getFailedPaymentTransactionByDate(Date startDate, Date endDate, int numOfRecords) {	
		int maxResults = startRow + numOfRecords;
		
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
	    java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
	    
	    //String queryString = "from PaymentTransaction pt where pt.paymentUnitMessage like 'Unsuccessful%' and pt.paymentTransDate between " + sqlStartDate +" and "+  sqlEndDate + " order by pt.paymentTransDate, pt.accountNo desc";
	   
	    
	    //getHibernateTemplate().getSessionFactory().getCurrentSession().beginTransaction();
	    //Criteria criteria=getHibernateTemplate().getSessionFactory().getCurrentSession().createCriteria(PaymentTransaction.class);
	    //criteria.createCriteria(queryString);
	    //criteria.addOrder(Order.desc("paymentTransDate"));
	    //criteria.setFirstResult(startRow);
	    //criteria.setMaxResults(maxResults);
	    
	   
	    //Collection pageResults=criteria.list();
	    //getHibernateTemplate().getSessionFactory().getCurrentSession().beginTransaction().commit();
	    getHibernateTemplate().setMaxResults(maxResults);
	    startRow = maxResults + 1;
	    return getHibernateTemplate().find("from PaymentTransaction pt where pt.paymentUnitMessage like ? and pt.paymentTransDate between ? and ? order by pt.paymentTransDate, pt.accountNo desc", "Unsuccessful%", sqlStartDate, sqlEndDate);
	}
    
}
