package com.ycsys.smartmap.webgis.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.ycsys.smartmap.sys.common.config.Global;
import com.ycsys.smartmap.sys.common.result.ResponseEx;
import com.ycsys.smartmap.webgis.entity.BookMark;
import com.ycsys.smartmap.webgis.service.BookMarkService;

@Controller
@RequestMapping("/locateService")
public class MapLocateController {

	@Autowired
	private BookMarkService bookMarkService;
	
	@RequestMapping("/toAdd")
	public ModelAndView toAddBookmark() throws Exception{
		ModelAndView mv=new ModelAndView("main/map_add_sqdw");
		return mv;
	}
	
	@RequestMapping("/toEdit")
	public ModelAndView toEditBookmark(HttpServletRequest request,Integer id) throws Exception{
		ModelAndView mv=new ModelAndView("main/map_edit_sqdw");
		BookMark bookMark=bookMarkService.get(BookMark.class, id);
		mv.addObject("o", bookMark);
		return mv;
	}
	
	@RequestMapping("/toList")
	@ResponseBody
	public List<BookMark> list(HttpServletRequest request,BookMark bookMark) throws Exception{
		String hql="from BookMark where 1=1 ";
		if(!StringUtils.isEmpty(bookMark.getName())){
			hql=hql.concat("and name like '%"+bookMark.getName()+"%'");
		}
		List<BookMark> bookMarks= bookMarkService.find(hql);
		return bookMarks;
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value="/add")
	@ResponseBody
	public String addBookmark(HttpServletRequest request,BookMark bookMark){
		String result;
		ResponseEx res=new ResponseEx();
		try {
//			User user =(User)request.getSession().getAttribute(Global.SESSION_USER);
			String uString = request.getParameter(Global.SESSION_USER);
			bookMark.setCreateTime(new Date());
			bookMark.setCreator("admin");//临时
			bookMarkService.save(bookMark);
			res.setSuccess("保存成功");
		} catch (Exception e) {
			res.setFail(e.getMessage());
		}
		result=JSONObject.toJSONString(res);
		return result;
	}

	@ResponseBody
	@RequestMapping("/getBookMark")
	public List<BookMark> getBookMark(Integer bookID){
		Object[] p = { bookID };
		return bookMarkService.find("from BookMark t where t.id=?",p);
	}

	@ResponseBody
	@RequestMapping("/addBookMark")
	public ResponseEx addBookMark(String bookName,String bookDes,Double bookXMin,Double bookXMax,Double bookYMin,Double bookYMax,Double bookYaw,Double bookPitch,Double bookRoll){
		ResponseEx ex = new ResponseEx();
		try {
			Date currentTime = new Date();
			String currentUser = "admin";
			BookMark bookMark = new BookMark(currentTime,currentUser,bookName,bookDes,bookXMin,bookXMax,bookYMin,bookYMax,bookYaw,bookPitch,bookRoll);
			bookMarkService.save(bookMark);
			ex.setSuccess("添加书签成功！");
		}catch (Exception e){
			ex.setFail("添加书签失败！");
		}
		return ex;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public String  deleteBookmark(HttpServletRequest request,BookMark o) {
		String string;
		ResponseEx res=new ResponseEx();
		try {
			bookMarkService.delete(o);
			res.setSuccess("删除成功");
		} catch (Exception e) {
			res.setFail(e.getMessage());
		}
		string=JSONObject.toJSONString(res);
		return string;
	}

	@ResponseBody
	@RequestMapping("/deleteBookMark")
	public ResponseEx deleteBookMark(Integer bookID){
		ResponseEx ex = new ResponseEx();
		try {
			Object[] p = { bookID };
			List<BookMark> bookMarks = bookMarkService.find("from BookMark t where t.id=?",p);
			if(bookMarks.size()==1){
				bookMarkService.delete(bookMarks.get(0));
			}
			ex.setSuccess("删除书签成功！");
		}catch (Exception e){
			ex.setFail("删除书签失败！");
		}
		return ex;
	}
	
	@RequestMapping(value="/edit")
	@ResponseBody
	public String  editBookmark(HttpServletRequest request,BookMark o) {
		String string;
		ResponseEx res=new ResponseEx();
		try {
			bookMarkService.update(o);
			res.setSuccess("修改成功");
		} catch (Exception e) {
			res.setFail(e.getMessage());
		}
		string=JSONObject.toJSONString(res);
		return string;
	}

	@ResponseBody
	@RequestMapping("/editBookMark")
	public ResponseEx editBookMark(Integer bookID,String bookName,String bookDes,Double bookXMin,Double bookXMax,Double bookYMin,Double bookYMax,Double bookYaw,Double bookPitch,Double bookRoll){
		ResponseEx ex = new ResponseEx();
		try {
			Date currentTime = new Date();
			String currentUser = "admin";
			BookMark bookMark = new BookMark(currentTime,currentUser,bookName,bookDes,bookXMin,bookXMax,bookYMin,bookYMax,bookYaw,bookPitch,bookRoll);
			bookMark.setId(bookID);
			bookMarkService.update(bookMark);
			ex.setSuccess("编辑书签成功！");
		}catch (Exception e){
			ex.setFail("编辑书签失败！");
		}
		return ex;
	}
}
