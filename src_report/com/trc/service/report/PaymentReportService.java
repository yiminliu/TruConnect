package com.trc.service.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.dao.PaymentTransactionDao;
import com.trc.domain.support.report.payment.PaymentTransaction;
import com.trc.domain.support.report.payment.PaymentReport;
import com.trc.exception.management.DeviceManagementException;
import com.trc.manager.AccountManager;
import com.trc.manager.DeviceManager;
import com.trc.manager.UserManager;
import com.trc.user.User;
import com.trc.user.account.AccountDetail;
import com.tscp.mvne.Account;
import com.tscp.mvne.Device;

@Service
public class PaymentReportService{

	@Autowired
	private PaymentTransactionDao paymentTransactionDao;
	@Autowired
	private UserManager userManager;
	@Autowired 
	private AccountManager accountManager;
	@Autowired
	private DeviceManager deviceManager;
	
	public Collection<PaymentTransaction> getAllPaymentTransactions() {		
		return paymentTransactionDao.getAllPaymentTransactions();
	}
	
	public Collection<Integer> getAllAccountNumbers() {		
		Set<Integer> accountNoSet = new HashSet<Integer>();
		Collection<Integer> accountNumbers = paymentTransactionDao.getAllAccountNumbers();
		accountNoSet.addAll(accountNumbers);
		return accountNoSet;		
	}
	
	public Collection<PaymentTransaction> getFailedPaymentTransactions() {		
		return paymentTransactionDao.getFailedPaymentTransactions();
	}
	
	public Collection<PaymentTransaction> getPaymentTransactionByCustId(int custId) {
		return paymentTransactionDao.getFailedPaymentTransactionByCustId(custId);
	}

	public PaymentReport getPaymentReportByTransId(int transId) {
		PaymentTransaction paymentTransaction = paymentTransactionDao.getFailedPaymentTransactionByAccountByTransId(transId);
		User user = new User();
		Account account = null;
		Device device = null;
		PaymentReport paymentReport = new PaymentReport();
		try{
		   user = userManager.getUserById(paymentTransaction.getCustId());	
	       userManager.setSessionUser(user);
		   account = accountManager.getAccount(paymentTransaction.getAccountNo());
		   device = getDevice(user, paymentTransaction.getAccountNo());
		}
		catch( Exception e){
			   e.printStackTrace();
		}			
   		paymentReport.setPaymentTransaction(paymentTransaction);
		paymentReport.setUser(user);
		paymentReport.setAccount(account);
		paymentReport.setDevice(device);
		return paymentReport;
	}

	public List<PaymentReport> getFailedPaymentReportByUserName(String userName) {
		User user = userManager.getUserByUsername(userName);
		Collection<PaymentTransaction> paymentTransactions = paymentTransactionDao.getFailedPaymentTransactionByCustId(user.getUserId());
		Account account = null;
		Device device = null;
		PaymentReport paymentReport = new PaymentReport();
		List<PaymentReport> paymentReportList = new ArrayList<PaymentReport>();
		for(PaymentTransaction paymentTransaction : paymentTransactions){
			paymentReport = new PaymentReport();
			try{
			   account = accountManager.getAccount(paymentTransaction.getAccountNo());
			   device = getDevice(user, paymentTransaction.getAccountNo());
			}
			catch( Exception e){
				   e.printStackTrace();
			}
			//user = userManager.getUserById(custId);
   		    paymentReport.setPaymentTransaction(paymentTransaction);
			paymentReport.setUser(user);
			paymentReport.setAccount(account);
			paymentReport.setDevice(device);
			paymentReportList.add(paymentReport);
		}
		return processPaymentReportList(paymentReportList);		
	}

	public List<PaymentReport> getFailedPaymentReportByAccountNo(int accountNo) {
		Collection<PaymentTransaction> paymentTransactions = paymentTransactionDao.getFailedPaymentTransactionByAccountNo(accountNo);
		Account account = null;
		Device device = null;
		User user = null;
		//AccountDetail accountDetail = null;
		PaymentReport paymentReport = new PaymentReport();
		List<PaymentReport> paymentReportList = new ArrayList<PaymentReport>();
		for(PaymentTransaction paymentTransaction : paymentTransactions){
			paymentReport = new PaymentReport();
			try{
			   account = accountManager.getAccount(accountNo);
			   user = userManager.getUserById(paymentTransaction.getCustId());
			   device = getDevice(user, paymentTransaction.getAccountNo());
			}
			catch( Exception e){
				   e.printStackTrace();
			}
			//user = userManager.getUserById(custId);
   		    paymentReport.setPaymentTransaction(paymentTransaction);
			paymentReport.setUser(user);
			paymentReport.setAccount(account);
			paymentReport.setDevice(device);
			paymentReportList.add(paymentReport);
		}
		return processPaymentReportList(paymentReportList);		
	}
	
	public List<PaymentReport> getPaymentByAccountNo(int accountNo) {
		Collection<PaymentTransaction> paymentTransactions = paymentTransactionDao.getFailedPaymentTransactionByAccountNo(accountNo);
		PaymentReport paymentReport = null;
		User user = new User();	
		int custId = 0;
		List<PaymentReport> paymentReportList = new ArrayList<PaymentReport>();
		for(PaymentTransaction paymentTransaction : paymentTransactions){
			paymentReport = new PaymentReport();
			try{
			   custId = paymentTransaction.getCustId();			   
			}
			catch( Exception e){
				   e.printStackTrace();
			}
			user = userManager.getUserById(custId);
   		    paymentReport.setPaymentTransaction(paymentTransaction);
			paymentReport.setUser(user);
			paymentReportList.add(paymentReport);
		}		
		return processPaymentReportList(paymentReportList);
	}

	public Collection<PaymentTransaction> getFailedPaymentTransactionByDate(Date beginDate, Date endDate) {
		return paymentTransactionDao.getFailedPaymentTransactionByDate(beginDate, endDate);
	}
	
	public Collection<PaymentTransaction> getFailedPaymentTransactionByDate(Date beginDate, Date endDate, int numOfRecord) {
		return paymentTransactionDao.getFailedPaymentTransactionByDate(beginDate, endDate, numOfRecord);
	}

	public List<PaymentReport> getFailedPaymentReport(){
		
		Collection<PaymentTransaction> paymentTransactions = paymentTransactionDao.getFailedPaymentTransactions();
		PaymentReport paymentReport = null;
		User user = null;
		Account account = null;
		Device device = null;
		List<PaymentReport> paymentReportList = new ArrayList<PaymentReport>();
		for(PaymentTransaction paymentTransaction : paymentTransactions){
			paymentReport = new PaymentReport();
			try{
			   //custId = paymentTransaction.getCustId();	
			   user = userManager.getUserById(paymentTransaction.getCustId());
			   account = accountManager.getAccount(paymentTransaction.getAccountNo());
			   device = getDevice(user, paymentTransaction.getAccountNo());
			}
			catch( Exception e){
				   e.printStackTrace();
			}
			paymentReport.setPaymentTransaction(paymentTransaction);
			paymentReport.setUser(user);
			paymentReport.setAccount(account);
			paymentReport.setDevice(device);
			paymentReportList.add(paymentReport);
		}		
		//setReportCount(paymentReportList);
		return processPaymentReportList(paymentReportList);
	}
	
    public List<PaymentReport> getFailedPaymentReportByDate(Date beginDate, Date endDate) {
		
    	Collection<PaymentTransaction> paymentTransactions = paymentTransactionDao.getFailedPaymentTransactionByDate(beginDate, endDate);

		PaymentReport paymentReport = null;
		User user = null;
		Account account = null;
		Device device = null;
		List<PaymentReport> paymentReportList = new ArrayList<PaymentReport>();
		for(PaymentTransaction paymentTransaction : paymentTransactions){
			paymentReport = new PaymentReport();
			try{
    		   user = userManager.getUserById(paymentTransaction.getCustId());
			   account = accountManager.getAccount(paymentTransaction.getAccountNo());
			}
			catch( Exception e){
				   e.printStackTrace();
			}
		    paymentReport.setPaymentTransaction(paymentTransaction);
			paymentReport.setUser(user);
			paymentReportList.add(paymentReport);
		}		
		return processPaymentReportList(paymentReportList);
 	}
    
public List<PaymentReport> getFailedPaymentReportByDate(Date beginDate, Date endDate, int numOfRecords) {
		
    	Collection<PaymentTransaction> paymentTransactions = paymentTransactionDao.getFailedPaymentTransactionByDate(beginDate, endDate, numOfRecords);

		PaymentReport paymentReport = null;
		User user = null;
		Account account = null;
		Device device = null;
		List<PaymentReport> paymentReportList = new ArrayList<PaymentReport>();
		for(PaymentTransaction paymentTransaction : paymentTransactions){
			paymentReport = new PaymentReport();
			try{
    		   user = userManager.getUserById(paymentTransaction.getCustId());
			   account = accountManager.getAccount(paymentTransaction.getAccountNo());
			}
			catch( Exception e){
				   e.printStackTrace();
			}
		    paymentReport.setPaymentTransaction(paymentTransaction);
			paymentReport.setUser(user);
			paymentReportList.add(paymentReport);
		}		
		return processPaymentReportList(paymentReportList);
 	}
    
    private Device getDevice(User user, int accountNo){
    	List<Device> deviceList = null;
    	try{
    		deviceList = deviceManager.getDeviceInfoList(user);
    	}
    	catch(DeviceManagementException de){
    	    de.printStackTrace();
    	}    
    	
    	for(Device device : deviceList){
    		if(device.getAccountNo() == accountNo)
    		   return device;	
    	}
    	return null;
    }
    
    private void setReportCount(List<PaymentReport> paymentReportList) {
    	int count = 0;
    	int j= 0;
    	PaymentReport paymentReportI = null;
    	PaymentReport paymentReportJ = null;
    	List<PaymentReport> newList = new ArrayList<PaymentReport>();
    	for(int i=0; i<paymentReportList.size()-1; i++){
    		paymentReportI = paymentReportList.get(i);
    		for(j=i+1; j<paymentReportList.size(); j++){
    			paymentReportJ = paymentReportList.get(j);
    		    if(paymentReportI.getAccount() != null && paymentReportJ.getAccount() != null && paymentReportI.getAccount().getAccountno() > 0){
    		    	System.out.println("paymentReportI.getAccount().getAccountno()="+paymentReportI.getAccount().getAccountno());
    		    	 
    		    	System.out.println("paymentReportJ.getAccount().getAccountno()="+paymentReportJ.getAccount().getAccountno());
    		    	if(paymentReportI.getAccount().getAccountno() == paymentReportJ.getAccount().getAccountno())
    		           count++;
    		    }
    		}    
    	    paymentReportI.setFailedPaymentCount(count);
    	    newList.add(paymentReportI);
    	    if(j == paymentReportList.size()-1)
    	    count = 0;   
      	} 
    	for(PaymentReport paymentReport : paymentReportList){
    		 if(paymentReport.getAccount() != null) 
	    	System.out.println("count: " + paymentReport.getAccount().getAccountno() +": "+ count);
    	}
    }
  
    private List<PaymentReport> processPaymentReportList(List<PaymentReport> paymentReportList){
    	User user = null;
    	for(int i=0; i<paymentReportList.size(); i++){
    	     user = paymentReportList.get(i).getUser();
    	     //if(user == null || user.getUsername() == null || user.getUsername().length() <1 || user.getUsername().startsWith("reserve_"))
    	     if(user == null || user.getUsername() == null || user.getUsername().length() <1)
    	        paymentReportList.remove(i);	
    	}
    	return paymentReportList;
    }
}
