package com.example.cinema.bl.promotion;

import com.example.cinema.vo.CouponForm;
import com.example.cinema.vo.ResponseVO;

/**
 * Created by liying on 2019/4/17.
 */
public interface CouponService {

    /**
     * 根据用户Id获取该用户所有的优惠券
     * @param userId
     * @return
     */
    ResponseVO getCouponsByUser(int userId);

    /**
     * 根据前端传回的优惠券信息增加优惠券记录
     * @param couponForm
     * @return
     */
    ResponseVO addCoupon(CouponForm couponForm);

    /**
     * 发放优惠券，在coupon_user表中增加一条优惠券与用户的对应记录
     *
     * @param couponId
     * @param userId
     * @return
     */
    ResponseVO issueCoupon(int couponId,int userId);

    /**
     * 根据最低金额发放优惠券，在coupon_user表中增加多条优惠券与用户的对应记录
     *
     * @param couponId
     * @param targetPurchase
     * @return
     */
    ResponseVO issueCoupons(int couponId, double targetPurchase);
}
