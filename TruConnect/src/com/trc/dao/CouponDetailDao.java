package com.trc.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import com.trc.coupon.CouponDetail;
import com.trc.coupon.hibernate.HibernateUtil;

@Service
public class CouponDetailDao {

	public int insertCouponDetail(CouponDetail couponDetail) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			int result = (Integer) session.save(couponDetail);
			transaction.commit();
			return result;
		} catch (RuntimeException e) {
			e.printStackTrace();
			transaction.rollback();
			return -1;
		} finally {
			HibernateUtil.closeSession(session);
		}
	}

	public void deleteCouponDetail(CouponDetail couponDetail) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.delete(couponDetail);
			transaction.commit();
		} catch (RuntimeException e) {
			e.printStackTrace();
			transaction.rollback();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}

	public void updateCouponDetail(CouponDetail couponDetail) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.update(couponDetail);
			transaction.commit();
		} catch (RuntimeException e) {
			e.printStackTrace();
			transaction.rollback();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}

	public CouponDetail getCouponDetail(int couponDetailId) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			CouponDetail couponDetail = (CouponDetail) session.get(CouponDetail.class, couponDetailId);
			transaction.commit();
			return couponDetail;
		} catch (RuntimeException e) {
			e.printStackTrace();
			transaction.rollback();
			return null;
		} finally {
			HibernateUtil.closeSession(session);
		}
	}
}
