package com.price_tracker.server.service;

import com.price_tracker.server.entity.Alarm;
import com.price_tracker.server.repository.AlarmRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlarmService {
  private final AlarmRepo alarmRepo;

  @Autowired
  public AlarmService(AlarmRepo alarmRepo) {
    this.alarmRepo = alarmRepo;
  }

  public Alarm findAlarmById(int id) {
    return alarmRepo.findById(id);
  }
}
