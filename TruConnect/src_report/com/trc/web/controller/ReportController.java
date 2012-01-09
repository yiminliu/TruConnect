package com.trc.web.controller;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.trc.manager.ReportManager;
import com.trc.report.ActivationReport;
import com.trc.util.logger.DevLogger;
import com.trc.util.logger.activation.ActivationState;
import com.trc.web.model.ResultModel;

@Controller
@RequestMapping("/admin/report")
public class ReportController {
  @Autowired
  private ReportManager reportManager;

  @RequestMapping(value = "/activation", method = RequestMethod.GET)
  public ModelAndView getActivationMap() {
    ResultModel model = new ResultModel("admin/report/activation");
    Calendar startDate = Calendar.getInstance();
    startDate.set(2011, 11, 4);
    Calendar endDate = Calendar.getInstance();
    endDate.set(2011, 11, 21);

    ActivationReport report = reportManager.getActivationReport(startDate.getTime(), endDate.getTime());
    model.addObject("report", report);
    return model.getSuccess();
  }

  private void printChildren(ActivationState actState) {
    for (ActivationState child : actState.getChildren()) {
      DevLogger.log(child.getActivationStateId().getActState().toString());
      printChildren(child);
    }
  }

}