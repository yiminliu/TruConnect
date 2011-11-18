package com.trc.service;

import javax.xml.ws.WebServiceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.coupon.Coupon;
import com.trc.exception.service.CouponServiceException;
import com.trc.service.gateway.TruConnectGateway;
import com.tscp.mvne.Account;
import com.tscp.mvne.KenanContract;
import com.tscp.mvne.ServiceInstance;
import com.tscp.mvne.TruConnect;

@Service
public class CouponService {
	private TruConnect truConnect;

	@Autowired
	public void init(TruConnectGateway truConnectGateway) {
		this.truConnect = truConnectGateway.getPort();
	}

	public void redeemCoupon(Coupon coupon, Account account, ServiceInstance serviceInstance)
			throws CouponServiceException {
		try {
			KenanContract kenanContract = new KenanContract();
			kenanContract.setAccount(account);
			kenanContract.setServiceInstance(serviceInstance);
			kenanContract.setContractType(coupon.getCouponDetail().getContract().getContractId());
			kenanContract.setDuration(coupon.getCouponDetail().getDuration());
			truConnect.applyContract(kenanContract);
		} catch (WebServiceException e) {
			throw new CouponServiceException(e.getMessage(), e.getCause());
		}
	}

	public void cancelCoupon(Coupon coupon, Account account, ServiceInstance serviceInstance)
			throws CouponServiceException {
		// TODO
	}

}