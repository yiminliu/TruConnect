package com.trc.dao;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Service;

import com.trc.coupon.Coupon;
import com.trc.coupon.hibernate.HibernateUtil;
import com.trc.user.User;
import com.tscp.mvne.Account;

@Service
public class CouponDao {

	public int insertCoupon(Coupon coupon) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			Serializable result = session.save(coupon);
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

	public void deleteCoupon(Coupon coupon) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.delete(coupon);
			transaction.commit();
		} catch (RuntimeException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}

	public void updateCoupon(Coupon coupon) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.update(coupon);
			transaction.commit();
		} catch (RuntimeException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}

	public Coupon getCoupon(int couponId) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			Coupon coupon = (Coupon) session.get(Coupon.class, couponId);
			return coupon;
		} catch (RuntimeException e) {
			transaction.rollback();
			e.printStackTrace();
			return null;
		} finally {
			HibernateUtil.closeSession(session);
		}
	}

	public Coupon getCouponByCode(String couponCode) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			Query query = session.createQuery("from TruCoupon trucoupon where couponCode = ?").setString(0, couponCode);
			Coupon coupon = (Coupon) query.uniqueResult();
			return coupon;
		} catch (RuntimeException e) {
			transaction.rollback();
			e.printStackTrace();
			return null;
		} finally {
			HibernateUtil.closeSession(session);
		}
	}

	public void insertUserCoupon(final User user, final Coupon coupon, final Account account) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.doWork(new Work() {
				public void execute(Connection connection) throws SQLException {
					CallableStatement call = connection.prepareCall("insert into coupon_user_map values (?, ?, ?)");
					call.setInt(1, user.getUserId());
					call.setInt(2, coupon.getCouponId());
					call.setInt(3, account.getAccountno());
					call.execute();
				}
			});
			transaction.commit();
		} catch (RuntimeException e) {
			transaction.rollback();
			e.printStackTrace();
		}
	}

	public void deleteUserCoupon(final User user, final Coupon coupon, final Account account) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.doWork(new Work() {
				public void execute(Connection connection) throws SQLException {
					CallableStatement call = connection
							.prepareCall("delete from coupon_user_map where user_id = ? and coupon_id = ? and account_number = ?");
					call.setInt(1, user.getUserId());
					call.setInt(2, coupon.getCouponId());
					call.setInt(3, account.getAccountno());
					call.execute();
				}
			});
			transaction.commit();
		} catch (RuntimeException e) {
			transaction.rollback();
			e.printStackTrace();
		}
	}

}