package com.trc.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import com.trc.user.User;
import com.trc.user.authority.Authority;
import com.trc.user.security.SecurityQuestion;
import com.trc.util.logger.activation.ActivationMap;
import com.trc.util.logger.activation.ActivationState;
import com.trc.util.logger.activation.ActivationStateId;

public class HibernateUtil {

	private static SessionFactory sessionFactory;

	public static Configuration getInitializedConfiguration() {
		Configuration config = new Configuration();
		config.addAnnotatedClass(ActivationMap.class);
		config.addAnnotatedClass(ActivationState.class);
		config.addAnnotatedClass(ActivationStateId.class);
		config.addAnnotatedClass(User.class);
		config.addAnnotatedClass(Authority.class);
		config.addAnnotatedClass(SecurityQuestion.class);
		config.configure();
		return config;
	}

	public static Session getSession() {
		if (sessionFactory == null) {
			Configuration config = HibernateUtil.getInitializedConfiguration();
			sessionFactory = config.buildSessionFactory();
		}
		Session hibernateSession = sessionFactory.getCurrentSession();
		return hibernateSession;
	}

	public static void closeSession() {
		HibernateUtil.getSession().close();
	}

	public static void recreateDatabase() {
		Configuration config;
		config = HibernateUtil.getInitializedConfiguration();
		new SchemaExport(config).create(true, true);
	}

	public static Session beginTransaction() {
		Session hibernateSession;
		hibernateSession = HibernateUtil.getSession();
		hibernateSession.beginTransaction();
		return hibernateSession;
	}

	public static void commitTransaction() {
		HibernateUtil.getSession().getTransaction().commit();
	}

	public static void rollbackTransaction() {
		HibernateUtil.getSession().getTransaction().rollback();
	}

	public static void main(String args[]) {
		HibernateUtil.recreateDatabase();
	}

}