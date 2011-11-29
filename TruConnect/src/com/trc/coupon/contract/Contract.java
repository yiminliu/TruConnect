package com.trc.coupon.contract;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.trc.coupon.CouponDetail;

/**
 * This object is the representation of a Contract in Kenan.
 * 
 */

@Entity
@Table(name = "coupon_contracts")
public class Contract implements Serializable {
	private static final long serialVersionUID = 2814309740931497593L;
	private int contractId;
	private String description;
	private Collection<CouponDetail> couponDetails = new HashSet<CouponDetail>();

	@Id
	@Column(name = "contract_id")
	public int getContractId() {
		return contractId;
	}

	public void setContractId(int contractId) {
		this.contractId = contractId;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contract")
	@Cascade(CascadeType.SAVE_UPDATE)
	public Collection<CouponDetail> getCoupons() {
		return couponDetails;
	}

	public void setCoupons(Collection<CouponDetail> couponDetails) {
		this.couponDetails = couponDetails;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("contractId=").append(contractId).append(", ");
		sb.append("description=").append(description);
		return sb.toString();
	}

	@Transient
	public String toFormattedString() {
		StringBuffer sb = new StringBuffer();
		sb.append("--Contract--").append("\n");
		sb.append("  Contract ID=").append(contractId).append("\n");
		sb.append("  Description=").append(description);
		return sb.toString();
	}

}