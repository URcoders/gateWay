package com.qgailab.gateway.controller;

import com.qgailab.gateway.config.ServiceManager;
import com.qgailab.gateway.dto.ResponseData;
import com.qgailab.gateway.enums.Status;
import com.qgailab.gateway.model.*;
import com.qgailab.gateway.raft.internal.RaftNodeBootStrap;
import com.qgailab.gateway.raft.internal.rpc.DefaultRpcClient;
import com.qgailab.gateway.service.AdminService;
import com.qgailab.gateway.service.Arbitration;
import com.qgailab.gateway.service.BlackListService;
import com.qgailab.gateway.util.DaoUtil;
import com.qgailab.gateway.util.ParseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author linxu
 * @date 2019/11/16
 * <tip>take care of yourself.everything is no in vain.</tip>
 * 这是仲裁会的控制器，处理仲裁会事务。
 */
@RestController
@RequestMapping("/gateway")
@Slf4j
public class ArbitrationController {
    @Autowired
    private Arbitration arbitration;

    @Autowired
    private RaftNodeBootStrap bootStrap;

    @Autowired
    private AdminService adminService;

    @Autowired
    private BlackListService blackListService;

    /**
     * 提供给服务接入的
     * @param serviceInfo
     * @return
     */
    @PostMapping("/access")
    public ResponseData goAccess(@RequestBody ServiceInfo serviceInfo) {
        //TODO  linxu
        serviceInfo.setExpire(System.currentTimeMillis() + 20000);
        Proposal<ServiceInfo> proposal = new Proposal<ServiceInfo>();
        proposal.setProposeTime(System.currentTimeMillis());
        proposal.setObj(serviceInfo);
        proposal.setType(Proposal.Type.ACCESS_APPROVAL);
        ResponseData responseData=new ResponseData();
        if (arbitration.arbitrate(proposal).accept()){
            responseData.setStatus(Status.OP_OK.getValue());
            responseData.setMessage("接入成功！");
        }else {
            responseData.setStatus(Status.OP_FAIL.getValue());
            responseData.setMessage("接入失败！");
        }
        return responseData;
    }

    @GetMapping("/queryService")
    public ResponseData queryServices() {
        ResponseData responseData = new ResponseData();
        responseData.setServiceList(ParseUtil.parseToListServiceInfo(
                DaoUtil.get(new DefaultRpcClient(), ServiceManager.SERVICES_NAMESPACE, bootStrap.getAddressList())
        ));
        //所有东西暂时取消TOKEN认证，后续再补充
        responseData.setStatus(Status.OP_OK.getValue());
        return responseData;
    }


    //TODO here 钰朝  郭沛
    @PostMapping("/login")
    public Map<String, Object> adminLogin(@RequestBody User user) {
        return adminService.AdminLogin(user);
    }

    @PostMapping("/queryBlackListNumber")
    public Map<String, Object> queryBlackListNumber(@RequestBody Paging paging) {
        return blackListService.queryBlackListNumber(paging);
    }

    @PostMapping("/addBlackList")
    public Map<String, Object> addBlackList(@RequestBody BlackList blackList) {
        return blackListService.addBlackList(blackList);
    }

    @PostMapping("/deleteBlackList")
    public Map<String, Object> deleteBlackList(@RequestBody BlackList blackList) {
        return blackListService.deleteBlackList(blackList);
    }
}
