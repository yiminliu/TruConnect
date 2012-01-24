package com.trc.hibernate;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@Configuration
//@EnableTransactionManagement
public class PersistenceHibernateConfig {

  // @Bean
  // public AnnotationSessionFactoryBean alertsSessionFactory() {
  // AnnotationSessionFactoryBean sessionFactory = new
  // AnnotationSessionFactoryBean();
  // sessionFactory.setDataSource(this.restDataSource());
  // sessionFactory.setPackagesToScan(new String[] { "org.rest" });
  // sessionFactory.setHibernateProperties(this.hibernateProperties());
  // return sessionFactory;
  // }
  //
  // public static Object jndiLookup(String name) throws NamingException {
  // Object obj = null;
  // InitialContext jndiContext;
  // try {
  // jndiContext = new InitialContext();
  // obj = jndiContext.lookup(name);
  // } catch (NamingException e) {
  // System.err.println("Could not create JNDI API " + "context: " +
  // e.toString());
  // throw e;
  // }
  // return obj;
  // }
  //
  // @Bean
  // public DataSource restDataSource() {
  // try {
  // return (DataSource) jndiLookup("jdbc/TrcMySql");
  // } catch (NamingException e) {
  // e.printStackTrace();
  // return null;
  // }
  // }
  //
  // @Bean
  // public HibernateTransactionManager transactionManager() {
  // HibernateTransactionManager txManager = new HibernateTransactionManager();
  // txManager.setSessionFactory(this.alertsSessionFactory().getObject());
  // return txManager;
  // }
  //
  // @Bean
  // public PersistenceExceptionTranslationPostProcessor exceptionTranslation()
  // {
  // return new PersistenceExceptionTranslationPostProcessor();
  // }

}