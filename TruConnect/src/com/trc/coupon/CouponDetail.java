package com.trc.coupon;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.trc.coupon.contract.Contract;

/**
 * This object contains the duration of a Coupon and the Contract that it maps
 * to in Kenan.
 * 
 */

@Entity
@Table(name = "coupon_detail")
public class CouponDetail implements Serializable {
	private static final long serialVersionUID = -237740560474759272L;
	private int couponDetailId;
	private int duration;
	private Contract contract = new Contract();
	private Set<Coupon> coupons = new HashSet<Coupon>();

	@Id
	@Column(name = "coupon_detail_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getCouponDetailId() {
		return couponDetailId;
	}

	public void setCouponDetailId(int couponDetailId) {
		this.couponDetailId = couponDetailId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "contract_id", nullable = false, insertable = false)
	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "coupon_type", nullable = false)
	public Set<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(Set<Coupon> coupons) {
		this.coupons = coupons;
	}

	@Column(name = "duration")
	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("COUPON DETAIL").append("\n");
		sb.append("Coupon Detail ID: ").append(couponDetailId).append("\n");
		sb.append("Duration: ").append(duration).append("\n");
		sb.append(contract.toString());
		return sb.toString();
	}

}