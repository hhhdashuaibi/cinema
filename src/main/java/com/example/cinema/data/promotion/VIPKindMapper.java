package com.example.cinema.data.promotion;

import com.example.cinema.po.VIPKind;
import com.example.cinema.vo.VIPKindForm;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VIPKindMapper {

    int insertOneVIPKind(VIPKind vipKind);

    VIPKind selectVIPKindByName(String name);

    List<VIPKind> selectVIPKinds();

    void deleteVIPKind(String name);

    int updateVIPKind(VIPKindForm vipKindForm);

}
