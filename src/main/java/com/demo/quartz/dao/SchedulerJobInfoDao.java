package com.demo.quartz.dao;

import com.demo.quartz.domain.SchedulerJobInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchedulerJobInfoDao extends JpaRepository<SchedulerJobInfo, String> {
    boolean existsById(String id);

    void deleteById(String id);

}
