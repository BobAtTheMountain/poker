package com.oe.controller;

import com.game.pokers.dto.ResDTO;
import com.oe.contant.OeConstant;
import com.oe.dao.OkxKlinesDataRepo;
import com.oe.objects.db.OkxKlinesDataDO;
import com.oe.objects.models.DayPolicyResult;
import com.oe.policies.OePolicy;
import com.oe.tasks.InitKlinesTask;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OE {

    @Resource
    private InitKlinesTask initKlinesTask;

    @Resource
    private OkxKlinesDataRepo okxKlinesDataRepo;

    @RequestMapping("/do_init_klines_task")
    public String doInitKlinesTask() {
        Runnable initTask = initKlinesTask.getInitKlinesTask();
        new Thread(initTask).start();
        return "启动成功";
    }

    @RequestMapping("/do_new_klines_task")
    public String doNewKlinesTask() {
        Runnable newTask = initKlinesTask.getNewKlinesTask();
        new Thread(newTask).start();
        return "启动成功";
    }

    @RequestMapping("/get_klines")
    public ResDTO<List<List<String>>> getKlines(HttpServletRequest request, @RequestParam String instId, @RequestParam String bar) {
        List<OkxKlinesDataDO> doList = okxKlinesDataRepo.getKLines(instId, bar, 1000000);
        List<List<String>> res = new ArrayList<>();
        if (!CollectionUtils.isEmpty(doList)) {
            doList.forEach(o -> res.add(o.ToApiList()));
        }
        return ResDTO.getSuccessResDTO(res);
    }

    @RequestMapping("/get_recommend")
    public String getKlines(HttpServletRequest request) {
        StringBuilder policyDesc = new StringBuilder();
        for (String instId : OeConstant.SupportedInstIds) {
            List<OkxKlinesDataDO> doList = okxKlinesDataRepo.getKLines(instId, "1D", 100);

            DayPolicyResult resultNowK = OePolicy.getPolicyV1(doList);
            policyDesc.append(resultNowK.toPageDesc()).append("\r\n");
            if (doList != null && doList.size() > 1) {
                doList.removeFirst();
                DayPolicyResult resultLastK = OePolicy.getPolicyV1(doList);
                policyDesc.append(resultLastK.toPageDesc()).append("\r\n");
            }
        }
        return policyDesc.toString();
    }
}
