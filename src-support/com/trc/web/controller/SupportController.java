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
import com.trc.domain.support.knowledgebase.Article;
import com.trc.domain.support.knowledgebase.Category;
import com.trc.domain.support.report.payment.FailedPaymentHistory;
import com.trc.domain.support.report.payment.PaymentReport;
import com.trc.domain.ticket.Ticket;
import com.trc.exception.management.SupportManagementException;
import com.trc.manager.ActivationReportManager;
import com.trc.manager.SupportManager;
import com.trc.manager.UserManager;
import com.trc.report.ActivationReport;
import com.trc.report.UserActivationReport;
import com.trc.service.report.PaymentReportService;
import com.trc.user.User;
import com.trc.util.logger.DevLogger;
import com.trc.util.logger.activation.ActivationState;
import com.trc.web.model.ResultModel;

@Controller
@RequestMapping("/support")
public class SupportController {
  @Autowired
  private SupportManager supportManager;
      

  //@RequestMapping(value = "/knowledgebase", method = RequestMethod.GET)
  @RequestMapping(method = RequestMethod.GET)
  public String showQuestionAndAnswers(Model model) {
	  List<Article> articleList = supportManager.getAllArticles();
	  model.addAttribute("articleList", articleList);
      return "support/support";
  }
  
  @RequestMapping(value = "/showAllCategories", method = RequestMethod.GET)
  public String getAllCategories(Model model) {
	  List<Category> categoryList = supportManager.getAllCategories();
	  model.addAttribute("categoryList", categoryList);
      return "support/showAllCategories";
  }

  @RequestMapping(value = "/showArticlesByCategory/{categoryId}", method = RequestMethod.GET)
  public String getArticlesByCategory(@PathVariable int categoryId, Model model) {
	  Category category = supportManager.getCategoryById(categoryId);
	  List<Article> articleList = supportManager.getArticlesByCategory(categoryId);
	  model.addAttribute("category", category);
	  model.addAttribute("articleList", articleList);
      return "support/showArticles";
  }
  
  @RequestMapping(value = "/showArticle/{articleId}", method = RequestMethod.GET)
  public String getArticle(@PathVariable int articleId, Model model) {
	  Article article = supportManager.getArticleById(articleId);
	  List<Article> articleList = new ArrayList<Article>();
	  articleList.add(article);
	  model.addAttribute("articleList", articleList);
      return "support/showArticles";
  }
  
  /**
   * This method is used to show the form to search tickets
   * 
   * @return ModelAndView
   */
  @RequestMapping(value="/search", method=RequestMethod.GET)
  public String searchArticles(Model model, @RequestParam(value="keyword", required=false) String keyword){	
	model.addAttribute("article", new Article()); 	
	return "support/search";
  }	
  
  /**
   * This method is used to show the form to show the searched tickets
   * 
   * @return ModelAndView
   */
   @RequestMapping(value="/search", method=RequestMethod.POST)
   public ModelAndView processSearchArticles(@RequestParam(value="keyword", required=true) String keyword){
	  ResultModel resultModel = new ResultModel("support/showArticles");
	  List<Article> articleList = new ArrayList<Article>();	
	  if(keyword != null && !keyword.equals("")) {
	   //try {	
		   articleList = supportManager.searchArticlesByKeyword(keyword);
       //}
	   //catch(SupportManagementException te){
	 ///	   return resultModel.getAccessException();
		//}						
	 	 resultModel.addObject("articleList", articleList); 		
	  }	
	  return resultModel.getSuccess();
   }
   
	/**
	   * This method is used to insert an article
	   * 
	   * @return ModelAndView
	   */
	   @RequestMapping(value="/insertArticle", method=RequestMethod.POST)
	   public ModelAndView processInsertArticle(@ModelAttribute("article") Article article){
	      ResultModel resultModel = new ResultModel("support/showAllCategories");
		  //try {	
		  int articleId = supportManager.insertArticle(article);
	    //}
		 //catch(SupportManagementException te){
		///	   return resultModel.getAccessException();
			//}						
			//resultModel.addObject("articleList", articleList); 		
				   
		  return resultModel.getSuccess();
	   }
	   
	   /**
		 * This method is used to insert a category
		 * 
		 * @return ModelAndView
		 */
	   @RequestMapping(value="/createCategory", method=RequestMethod.POST)
	   public ModelAndView processInsertCategory(@RequestParam(value="categoryName", required=true) String categoryName){
	      ResultModel resultModel = new ResultModel("support/showAllCategories");
	      Category category = new Category();
	      category.setTitle(categoryName);
		  //try {	
		  int articleId = supportManager.createCategory(category);
	      //}
		  //catch(SupportManagementException te){
		 ///	   return resultModel.getAccessException();
		 //}						
		//resultModel.addObject("articleList", articleList); 		
					   
		  return resultModel.getSuccess();
		}
  
  
  
  /**
   * The followings are utility methods
   */
    
}