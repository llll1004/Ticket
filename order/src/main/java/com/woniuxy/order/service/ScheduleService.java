package com.woniuxy.order.service;

import com.woniuxy.order.service.impl.ScheduleServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "cinema", fallback = ScheduleServiceImpl.class)
public interface ScheduleService {

    @PostMapping("/schedule/seat-status")
    String updateSeatStatus(@RequestParam("status") String status,
                            @RequestParam("scheduleId") Integer scheduleId,
                            @RequestParam("seatId") Integer seatId);

    @GetMapping("/seat/get/{id}")
    String getById(@PathVariable Integer id);
}
