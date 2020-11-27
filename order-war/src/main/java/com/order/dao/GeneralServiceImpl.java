package com.order.dao;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

public class GeneralServiceImpl extends HibernateDaoSupport implements GeneralService  {
	
	
	public void delete(Object obj) {
		getSessionFactory().getCurrentSession().setFlushMode(FlushMode.AUTO);
		getHibernateTemplate().delete(obj);
	}

	@SuppressWarnings("unchecked")
	public List find(String hql, Object[] values) {
		return getHibernateTemplate().find(hql, values);
	}

	@Transactional
	public void save(Object obj) {
//		getSessionFactory().getCurrentSession().setFlushMode(FlushMode.AUTO);
		getHibernateTemplate().save(obj);
	}

	@Transactional
	public void update(Object obj) {
		getSessionFactory().getCurrentSession().setFlushMode(FlushMode.AUTO);
		getHibernateTemplate().update(obj);
	}
	
	@SuppressWarnings("unchecked")
	public List findAll(Class cls) {
		return getHibernateTemplate().loadAll(cls);
	}
	
	@SuppressWarnings("unchecked")
	public Object findUniqueObject(String hql, Object[] values) {
		List list = find(hql, values);
		if (list.size() == 1) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Object get(Class cls, java.io.Serializable id) {
		Object obj =null;
//		getHibernateTemplate().get
//		Session session =  getSession();
		try{
			obj = getHibernateTemplate().get(cls, id);
//			session.close();
		}catch(Exception e){

		}
		return obj;
	}

	@SuppressWarnings("unchecked")
	public List find(String hql) {
		
		return getHibernateTemplate().find(hql);
	}
	
	@SuppressWarnings("unchecked")
	public Integer getRecordCount(final String hql,final Hashtable params) {
		return ((Long) getHibernateTemplate().execute((new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException
					 {
				Query query = s.createQuery(hql);
				Enumeration enumeration = params.keys();

				while (enumeration.hasMoreElements()) {
					String keys = (String) enumeration.nextElement();
					if(params.get(keys) instanceof Object[]){
						query.setParameterList(keys,(Object[])params.get(keys));
					}else{
						query.setParameter(keys, params.get(keys));
					}
				}
				return  query.list().get(0);
			}
		}))).intValue() ;
	}
	
	@SuppressWarnings("unchecked")
	public List queryPageList(final String hql,final Hashtable params,final int curPage,final int pageSize) {
//		return getHibernateTemplate().executeFind(new HibernateCallback() {
//			public Object doInHibernate(Session s) throws HibernateException,
//					SQLException {
//				Query query = s.createQuery(hql);
//				Enumeration enumeration = params.keys();
//
//				while (enumeration.hasMoreElements()) {
//					String keys = (String) enumeration.nextElement();
//					if(params.get(keys) instanceof Object[]){
//						query.setParameterList(keys,(Object[])params.get(keys));
//					}else{
//						query.setParameter(keys, params.get(keys));
//					}
//				}
//
//				query.setFirstResult((curPage-1)*pageSize);
//				query.setMaxResults(pageSize);
//
//				return query.list();
//			}
//		});

		return getHibernateTemplate().execute(
				new HibernateCallback<List>() {
					@Override
					public List doInHibernate(Session session) throws HibernateException {
										Query query = session.createQuery(hql);
				Enumeration enumeration = params.keys();

				while (enumeration.hasMoreElements()) {
					String keys = (String) enumeration.nextElement();
					if(params.get(keys) instanceof Object[]){
						query.setParameterList(keys,(Object[])params.get(keys));
					}else{
						query.setParameter(keys, params.get(keys));
					}
				}

				query.setFirstResult((curPage-1)*pageSize);
				query.setMaxResults(pageSize);

				return query.list();
					}
				}
		);

	}

	@SuppressWarnings("unchecked")
	public List find(final String hql, final Hashtable params) {
//		return getHibernateTemplate().executeFind(new HibernateCallback() {
//			public Object doInHibernate(Session s) throws HibernateException,
//					SQLException {
//				Query query = s.createQuery(hql);
//				Enumeration enumeration = params.keys();
//
//				while (enumeration.hasMoreElements()) {
//					String keys = (String) enumeration.nextElement();
//					if(params.get(keys) instanceof Object[]){
//						query.setParameterList(keys,(Object[])params.get(keys));
//					}else{
//						query.setParameter(keys, params.get(keys));
//					}
//				}
//				return  query.list();
//			}
//		});
		return getHibernateTemplate().execute(
				new HibernateCallback<List>() {
					@Override
					public List doInHibernate(Session session) throws HibernateException {
						Query query = session.createQuery(hql);
				Enumeration enumeration = params.keys();

				while (enumeration.hasMoreElements()) {
					String keys = (String) enumeration.nextElement();
					if(params.get(keys) instanceof Object[]){
						query.setParameterList(keys,(Object[])params.get(keys));
					}else{
						query.setParameter(keys, params.get(keys));
					}
				}
				return  query.list();
					}
				}
		);
	}
	
	@SuppressWarnings("unchecked")
	public Integer execute(final String hql,final Hashtable params) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback(){

			public Object doInHibernate(Session s) throws HibernateException {
				Query query = s.createQuery(hql);
				Enumeration enumeration = params.keys();

				while (enumeration.hasMoreElements()) {
					String keys = (String) enumeration.nextElement();
					if(params.get(keys) instanceof Object[]){
						query.setParameterList(keys,(Object[])params.get(keys));
					}else{
						query.setParameter(keys, params.get(keys));
					}
				}
				return new Integer(query.executeUpdate());
			}
			
		});

	}

	@SuppressWarnings("unchecked")
	public List executeQuery(final String queryName) {
		
		
		return (List) getHibernateTemplate().execute(new HibernateCallback(){

			public Object doInHibernate(Session session) throws HibernateException {
				
				return session.getNamedQuery(queryName).list();
			}
			
		});
	}


	

}
