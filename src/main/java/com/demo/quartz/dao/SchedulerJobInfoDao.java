package com.demo.quartz.dao;

import com.demo.quartz.domain.SchedulerJobInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface SchedulerJobInfoDao extends JpaRepository<SchedulerJobInfo, String> {

    boolean existsById(String id);

    void deleteById(String id);

    @Query(value = "select sj from SchedulerJobInfo sj where sj.userId = ?1")
    List<SchedulerJobInfo> findAll(Long userId);

}
