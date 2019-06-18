package com.example.cinema.bl.promotion;

import com.example.cinema.vo.RefundForm;
import com.example.cinema.vo.ResponseVO;

public interface RefundService {
    ResponseVO publishRefund(RefundForm refundForm);

    ResponseVO getRefunds();

    ResponseVO changeRefund(RefundForm refundForm);
}
