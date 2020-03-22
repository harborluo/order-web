

package com.asc.dao;

import java.util.List;

 

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

 

 

public class UserDAO extends HibernateDaoSupport implements IUserDAO{

      //插入数据

      public void insertUser(User user) {

           getHibernateTemplate().saveOrUpdate(user);

      }

      

//查询数据

      @SuppressWarnings("unchecked")

      public List<User> selectUser(){

           String sql="From User Order By id";

           List<User> findByNamedQuery = getHibernateTemplate().find(sql);

           return findByNamedQuery;

      }

}
