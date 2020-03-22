

package com.asc.web.ctl;




import java.util.List;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.RequestMapping;

import com.asc.dao.IUserDAO;

import com.asc.dao.User;

 

//����ע�ⷽʽ,���ü̳��κ������ʵ���κνӿ�

@Controller

public class HelloController{

//      private String viewName;

      private IUserDAO userDao;

      

//��orderWeb-servlet.xml�����õ�,�������Ѿ���ע��userDAOProxy�ӿ���

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

      

//ע����urlӳ��

      @SuppressWarnings("unchecked")

      @RequestMapping("/hello.do") 

      public String index(ModelMap map,HttpServletRequest request,HttpServletResponse response) {

           // map����������View�����ݵ�

           map.put("a1", "Spring�����");

           request.getSession().setAttribute("a2", "����Session");

           List<User> list2 = userDao.selectUser();

           map.put("a3", list2);

           return "hello";  //�����Ա�ע��ֵhello��,������Ⱦ��ͼhello.jsp

      }
      
      @SuppressWarnings("unchecked")

      @RequestMapping("/hello2.do") 

      public String index2(ModelMap map,HttpServletRequest request,HttpServletResponse response) {
    	  /**
           // map����������View�����ݵ�

           map.put("a1", "Spring�����");

           request.getSession().setAttribute("a2", "����Session");

           List<User> list2 = userDao.selectUser();

           map.put("a3", list2);
			**/
           return "test";  //�����Ա�ע��ֵhello��,������Ⱦ��ͼhello.jsp

      }
      
}


