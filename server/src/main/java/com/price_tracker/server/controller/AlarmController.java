package com.price_tracker.server.controller;

import org.springframework.http.HttpStatus;
import com.price_tracker.server.entity.Alarm;
import org.springframework.http.ResponseEntity;
import com.price_tracker.server.service.AlarmService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/alarm")
public class AlarmController {
  private final AlarmService alarmService;

  @Autowired
  public AlarmController(AlarmService alarmService) {
    this.alarmService = alarmService;
  }

  @GetMapping("/load-alarm/{id}")
  public ResponseEntity<?> loadAlarm(@PathVariable int id) {
    Alarm alarm = alarmService.findAlarmById(id);
    if (alarm != null) {
      return ResponseEntity.status(HttpStatus.OK).body(alarm);
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
