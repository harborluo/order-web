package com.order.dao;

import java.util.Hashtable;
import java.util.List;

public interface GeneralService {
	/**
	 * insert one record to table
	 * @param obj
	 */
	public abstract void save(Object obj);
	/**
	 * delete one record
	 * @param obj
	 */
	public abstract void delete(Object obj);
	/**
	 * update one record
	 * @param obj
	 */
	public abstract void update(Object obj);
	/**
	 * search data by condiction
	 * @param hql
	 * @param values
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public abstract List find(String hql, Object[] values);
	/**
	 * search data
	 * @param hql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public abstract List find(String hql, Hashtable params);
	/**
	 * 
	 * @param cls
	 * @return
	 */			
	@SuppressWarnings("unchecked")
	public abstract List findAll(Class cls);	
	/**
	 * get one record by primary key
	 * @param cls
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object get(Class cls, java.io.Serializable id);
	/**
	 * search data without condiction
	 * @param hql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public abstract List find(String hql);
	
	/**
	 * get data by page
	 * @param hql
	 * @param params
	 * @param curPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List queryPageList(String hql, Hashtable params, int curPage, int pageSize);
	/**
	 * get recourd count with condiciton
	 * @param hql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Integer getRecordCount(String hql, Hashtable params);
	/**
	 * excute hql ,return effected recourds.
	 * @param hql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Integer execute(String hql, Hashtable params);
	
	@SuppressWarnings("unchecked")
	public List executeQuery(String queryName);	
	
	
}
