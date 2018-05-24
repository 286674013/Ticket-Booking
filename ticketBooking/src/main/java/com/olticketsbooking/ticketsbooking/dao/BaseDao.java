package com.olticketsbooking.ticketsbooking.dao;

import com.olticketsbooking.ticketsbooking.config.MessageInfo;
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;


@Repository
public class BaseDao {
    @Resource
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }


    public MessageInfo save(Object entity) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            session.save(entity);
            tx.commit();
            session.clear();
            return new MessageInfo(true,"保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
            return  new MessageInfo(false,"数据库保存错误");
        } finally {
            session.close();
        }
    }


    public MessageInfo update(Object entity) {
        Session session = getSession();
        try {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
            session.clear();
            return new MessageInfo(true,"更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            if (session!=null) {
                session.getTransaction().rollback();
            }
            return  new MessageInfo(false,"数据库更新错误");
        } finally {
            session.close();
        }

    }

    public MessageInfo delete(Object entity) {
        Session session = getSession();
        try {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
            session.clear();
            return new MessageInfo(true,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            if (session!=null) {
                session.getTransaction().rollback();
            }
            return new MessageInfo(false,"删除异常");
        } finally {
            session.close();
        }
    }

    public MessageInfo deleteById(Class<?> className, int id) {
        Session session = getSession();
        try {
            session.beginTransaction();
            Object instance = session.get(className, id);
            session.delete(instance);
            session.getTransaction().commit();
            session.clear();
            return new MessageInfo(true,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            if (session!=null) {
                session.getTransaction().rollback();
            }
            return new MessageInfo(false,"删除异常");
        } finally {
            session.close();
        }
    }


    public MessageInfo getPageAll(Class<?> className, int up, int size) {
        List<?> list = null;
        Session session = getSession();
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(className);
            criteria.addOrder(Order.desc("id"));
            criteria.setFirstResult(up);
            criteria.setMaxResults(size);
            list = criteria.list();
            session.getTransaction().commit();
            session.clear();
            return new MessageInfo(true,list,"数据获取成功(分页)");
        } catch (Exception e) {
            e.printStackTrace();
            if (session!=null) {
                session.getTransaction().rollback();
            }
            return new MessageInfo(false,"数据获取异常(分页)");
        } finally {
            session.close();
        }
    }

    public MessageInfo getAll(Class<?> className) {
        List<?> list = null;
        Session session = getSession();
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(className);
            criteria.addOrder(Order.desc("id"));
            list = criteria.list();
            session.getTransaction().commit();
            session.clear();
           return new MessageInfo(true,list,"数据获取成功(分页)");
        } catch (Exception e) {
            e.printStackTrace();
            if (session!=null) {
                session.getTransaction().rollback();
            }
            return new MessageInfo(false,"数据获取异常(分页)");
        } finally {
            session.close();
        }
    }

    public MessageInfo findById(Class<?> className, int id) {
        Session session = getSession();
        try {
            Object instance = session.get(className, id);
          return   new MessageInfo(true,instance,"数据获取成功");
        } catch (Exception re) {
            re.printStackTrace();
           return new MessageInfo(false,"数据获取失败");
        } finally {
            session.close();
        }
    }



    public MessageInfo findByProperty(Class<?> className, String propertyName,
                                      Object value) {

        Session session = getSession();
        try {
            String queryString = "from " + className.getSimpleName()
                    + " as model where model." + propertyName + "= ?";
            Query queryObject = session.createQuery(queryString);
            queryObject.setParameter(0, value);
            List<?> list = queryObject.list();
            return   new MessageInfo(true,list,"数据获取成功");
        } catch (Exception re) {
            re.printStackTrace();
            return   new MessageInfo(false,"数据获取失败");
        } finally {
            session.close();
        }
    }

    public MessageInfo findByPropertySingle(Class<?> className, String propertyName,
                                            Object value) {

        Session session = getSession();
        try {
            String queryString = "from " + className.getSimpleName()
                    + " as model where model." + propertyName + "= ?";
            Query queryObject = session.createQuery(queryString);
            queryObject.setParameter(0, value);
            List<?> list = queryObject.list();
//            System.out.println("result:"+list.toString());
            if(list.size()>0) {
                return new MessageInfo(true, list.get(0), "数据获取成功");
            }else {
                return   new MessageInfo(false,"数据获取失败,没有该数据");
            }
        } catch (Exception re) {
            re.printStackTrace();
            return   new MessageInfo(false,"数据获取失败");
        } finally {
            session.close();
        }
    }

    public MessageInfo execSqlQuery(String sql) {
        Session session = getSession();
        try {
            session.beginTransaction();
            List<Object[]> objects = session.createSQLQuery(sql).list();
            session.getTransaction().commit();
            session.clear();
            return   new MessageInfo(true,objects,"SQL执行成功");
        } catch (Exception re) {
            re.printStackTrace();
            if (session!=null) {
                session.getTransaction().rollback();
            }
            return   new MessageInfo(true,"SQL执行失败");
        } finally {
            session.close();
        }
    }


}
