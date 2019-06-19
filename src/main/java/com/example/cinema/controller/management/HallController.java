package com.example.cinema.controller.management;

import com.example.cinema.bl.management.HallService;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**影厅管理
 * @author fjj
 * @date 2019/4/12 1:59 PM
 */
@RestController
public class HallController {
    @Autowired
    private HallService hallService;

    @RequestMapping(value = "hall/all", method = RequestMethod.GET)
    public ResponseVO searchAllHall(){
        return hallService.searchAllHall();
    }

    @RequestMapping(value = "hall/add", method = RequestMethod.GET)
    public ResponseVO addHall(@RequestParam String name,@RequestParam String type){
        return hallService.addHall(name,type);
    }

    @RequestMapping(value = "hall/modify", method = RequestMethod.GET)
    public ResponseVO modifyHall(@RequestParam String name,@RequestParam String type,@RequestParam int id){
        return hallService.modifyHall(name,type,id);
    }

    @RequestMapping(value = "hall/delete", method = RequestMethod.GET)
    public ResponseVO deleteHall(@RequestParam int id){
        return hallService.deleteHall(id);
    }

}
