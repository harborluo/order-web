

package com.asc.web.ctl;




import java.util.List;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.RequestMapping;

import com.asc.dao.IUserDAO;

import com.asc.dao.User;

 

//还是注解方式,不用继承任何类或者实现任何接口

@Controller

public class HelloController{

//      private String viewName;

      private IUserDAO userDao;

      

//在orderWeb-servlet.xml里配置的,该属性已经被注入userDAOProxy接口了

      public IUserDAO getUserDao() {

           return userDao;

      }

      public void setUserDao(IUserDAO userDao) {

           this.userDao = userDao;

      }

//      public String getViewName() {
//
//           return viewName;
//
//      }
//
//      public void setViewName(String viewName) {
//
//           this.viewName = viewName;
//
//      }

      

//注解其url映射

      @SuppressWarnings("unchecked")

      @RequestMapping("/hello.do") 

      public String index(ModelMap map,HttpServletRequest request,HttpServletResponse response) {

           // map是用来设置View层数据的

           map.put("a1", "Spring真好用");

           request.getSession().setAttribute("a2", "测试Session");

           List<User> list2 = userDao.selectUser();

           map.put("a3", list2);

           return "hello";  //该属性被注入值hello了,就是渲染视图hello.jsp

      }
      
      @SuppressWarnings("unchecked")

      @RequestMapping("/hello2.do") 

      public String index2(ModelMap map,HttpServletRequest request,HttpServletResponse response) {
    	  /**
           // map是用来设置View层数据的

           map.put("a1", "Spring真好用");

           request.getSession().setAttribute("a2", "测试Session");

           List<User> list2 = userDao.selectUser();

           map.put("a3", list2);
			**/
           return "test";  //该属性被注入值hello了,就是渲染视图hello.jsp

      }
      
}


