package com.trc.service;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import javax.annotation.Resource;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.WebServiceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trc.coupon.Coupon;
import com.trc.coupon.CouponDetail;
import com.trc.coupon.UserCoupon;
import com.trc.dao.CouponDao;
import com.trc.dao.CouponDetailDao;
import com.trc.dao.UserCouponDao;
import com.trc.exception.service.CouponServiceException;
import com.trc.service.gateway.TruConnectGateway;
import com.trc.user.User;
import com.trc.util.Formatter;
import com.trc.util.logger.DevLogger;
import com.tscp.mvne.Account;
import com.tscp.mvne.KenanContract;
import com.tscp.mvne.ServiceInstance;
import com.tscp.mvne.TruConnect;

@Service
public class CouponService {
	private TruConnect truConnect;
	private CouponDao couponDao;
	private CouponDetailDao couponDetailDao;
	private UserCouponDao userCouponDao;
	@Resource
	private DevLogger devLogger;

	/* *****************************************************************
	 * Initialization
	 * *****************************************************************
	 */

	@Autowired
	public void init(TruConnectGateway truConnectGateway, CouponDao couponDao, CouponDetailDao couponDetailDao,
			UserCouponDao userCouponDao) {
		this.truConnect = truConnectGateway.getPort();
		this.couponDao = couponDao;
		this.couponDetailDao = couponDetailDao;
		this.userCouponDao = userCouponDao;
	}

	/* *****************************************************************
	 * Coupon DAO layer interaction
	 * *****************************************************************
	 */

	@Transactional
	public int insertCoupon(Coupon coupon) {
		return couponDao.insertCoupon(coupon);
	}

	@Transactional
	public void deleteCoupon(Coupon coupon) {
		couponDao.deleteCoupon(coupon);
	}

	@Transactional
	public void updateCoupon(Coupon coupon) {
		couponDao.updateCoupon(coupon);
	}

	@Transactional
	public Coupon getCoupon(int couponId) {
		return couponDao.getCoupon(couponId);
	}

	@Transactional
	public Coupon getCouponByCode(String couponCode) {
		return couponDao.getCouponByCode(couponCode);
	}

	/* *****************************************************************
	 * CouponDetail DAO layer interaction
	 * *****************************************************************
	 */

	@Transactional
	public int insertCouponDetail(CouponDetail couponDetail) {
		return (Integer) couponDetailDao.insertCouponDetail(couponDetail);
	}

	@Transactional
	public void deleteCouponDetail(CouponDetail couponDetail) {
		couponDetailDao.deleteCouponDetail(couponDetail);
	}

	@Transactional
	public void updateCouponDetail(CouponDetail couponDetail) {
		couponDetailDao.updateCouponDetail(couponDetail);
	}

	@Transactional
	public CouponDetail getCouponDetail(int couponDetailId) {
		return couponDetailDao.getCouponDetail(couponDetailId);
	}

	/* *****************************************************************
	 * UserCoupon DAO layer interaction
	 * *****************************************************************
	 */

	@Transactional
	public void insertUserCoupon(UserCoupon userCoupon) {
		userCouponDao.insertUserCoupon(userCoupon);
	}

	@Transactional
	public void deleteUserCoupon(User user, Coupon coupon, Account account) {
		UserCoupon userCoupon = new UserCoupon(user, coupon, account);
		userCouponDao.deleteUserCoupon(userCoupon);
	}

	@Transactional
	public void updateUserCoupon(UserCoupon userCoupon) {
		userCouponDao.updateUserCoupon(userCoupon);
	}

	@Transactional
	public List<UserCoupon> getUserCoupon(User user, Coupon coupon, Account account) {
		UserCoupon userCoupon = new UserCoupon(user, coupon, account);
		return userCouponDao.getUserCoupon(userCoupon);
	}

	@Transactional
	public List<UserCoupon> getUserCoupons(int userId) {
		return userCouponDao.getUserCoupons(userId);
	}

	/* *****************************************************************
	 * Application of Coupons in Kenan using TSCPMVNE
	 * *****************************************************************
	 */

	@Transactional
	public void applyCouponPayment(Account account, Double amount, Date date) throws CouponServiceException {
		try {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			XMLGregorianCalendar xmlCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
			String stringAmount = Formatter.formatDollarAmountQuery(amount);
			devLogger.log("....applying coupon payment for string amount of " + amount);
			truConnect.applyCouponPayment(account, stringAmount, xmlCal);
			devLogger.log("....finished applying coupon payment");
		} catch (DatatypeConfigurationException dce) {
			devLogger.error("....Error applying coupon payment in kenan: could not create XMLGregorianCalendar");
			throw new CouponServiceException(dce.getMessage(), dce.getCause());
		} catch (WebServiceException e) {
			devLogger.error("....Error applying coupon payment in kenan " + e.getMessage());
			throw new CouponServiceException("Error applying coupon payment: " + e.getMessage(), e.getCause());
		}
	}

	public List<Coupon> getCoupons() {
		// TODO
		return new Vector<Coupon>();
	}

	public List<KenanContract> getContracts(Account account, ServiceInstance serviceInstance)
			throws CouponServiceException {
		// TODO there needs to be a way for individual contracts to map back to
		// individual coupons
		try {
			devLogger.error("Fetching contracts in kenan");
			List<KenanContract> contracts = truConnect.getContracts(account, serviceInstance);
			return contracts;
		} catch (WebServiceException e) {
			devLogger.error("Error fetching contracts in kenan");
			throw new CouponServiceException(e.getMessage(), e.getCause());
		}
	}

	@Transactional
	public void applyCoupon(User user, Coupon coupon, Account account, ServiceInstance serviceInstance)
			throws CouponServiceException {
		try {
			devLogger.log("Applying coupon " + coupon.getCouponId() + " to account " + account.getAccountno());
			KenanContract kenanContract = new KenanContract();
			kenanContract.setAccount(account);
			kenanContract.setServiceInstance(serviceInstance);
			kenanContract.setContractType(coupon.getCouponDetail().getContract().getContractId());
			kenanContract.setDuration(coupon.getCouponDetail().getDuration());
			truConnect.applyContract(kenanContract);
			try {
				UserCoupon userCoupon = new UserCoupon(user, coupon, account);
				userCoupon.setKenanContractId(kenanContract.getContractId());
				userCoupon.setActive(true);
				insertUserCoupon(userCoupon);
			} catch (DataAccessException e) {
				kenanContract.setDuration(0);
				truConnect.updateContract(kenanContract);
				throw new CouponServiceException("Error inserting UserCoupon: " + e.getMessage(), e.getCause());
			}
		} catch (WebServiceException e) {
			devLogger.error("Error applying contract in kenan");
			throw new CouponServiceException(e.getMessage(), e.getCause());
		}
	}

	private KenanContract findContractInList(List<KenanContract> contracts, int contractId) {
		for (KenanContract kc : contracts) {
			if (kc.getContractType() == contractId) {
				return kc;
			}
		}
		return null;
	}

	@Transactional
	public void cancelCoupon(User user, Coupon coupon, Account account, ServiceInstance serviceInstance)
			throws CouponServiceException {
		// TODO contract ID should also be mapped in the database to make fetch's
		// easier. Then we can check for the contract Id rather than checking by
		// contract type
		try {
			List<KenanContract> contracts = truConnect.getContracts(account, serviceInstance);
			KenanContract kenanContract = findContractInList(contracts, coupon.getCouponDetail().getContract()
					.getContractId());
			if (kenanContract != null) {
				int originalDuration = kenanContract.getDuration();
				kenanContract.setAccount(account);
				kenanContract.setServiceInstance(serviceInstance);
				kenanContract.setDuration(0);
				truConnect.updateContract(kenanContract);
				try {
					UserCoupon userCoupon = new UserCoupon(user, coupon, account);
					userCoupon.setKenanContractId(kenanContract.getContractId());
					userCoupon.setActive(false);
					updateUserCoupon(userCoupon);
				} catch (DataAccessException e) {
					kenanContract.setDuration(originalDuration);
					truConnect.updateContract(kenanContract);
					throw new CouponServiceException("Error could not update UserCoupon: " + e.getMessage(), e.getCause());
				}
			} else {
				throw new CouponServiceException("Could not find associated contract for coupon " + coupon.getCouponId());
			}
		} catch (WebServiceException e) {
			devLogger.error("Error canceling coupon");
			throw new CouponServiceException(e.getMessage(), e.getCause());
		}
	}
}