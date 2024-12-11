package com.oe.tasks;

import com.oe.contant.OeConstant;
import com.oe.dao.OkxKlinesDataRepo;
import com.oe.objects.db.OkxKlinesDataDO;
import com.oe.objects.models.Kline;
import com.oe.objects.params.GetKeyLineParam;
import com.oe.remote.OkxApi;
import com.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class InitKlinesTask {

    @Autowired
    public OkxKlinesDataRepo okxKlinesDataRepo;

    public Runnable getInitKlinesTask() {
        return () -> {
            for (String instId : OeConstant.SupportedInstIds) {
                GetKeyLineParam param = new GetKeyLineParam();
                param.setInstId(instId);
                param.setBar("1D");
                param.setLimit(100);
                List<Kline> results = new ArrayList<>();
                boolean isFirst = true;
                while (!CollectionUtils.isEmpty(results) || isFirst) {
                    try {
                        results = OkxApi.getKeyLines(param);
                        isFirst = false;
                        if (CollectionUtils.isEmpty(results)) {
                            break;
                        }
                        List<OkxKlinesDataDO> dos = new ArrayList<>();
                        for (Kline kline : results) {
                            dos.add(kline.toDataDO());
                        }
                        System.out.println("拉取数据，instId=" + instId + " Last=" + results.getLast().getTimeStampMilli());
                        System.out.println("拉取数据，instId=" + instId + " first=" + results.getFirst().getTimeStampMilli());
                        System.out.println("拉取数据，instId=" + instId + " data=" + JsonUtils.toJson(dos));

                        okxKlinesDataRepo.saveAll(dos);
                        param.setAfter(new Date(results.getLast().getTimeStampMilli()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
    }


    public Runnable getNewKlinesTask() {
        return () -> {
            while (true) {
                for (String instId : OeConstant.SupportedInstIds) {
                    GetKeyLineParam param = new GetKeyLineParam();
                    param.setInstId(instId);
                    param.setBar("1D");
                    param.setLimit(30);
                    try {
                        List<Kline> results = OkxApi.getKeyLines(param);
                        if (CollectionUtils.isEmpty(results)) {
                            break;
                        }
                        List<OkxKlinesDataDO> dos = new ArrayList<>();
                        for (Kline kline : results) {
                            dos.add(kline.toDataDO());
                        }
                        for (OkxKlinesDataDO okxKlinesDataDO : dos) {
                            okxKlinesDataRepo.insertOnDuplicate(
                                    okxKlinesDataDO.getTimestampMilli(), okxKlinesDataDO.getOpenPrice(),
                                    okxKlinesDataDO.getHighPrice(), okxKlinesDataDO.getLowPrice(),
                                    okxKlinesDataDO.getClosePrice(), okxKlinesDataDO.getBar(),
                                    okxKlinesDataDO.getInstId(), okxKlinesDataDO.getConfirm());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            Thread.sleep(10*1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        };
    }
}
