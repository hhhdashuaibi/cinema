package com.example.cinema.bl.promotion;

import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.VIPKindForm;

public interface VIPKindService {
    ResponseVO addVIPKind(VIPKindForm vipKindForm);

    ResponseVO deleteVIPKind(String name);

    ResponseVO getVIPKindByName(String name);

    ResponseVO getVIPKinds();

    ResponseVO updateVIPKind(VIPKindForm vipKindForm);


}
