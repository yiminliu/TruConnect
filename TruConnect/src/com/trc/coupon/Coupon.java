package com.trc.coupon;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * This object represents a Coupon that refers to a Contract in Kenan.
 * 
 */

@Entity
@Table(name = "coupons")
public class Coupon implements Serializable {
	private static final long serialVersionUID = 3994060771315068342L;
	private int couponId;
	private String couponCode;
	private Date startDate;
	private Date endDate;
	private boolean enabled;
	private int quantity;
	private int used;
	private CouponDetail couponDetail;

	@Id
	@Column(name = "coupon_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getCouponId() {
		return couponId;
	}

	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}

	@Column(name = "coupon_code")
	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	@Column(name = "date_start")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "date_end")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "enabled", columnDefinition = "BOOLEAN")
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Column(name = "quantity")
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Column(name = "used")
	public int getUsed() {
		return used;
	}

	public void setUsed(int used) {
		this.used = used;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade(CascadeType.SAVE_UPDATE)
	@JoinColumn(name = "coupon_type", nullable = false, insertable = false, updatable = false)
	public CouponDetail getCouponDetail() {
		return couponDetail;
	}

	public void setCouponDetail(CouponDetail couponDetail) {
		this.couponDetail = couponDetail;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("COUPON").append("\n");
		sb.append("ID: ").append(couponId).append("\n");
		sb.append("Code: ").append(couponCode).append("\n");
		sb.append("Start: ").append(startDate).append("\n");
		sb.append("End: ").append(endDate).append("\n");
		sb.append("Enabled: ").append(enabled).append("\n");
		sb.append("Used: ").append(used).append("\n");
		sb.append(couponDetail.toString());
		return sb.toString();
	}

	@Transient
	public boolean isEmpty() {
		return couponCode == null || couponCode.isEmpty();
	}

}