package com.trc.web.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.trc.config.Config;
import com.trc.domain.support.report.payment.FailedPaymentHistory;
import com.trc.domain.support.report.payment.PaymentReport;
import com.trc.manager.ActivationReportManager;
import com.trc.manager.UserManager;
import com.trc.report.ActivationReport;
import com.trc.report.UserActivationReport;
import com.trc.service.report.PaymentReportService;
import com.trc.user.User;
import com.trc.util.logger.DevLogger;
import com.trc.util.logger.activation.ActivationState;
import com.trc.web.model.ResultModel;

@Controller
@RequestMapping("/admin/report")
public class ReportController {
  @Autowired
  private ActivationReportManager reportManager;
  @Autowired 
  private PaymentReportService paymentReportService;
  @Autowired
  private UserManager userManager;
  
  @ModelAttribute
  private void dateReferenceData(ModelMap modelMap) {
    modelMap.addAttribute("states", Config.states.entrySet());
    modelMap.addAttribute("months", Config.months.entrySet());
    modelMap.addAttribute("years", Config.yearsPast.entrySet());
    int numDaysInMonth = new DateTime().dayOfMonth().getMaximumValue();
    int[] days = new int[numDaysInMonth];
    for (int i = 0; i < numDaysInMonth; i++) {
      days[i] = i + 1;
    }
    modelMap.addAttribute("days", days);
  }

  @RequestMapping(method = RequestMethod.GET)
  public String showReports() {
    return "admin/report/reports";
  }

  @RequestMapping(value = "/activation", method = RequestMethod.GET)
  public String showActivaitonReportRequest() {
    return "admin/report/activation/request";
  }

  @RequestMapping(value = "/activation/{quickLink}", method = RequestMethod.GET)
  public ModelAndView getQuickView(@PathVariable String quickLink) {
    ResultModel model = new ResultModel("admin/report/activation/report");
    DateTime startDate = new DateTime();
    DateTime endDate = new DateTime();
    String period = null;
    if (quickLink.equals("lastMonth")) {
      startDate = startDate.minusMonths(1).dayOfMonth().withMinimumValue();
      endDate = startDate.dayOfMonth().withMaximumValue();
      period = "Last Month " + startDate.toString("MMMM yyyy");
    } else if (quickLink.equals("lastWeek")) {
      startDate = startDate.withDayOfWeek(DateTimeConstants.MONDAY).minusWeeks(1);
      endDate = startDate.withDayOfWeek(DateTimeConstants.SUNDAY);
      period = "Last Week " + startDate.toString("MMMM dd yyyy") + " to " + endDate.toString("MMMM dd yyyy");
    } else if (quickLink.equals("thisMonth")) {
      startDate = startDate.dayOfMonth().withMinimumValue();
      period = "This Month " + startDate.toString("MMMM yyyy");
    } else if (quickLink.equals("thisWeek")) {
      startDate = startDate.withDayOfWeek(DateTimeConstants.MONDAY);
      period = "This Week " + startDate.toString("MMMM dd yyyy");
    } else if (quickLink.equals("yesterday")) {
      startDate = startDate.minusDays(1);
      period = "Yesterday " + startDate.toString("MMMM dd yyyy");
    } else if (quickLink.equals("today")) {
      period = "Today " + startDate.toString("MMMM dd yyyy");
    }
    ActivationReport report = reportManager.getActivationReport(startDate, endDate);
    model.addObject("report", report);
    model.addObject("period", period);
    return model.getSuccess();
  }

  @RequestMapping(value = "/activation", method = RequestMethod.POST)
  public ModelAndView getActivationMap(@RequestParam("month_start") int monthStart,
      @RequestParam("day_start") int dayStart, @RequestParam("year_start") int yearStart,
      @RequestParam("month_end") int monthEnd, @RequestParam("day_end") int dayEnd,
      @RequestParam("year_end") int yearEnd) {
    ResultModel model = new ResultModel("admin/report/activation/report");
    DateTime startDate = new DateTime(yearStart, monthStart, dayStart, 0, 0);
    DateTime endDate = new DateTime(yearEnd, monthEnd, dayEnd, 23, 59);

    String period = "From " + startDate.toString("MM/dd/yyyy") + " to " + endDate.toString("MM/dd/yyyy");

    ActivationReport report = reportManager.getActivationReport(startDate, endDate);
    model.addObject("report", report);    
    return model.getSuccess();
  }

  @RequestMapping(value = "/activation/user/{userId}", method = RequestMethod.GET)
  public ModelAndView getUserActivationReport(@PathVariable int userId) {
    ResultModel model = new ResultModel("admin/report/activation/user/report");
    UserActivationReport report = reportManager.getUserActivationReport(userId);
    model.addObject("report", report);
    return model.getSuccess();
  } 

  private void printChildren(ActivationState actState) {
    for (ActivationState child : actState.getChildren()) {
      DevLogger.log(child.getActivationStateId().getActState().toString());
      printChildren(child);
    }
  }
  
  /**
   * This method will display all failed payment report requests
   * @return String
   */
  @RequestMapping(value="/payment", method=RequestMethod.GET)
  public String showFailedPaymentReportRequest(Model model){
	  Collection<Integer> accountNoList = getAllAccountNumbers();
	  List<String> userNameList = getUserNames();
	  Collection<String> failureCodeList = getAllFailureCodes();
	  model.addAttribute("accountNoList", accountNoList);
	  model.addAttribute("userNameList", userNameList);
	  model.addAttribute("failureCodeList", failureCodeList);	  
	  userManager.setSessionUser(null);
      return "admin/report/payment/request";	  
  }
  
  /**
   * This method handles the pre-defined quick date range requests
   * @param quickLink
   * @return String
   */    
  @RequestMapping(value = "/payment/{quickLink}", method = RequestMethod.GET)
  public String getQuickPaymentReport(@PathVariable("quickLink") String quickLink) {
	 return "redirect:/admin/report/payment/"+ quickLink + "/1";		  
  }
  
  /**
   * This method handles the pre-defined date range requests. It handles the pagination as well.
   * @param quickLink
   * @Param page
   * @return ModelAndView
   */    
  @RequestMapping(value = "/payment/{quickLink}/{page}", method = RequestMethod.GET)
  public ModelAndView getQuickPaymentReport(@PathVariable("quickLink") String quickLink, @PathVariable("page") int page) {
     ResultModel model = new ResultModel("admin/report/payment/report");
     DateTime startDate = new DateTime();
     DateTime endDate = new DateTime();
     String period = null;
     String periodShort = null;
     List<PaymentReport> reportList = null;
      
     if (quickLink.equals("lastMonth")) {
         startDate = startDate.minusMonths(1).dayOfMonth().withMinimumValue();
         endDate = startDate.dayOfMonth().withMaximumValue();
         period = "Last Month " + startDate.toString("MMMM yyyy");
         periodShort = "lastMonth";
     } 
     else if (quickLink.equals("lastWeek")) {
        startDate = startDate.withDayOfWeek(DateTimeConstants.MONDAY).minusWeeks(1).toDateMidnight().toDateTime(); 
        endDate = startDate.withDayOfWeek(DateTimeConstants.SUNDAY);
        period = "Last Week " + startDate.toString("MMMM dd yyyy") + " to " + endDate.toString("MMMM dd yyyy");
        periodShort = "lastWeek"; 
     }
     else if (quickLink.equals("thisMonth")) {
        startDate = startDate.dayOfMonth().withMinimumValue();
        period = "This Month " + startDate.toString("MMMM yyyy");
        periodShort = "thisMonth";
     }
     else if (quickLink.equals("thisWeek")) {
        startDate = startDate.withDayOfWeek(DateTimeConstants.MONDAY).toDateMidnight().toDateTime();  
        period = "This Week " + endDate.toString("MMMM dd yyyy");
        periodShort = "thisWeek";
     }
     else if (quickLink.equals("yesterday")) {
    	startDate = startDate.minusDays(1).toDateMidnight().toDateTime();    	
        endDate = endDate.toDateMidnight().toDateTime();
        period = "Yesterday " + startDate.toString("MMMM dd yyyy");
    	periodShort = "yesterday ";
     }
     else if (quickLink.equals("today")) {
    	startDate = startDate.minus(startDate.getMillisOfDay()); 
       	period = "Today " + startDate.toString("MMMM dd yyyy");  
       	periodShort = "today";
     }    
     reportList = paymentReportService.getFailedPaymentReportByDate(startDate.toDate(), endDate.toDate());     
  	 FailedPaymentHistory failedPaymentHistory = new FailedPaymentHistory(reportList);
  	 failedPaymentHistory.setCurrentPageNum(page);	
     model.addObject("reportList", reportList);
     model.addObject("failedPaymentHistory", failedPaymentHistory);
     model.addObject("period", period);
     model.addObject("periodShort", periodShort);
     return model.getSuccess();
  }
  
  /**
   * This method handles form input requests with user specified parameters
   * @param 
   * @return ModelAndView
   */
  @RequestMapping(value="/payment", method=RequestMethod.POST)
  public ModelAndView getFailedPaymentReport(@RequestParam(value="month_start", required=false) Integer monthStart,
		                                             @RequestParam(value="day_start", required=false) Integer dayStart, 
		                                             @RequestParam(value="year_start", required=false) Integer yearStart,
		                                             @RequestParam(value="month_end", required=false) Integer monthEnd, 
		                                             @RequestParam(value="day_end", required=false) Integer dayEnd,
		                                             @RequestParam(value="year_end", required=false) Integer yearEnd,
		                                             @RequestParam(value="userName", required=false) String userName,
		                                             @RequestParam(value="accountNo", required=false) Integer accountNo,
		                                             @RequestParam(value="failureCode", required=false) String failureCode){
	  ResultModel model = new ResultModel("admin/report/payment/report");
	  DateTime startDate = null;
	  DateTime endDate = null;
	  List<PaymentReport> reportList = null;	 
	  if(yearStart != null && monthStart != null && dayStart != null ) 
	     startDate = new DateTime(yearStart, monthStart, dayStart, 0, 0);
	  if(yearEnd != null && monthEnd != null && dayEnd != null )    
	     endDate = new DateTime(yearEnd, monthEnd, dayEnd, 23, 59);
	  if(startDate != null){
	     String period = "From " + startDate.toString("MM/dd/yyyy") + " to " + endDate.toString("MM/dd/yyyy");
	     reportList = paymentReportService.getFailedPaymentReportByDate(startDate.toDate(), endDate.toDate());
	     model.addObject("period", period);
	  }
	  else if(userName != null && userName.length() > 0){
		  reportList = paymentReportService.getFailedPaymentReportByUserName(userName);
      }
	  else if(accountNo != null && accountNo > 0){
		  reportList = paymentReportService.getFailedPaymentReportByAccountNo(accountNo);
	  }	 
	  else if(failureCode != null && failureCode.length() > 0){
		  reportList = paymentReportService.getFailedPaymentReportByFailureCode(failureCode);
      }
	  FailedPaymentHistory failedPaymentHistory = new FailedPaymentHistory(reportList);
	  failedPaymentHistory.setCurrentPageNum(1);
	  model.addObject("reportList", reportList);
	  model.addObject("failedPaymentHistory", failedPaymentHistory);	
	  //These parameters will be used when processing paged reports
	  model.addObject("month_start", monthStart);
	  model.addObject("day_start", dayStart);
	  model.addObject("year_start", yearStart);
	  model.addObject("month_end", monthEnd);
	  model.addObject("day_end", dayEnd);
	  model.addObject("year_end", yearEnd);
	  model.addObject("userName", userName);
	  model.addObject("accountNo", accountNo);
	  model.addObject("failureCode", failureCode);
	  	  
	  return model.getSuccess();	  
  }  
  
  /**
   * This handles form requests with user-specified date range. it also handles pagination
   * @param 
   * @return ModelAndView
   */  
  @RequestMapping(value="/payment/{page}", method=RequestMethod.POST)
  public ModelAndView getFailedPaymentReport(@RequestParam(value="month_start", required=false) Integer monthStart,
                                             @RequestParam(value="day_start", required=false) Integer dayStart, 
                                             @RequestParam(value="year_start", required=false) Integer yearStart,
                                             @RequestParam(value="month_end", required=false) Integer monthEnd, 
                                             @RequestParam(value="day_end", required=false) Integer dayEnd,
                                             @RequestParam(value="year_end", required=false) Integer yearEnd,
                                             @RequestParam(value="userName", required=false) String userName,
                                             @RequestParam(value="accountNo", required=false) Integer accountNo,
                                             @RequestParam(value="failureCode", required=false) String failureCode,
                                             @PathVariable("page") Integer page){
	  ResultModel model = new ResultModel("admin/report/payment/report");
	  DateTime startDate = null;
	  DateTime endDate = null;
	  List<PaymentReport> reportList = null;	 
	  if(yearStart != null && monthStart != null && dayStart != null ) 
	     startDate = new DateTime(yearStart, monthStart, dayStart, 0, 0);
	  if(yearEnd != null && monthEnd != null && dayEnd != null )    
	     endDate = new DateTime(yearEnd, monthEnd, dayEnd, 23, 59);
	  if(startDate != null){
	     String period = "From " + startDate.toString("MM/dd/yyyy") + " to " + endDate.toString("MM/dd/yyyy");
	     reportList = paymentReportService.getFailedPaymentReportByDate(startDate.toDate(), endDate.toDate());
	     model.addObject("period", period);
	  }
	  else if(userName != null && userName.length() > 0){
		  reportList = paymentReportService.getFailedPaymentReportByUserName(userName);
      }
	  else if(accountNo != null && accountNo > 0){
		  reportList = paymentReportService.getFailedPaymentReportByAccountNo(accountNo);
	  }    
	  else if(failureCode != null && failureCode.length() > 0){
		  reportList = paymentReportService.getFailedPaymentReportByFailureCode(failureCode);
      }
	  FailedPaymentHistory failedPaymentHistory = new FailedPaymentHistory(reportList);
	  failedPaymentHistory.setCurrentPageNum(page);
	  
	  model.addObject("month_start", monthStart);
	  model.addObject("day_start", dayStart);
	  model.addObject("year_start", yearStart);
	  model.addObject("month_end", monthEnd);
	  model.addObject("day_end", dayEnd);
	  model.addObject("year_end", yearEnd);
	  model.addObject("userName", userName);
	  model.addObject("accountNo", accountNo);	 
	  model.addObject("failureCode", failureCode);
	  model.addObject("reportList", reportList);
	  model.addObject("failedPaymentHistory", failedPaymentHistory);
	  return model.getSuccess();	
  }    
  
  /**
   * This will return the failed payment detail for the given transaction id
   * @param trans id
   * @return ModelAndView
   */
  @RequestMapping(value = "/payment/detail/{transId}", method = RequestMethod.GET)
  public ModelAndView getPaymentReportDetail(@PathVariable int transId) {
     ResultModel model = new ResultModel("admin/report/payment/detail");     
     PaymentReport report = null;
     report = paymentReportService.getPaymentReportByTransId(transId);
     userManager.setSessionUser(report.getUser());
     model.addObject("report", report);
     return model.getSuccess();
  }
  
  /**
   * This method handles output formats
   * @param 
   * @return ModelAndView
   */  
  @RequestMapping(value="/payment/viewtype/{type}", method=RequestMethod.GET)
  public ModelAndView getFailedPaymentReport(HttpServletRequest request, HttpServletResponse response,
		                                    @PathVariable String type){	 
	  List<PaymentReport> reportList = (List<PaymentReport>)request.getSession().getAttribute("reportList");
	  ModelAndView resultModel = null;	  
	  Map<String, List<PaymentReport>> reportListMap = new HashMap<String, List<PaymentReport>>();
	  reportListMap.put("reportList", reportList);
	  if("excel".equalsIgnoreCase(type))
    	  resultModel = new ModelAndView("ExcelPaymentReportView");	
	  resultModel.addObject(reportListMap);
	  return resultModel;	
  }      
  
  /**
   * The followings are utility methods
   */
  
  //@ModelAttribute("userNameList")
  private List<String> getUserNames(){
	List<String> userNameList = userManager.getAllUserNames();
    List<String> newUserNameList = new ArrayList<String>();
	User user = null;
	for(String userName : userNameList){
        if(userName != null && userName.length() > 1 && !userName.startsWith("reserve_"))
    	   newUserNameList.add(userName);	
    }
	return newUserNameList;
  }
  
  //@ModelAttribute("accountNumbers")
  private Collection<Integer> getAllAccountNumbers(){
	return paymentReportService.getAllAccountNumbers();	
  }
  
  private Collection<String> getAllFailureCodes(){
	return paymentReportService.getAllFailureCodes();	
	  }
}