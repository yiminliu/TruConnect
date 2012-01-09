package com.trc.manager;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trc.report.ActivationReport;
import com.trc.user.User;
import com.trc.util.logger.activation.ActState;
import com.trc.util.logger.activation.ActivationMap;
import com.trc.util.logger.activation.ActivationState;

@Component
public class ReportManager {
  @Autowired
  private UserManager userManager;
  @Autowired
  private ActivationStateManager activationStateManager;

  private static final String RESERVED_USER = "reserve";
  private static final int RESERVED_SUBSTRING_LENGTH = 16;

  public ActivationReport getActivationReport(Date startDate, Date endDate) {
    ActivationReport report = new ActivationReport();
    report.setActivatedUsers(getActiveUsers(startDate, endDate));
    report.setUniqueReservations(getUniqueReservedUsers(startDate, endDate));
    report.setFailedStates(getFailedStates(report.getFailedReservations()));
    return report;
  }

  public Collection<User> getUniqueReservedUsers() {
    return getUniqueReservedUsers(null, null);
  }

  public Collection<User> getUniqueReservedUsers(Date startDate, Date endDate) {
    setDates(startDate, endDate);
    List<User> results = userManager.searchByEmailAndDate(RESERVED_USER, startDate, endDate);
    for (User user : results) {
      user.setEmail(user.getEmail().substring(RESERVED_SUBSTRING_LENGTH));
      user.setUsername(user.getUsername().substring(RESERVED_SUBSTRING_LENGTH));
    }
    Collection<User> uniqueResults = new HashSet<User>();
    uniqueResults.addAll(results);
    return uniqueResults;
  }

  public Collection<User> getActiveUsers() {
    return getActiveUsers(null, null);
  }

  public Collection<User> getActiveUsers(Date startDate, Date endDate) {
    setDates(startDate, endDate);
    return userManager.searchByNotEmailAndDate(RESERVED_USER, startDate, endDate);
  }

  public ActivationState getLastState(User user) {
    List<ActivationMap> activations = activationStateManager.getActivationMapByUserId(user.getUserId());
    if (activations != null && activations.size() > 0) {
      ActivationMap actMap = activations.get(0);
      ActivationState actState = activationStateManager.getActivationState(actMap, ActState.ROOT);
      while (actState.getChildren().size() > 0) {
        actState = actState.getChildren().toArray(new ActivationState[0])[0];
      }
      return actState;
    }
    return null;
  }

  private EnumMap<ActState, Integer> getFailedStates(Collection<User> failedReservations) {
    EnumMap<ActState, Integer> failedStates = new EnumMap<ActState, Integer>(ActState.class);
    int count;
    ActState lastState;
    for (User user : failedReservations) {
      lastState = getLastState(user).getState();
      count = failedStates.get(lastState) == null ? 0 : failedStates.get(lastState);
      failedStates.put(lastState, count);
    }
    return failedStates;
  }

  private void setDates(Date startDate, Date endDate) {
    if (startDate == null || endDate == null) {
      Calendar calendar = Calendar.getInstance();
      if (startDate == null) {
        calendar.set(2000, 0, 1);
        startDate = calendar.getTime();
      }
      if (endDate == null) {
        calendar.set(2020, 0, 1);
        endDate = calendar.getTime();
      }
    }
  }

}
