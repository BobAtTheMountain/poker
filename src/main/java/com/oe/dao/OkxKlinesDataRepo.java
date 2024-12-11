package com.oe.dao;

import com.oe.objects.db.OkxKlinesDataDO;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OkxKlinesDataRepo extends CrudRepository<OkxKlinesDataDO, Long> {

    @Modifying
    @Query("insert into okx_klines_data(timestamp_milli, open_price, high_price, low_price,close_price, confirm, bar, inst_id)values(:timestampMilli, :openPrice,:highPrice, :lowPrice, :closePrice,:confirm, :bar, :instId) ON DUPLICATE KEY UPDATE confirm= :confirm")
    void insertOnDuplicate(Long timestampMilli, Double openPrice, Double highPrice,
                         Double lowPrice, Double closePrice, String bar, String instId, Boolean confirm);

    @Query("select * from okx_klines_data where inst_id=:instId and bar=:bar order by timestamp_milli desc limit :limit")
    List<OkxKlinesDataDO> getKLines(String instId, String bar, int limit);
}
