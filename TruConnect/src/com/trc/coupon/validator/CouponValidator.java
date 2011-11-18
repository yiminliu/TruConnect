package com.trc.coupon.validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.trc.coupon.Coupon;
import com.trc.coupon.hibernate.HibernateUtil;
import com.trc.manager.CouponManager;
import com.trc.user.User;
import com.tscp.mvne.Account;

@Component
public class CouponValidator {
	private static final CouponManager couponManager = new CouponManager();

	public boolean validateCoupon(Coupon coupon, Account account) {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean validateCoupon(final Coupon coupon, final User user, final Account account) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		int existingCouponCount = 0;
		if (validateCoupon(coupon)) {
			if (!session.isOpen()) {
				session = HibernateUtil.getCurrentSession();
				transaction = session.beginTransaction();
			}
			try {
				Connection connection = session.connection();
				PreparedStatement statement = connection
						.prepareStatement("select count(*) from user_coupons where user_id = ? and coupon_id = ? and account_number = ?");
				statement.setInt(1, user.getUserId());
				statement.setInt(2, coupon.getCouponId());
				statement.setInt(3, account.getAccountno());
				statement.execute();
				ResultSet resultSet = statement.getResultSet();
				while (resultSet.next()) {
					existingCouponCount = resultSet.getInt(1);
				}
				System.out.println(1);
				return existingCouponCount < 1;
			} catch (SQLException e) {
				transaction.rollback();
				e.printStackTrace();
				return false;
			}
		} else {
			System.out.println(2);
			return false;
		}
	}

	public boolean validateCoupon(Coupon coupon) {
		coupon = couponManager.getCouponByCode(coupon.getCouponCode());
		if (validateCouponProperties(coupon)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean validateCouponProperties(Coupon coupon) {
		return coupon != null && coupon.isEnabled() && validateStartDate(coupon.getStartDate())
				&& validateEndDate(coupon.getEndDate()) && validateCouponCode(coupon.getCouponCode());
	}

	private boolean validateCouponCode(String couponCode) {
		// TODO
		if (couponCode != null) {
			return couponCode.substring(0, 3).equals("tru");
		} else {
			return false;
		}
	}

	private boolean validateEndDate(Date endDate) {
		return (endDate == null || endDate.compareTo(new Date()) >= 0);
	}

	private boolean validateStartDate(Date startDate) {
		return (startDate != null && startDate.compareTo(new Date()) <= 0);
	}

}
