package com.trc.coupon.tester;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.trc.coupon.Coupon;
import com.trc.coupon.CouponDetail;
import com.trc.coupon.contract.Contract;
import com.trc.coupon.hibernate.HibernateUtil;
import com.trc.coupon.validator.CouponValidator;
import com.trc.manager.CouponManager;
import com.trc.user.User;
import com.trc.util.ClassUtils;
import com.tscp.mvne.Account;

public class Tester {
	private static final CouponManager couponManager = new CouponManager();
	private static final CouponValidator couponValidator = new CouponValidator();

	public static void main(String[] args) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction transaction = session.beginTransaction();

		CouponDetail couponDetail = (CouponDetail) session.get(CouponDetail.class, 5);
		System.out.println(ClassUtils.toString(couponDetail));

		Coupon coupon = new Coupon();
		coupon.setStartDate(new Date());
		coupon.setEnabled(true);
		coupon.setCouponDetail(couponDetail);
		coupon.setCouponCode("tru3");

		couponDetail.getCoupons().add(coupon);
		
		session.save(coupon);
		transaction.commit();

	}

	public static CouponDetail getCouponDetail(int id) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		CouponDetail couponDetail = (CouponDetail) session.get(CouponDetail.class, id);
		return couponDetail;
	}

	public static Coupon insertCoupon(String code, CouponDetail couponDetail) {
		Coupon coupon = new Coupon();
		coupon.setStartDate(new Date());
		coupon.setEnabled(true);
		coupon.setCouponCode(code);
		coupon.setCouponDetail(couponDetail);
		couponDetail.getCoupons().add(coupon);
		// coupon.setCouponType(2);
		// couponManager.insertCoupon(coupon);
		couponManager.updateCouponDetail(couponDetail);

		return coupon;
	}

	private Account getAccount() {
		Account account = new Account();
		account.setAccountno(1234);
		return account;
	}

	private User getUser() {
		User user = new User();
		user.setUserId(547);
		user.setUsername("jonathan.pong@gmail.com");
		user.setEmail("jonathan.pong@gmail.com");
		return user;
	}

	private Contract getContract() {
		Session session = HibernateUtil.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Contract contract = (Contract) session.get(Contract.class, 10007);
		return contract;
	}

}
