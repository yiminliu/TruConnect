package com.trc.service;

import java.util.List;

import javax.xml.ws.WebServiceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trc.coupon.Coupon;
import com.trc.coupon.CouponDetail;
import com.trc.coupon.UserCoupon;
import com.trc.dao.CouponDao;
import com.trc.exception.service.CouponServiceException;
import com.trc.service.gateway.TruConnectGateway;
import com.trc.user.User;
import com.tscp.mvne.Account;
import com.tscp.mvne.KenanContract;
import com.tscp.mvne.ServiceInstance;
import com.tscp.mvne.TruConnect;

@Service
public class CouponService {
	private TruConnect truConnect;
	private CouponDao couponDao;

	/* *****************************************************************
	 * Initialization
	 * *****************************************************************
	 */

	@Autowired
	public void init(TruConnectGateway truConnectGateway, CouponDao couponDao) {
		this.truConnect = truConnectGateway.getPort();
		this.couponDao = couponDao;
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
		return (Integer) couponDao.insertCouponDetail(couponDetail);
	}

	@Transactional
	public void deleteCouponDetail(CouponDetail couponDetail) {
		couponDao.deleteCouponDetail(couponDetail);
	}

	@Transactional
	public void updateCouponDetail(CouponDetail couponDetail) {
		couponDao.updateCouponDetail(couponDetail);
	}

	@Transactional
	public CouponDetail getCouponDetail(int couponDetailId) {
		return couponDao.getCouponDetail(couponDetailId);
	}

	/* *****************************************************************
	 * UserCoupon DAO layer interaction
	 * *****************************************************************
	 */

	@Transactional
	public void insertUserCoupon(User user, Coupon coupon, Account account) {
		UserCoupon userCoupon = new UserCoupon(user, coupon, account);
		couponDao.insertUserCoupon(userCoupon);
	}

	@Transactional
	public void deleteUserCoupon(User user, Coupon coupon, Account account) {
		UserCoupon userCoupon = new UserCoupon(user, coupon, account);
		couponDao.deleteUserCoupon(userCoupon);
	}

	@Transactional
	public List<UserCoupon> getUserCoupons(User user, Coupon coupon, Account account) {
		UserCoupon userCoupon = new UserCoupon(user, coupon, account);
		return couponDao.getUserCoupons(userCoupon);
	}

	/* *****************************************************************
	 * Application of Coupons in Kenan using TSCPMVNE
	 * *****************************************************************
	 */

	public void applyContract(Coupon coupon, Account account, ServiceInstance serviceInstance)
			throws CouponServiceException {
		try {
			System.out.println("TC! applying contract in kenan");
			KenanContract kenanContract = new KenanContract();
			kenanContract.setAccount(account);
			kenanContract.setServiceInstance(serviceInstance);
			kenanContract.setContractType(coupon.getCouponDetail().getContract().getContractId());
			kenanContract.setDuration(coupon.getCouponDetail().getDuration());
			truConnect.applyContract(kenanContract);
		} catch (WebServiceException e) {
			System.out.println("TC! error applying contract in kenan");
			throw new CouponServiceException(e.getMessage(), e.getCause());
		}
	}

	public void cancelContract(Coupon coupon, Account account, ServiceInstance serviceInstance)
			throws CouponServiceException {
		// TODO
	}

}