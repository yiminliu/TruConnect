package com.trc.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.trc.domain.support.knowledgebase.Article;
import com.trc.domain.support.knowledgebase.Category;
import com.trc.exception.management.SupportManagementException;
import com.trc.manager.SupportManager;
import com.trc.web.model.ResultModel;

@Controller
@RequestMapping("/support")
public class SupportController {
  @Autowired
  private SupportManager supportManager;
 
  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView showSupport() {
	  List<Article> articleList = null;
	  ResultModel resultModel = new ResultModel("support/support");
	  try{
	      articleList = supportManager.getAllArticles();
	  }
	  catch(SupportManagementException te){
		   return resultModel.getAccessException();
	  }	    
	  resultModel.addObject("articleList", articleList);
	  return resultModel.getSuccess();
  }
  
  /**
   * This method is used to retrieve all categories
   * 
   * @return String
   */
  @RequestMapping(value = "/showAllCategories", method = RequestMethod.GET)
  public ModelAndView getAllCategories() {
	  List<Category> categoryList = null;
	  ResultModel resultModel = new ResultModel("support/showAllCategories");
	  try{
		  categoryList = supportManager.getAllCategories();
	  }
	  catch(SupportManagementException te){
		   return resultModel.getAccessException();
	  }	
	  resultModel.addObject("categoryList", categoryList);
	  return resultModel.getSuccess();
  }

  /**
   * This method is used to retrieve an article by using category
   * 
   * @return String
   */
  @RequestMapping(value = "/showArticlesByCategory/{categoryId}", method = RequestMethod.GET)
  public ModelAndView getArticlesByCategory(@PathVariable int categoryId, Model model) {
	  List<Article> articleList = null;
	  ResultModel resultModel = new ResultModel("support/showArticles");
	  try{
	     Category category = supportManager.getCategoryById(categoryId);
	     articleList = supportManager.getArticlesByCategory(categoryId);
	     resultModel.addObject("category", category);
	     resultModel.addObject("articleList", articleList);
	  }
	  catch(SupportManagementException te){
		  return resultModel.getAccessException();
	  }	   
	  return resultModel.getSuccess();
  }
  
  /**
   * This method is used to retrieve an article by using article id
   * 
   * @return String
   */
  @RequestMapping(value = "/showArticle/{articleId}", method = RequestMethod.GET)
  public ModelAndView getArticleById(@PathVariable int articleId, Model model) {	  
	  List<Article> articleList = new ArrayList<Article>();
	  ResultModel resultModel = new ResultModel("support/showArticles");
	  try{
		  Article article = supportManager.getArticleById(articleId);
		  articleList.add(article);
	  }
	  catch(SupportManagementException te){
		   return resultModel.getAccessException();
	  }	
	  resultModel.addObject("articleList", articleList);
	  return resultModel.getSuccess();   
  }
  
  /**
   * This method is used to show the form to search articles
   * 
   * @return String
   */
  @RequestMapping(value="/search", method=RequestMethod.GET)
  public String searchArticles(Model model, @RequestParam(value="keyword", required=false) String keyword){	
	model.addAttribute("article", new Article()); 	
	return "support/search";
  }	
  
  /**
   * This method is used to retrieve articles
   * 
   * @return ModelAndView
   */
   @RequestMapping(value="/search", method=RequestMethod.POST)
   public ModelAndView processSearchArticles(@RequestParam(value="keyword", required=true) String keyword){
	  ResultModel resultModel = new ResultModel("support/showArticles");
	  List<Article> articleList = new ArrayList<Article>();	
	  if(keyword != null && !keyword.equals("")) {
	    try {	
		   articleList = supportManager.searchArticlesByKeyword(keyword);
        }
	    catch(SupportManagementException te){
	 	   return resultModel.getAccessException();
		}						
	 	 resultModel.addObject("articleList", articleList); 		
	     return resultModel.getSuccess();
	  }
	  else
		 throw new IllegalArgumentException("No keyword specified.");  
   }
   
   /**
	   * This method is used to insert an article
	   * 
	   * @return ModelAndView
	   */
	   @RequestMapping(value="/insertArticle", method=RequestMethod.POST)
	   public ModelAndView processInsertArticle(@ModelAttribute("article") Article article){
	      ResultModel resultModel = new ResultModel("support/showAllCategories");
		  try {	
		      int articleId = supportManager.insertArticle(article);
	      }
		  catch(SupportManagementException te){
			   return resultModel.getAccessException();
		  }						
		  return resultModel.getSuccess();
	   }
	   
   
	/**
	   * This method is used to insert an article
	   * 
	   * @return ModelAndView
	   */
	   @RequestMapping(value="/insertArticle", method=RequestMethod.GET)
	   public String insertArticle(Model model){
		   model.addAttribute("article", new Article());  		
		  return "/support/insertArticle";
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
		  try {	
		       supportManager.createCategory(category);
	      }
		  catch(SupportManagementException te){
		     return resultModel.getAccessException();
		  }					   
		  return resultModel.getSuccess();
		}  
  
  /**
   * The followings are utility methods
   */
    @ModelAttribute("categoryList")
	public List getCategories() {
    	List categoryList = null;
    	try{
    		categoryList = supportManager.getAllCategories();
    	}
    	catch(SupportManagementException te){
	       te.printStackTrace();
    	}
    	// Collections.sort(categoryList, new CategoryIdComparator());
    	return categoryList;    	   
	}    
}