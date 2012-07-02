package com.trc.service.report;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.trc.domain.support.report.payment.PaymentReport;

@Component
public class ExcelPaymentReportView extends AbstractExcelView {
	   
	 protected void buildExcelDocument(Map model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    List<PaymentReport> paymentReportList = (List)((Map)model.get("hashMap")).get("reportList");
		if(paymentReportList == null)
		   paymentReportList = (List<PaymentReport>)request.getSession().getAttribute("reportList");
		//DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		HSSFSheet sheet = workbook.createSheet("Failed Payment Report");
		HSSFRow header = sheet.createRow(0);		
		header.createCell(0).setCellValue("User Name");
		header.createCell(1).setCellValue("Account");
		header.createCell(2).setCellValue("Amount");
		header.createCell(3).setCellValue("Payment Source");
		header.createCell(4).setCellValue("Date and Time");
		header.createCell(5).setCellValue("Failure Code");
		int rowNum = 1;
		for (PaymentReport paymentReport : paymentReportList) {
		     HSSFRow row = sheet.createRow(rowNum++);
		     row.createCell(0).setCellValue(paymentReport.getUser() == null ? "": paymentReport.getUser().getUsername());
		   	 row.createCell(1).setCellValue(paymentReport.getPaymentTransaction().getAccountNo());
		     row.createCell(2).setCellValue(paymentReport.getPaymentTransaction().getPaymentAmount());
		     row.createCell(3).setCellValue(paymentReport.getPaymentTransaction().getPaymentMethod() + "-" + paymentReport.getPaymentTransaction().getPaymentSource());
		     //row.createCell(4).setCellValue(dateFormat.format(paymentReport.getPaymentTransaction().getPaymentTransDate()));
		     row.createCell(4).setCellValue(paymentReport.getPaymentTransaction().getPaymentTransDate().toString());	    	 
		     row.createCell(5).setCellValue(paymentReport.getPaymentTransaction().getPaymentUnitMessage());
	    }		
	 }		
}
