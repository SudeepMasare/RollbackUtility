package com.bpsheet.rollback.configuration;

import java.io.File;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
/**
 * 
 * @author SudeepMasare
 * 
 * */
public class HibernateConfiguration {

	private static final SessionFactory sessionFactory;
	static {
		try {

			/**
			 * 
			 * Following commented block is the production related configuration
			 * 
			 */

			File config = new File("./config/hibernate.cfg.xml");
			StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure(config).build();
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

			/*
			 * Configuration configuration = new Configuration(); sessionFactory =
			 * configuration.configure().buildSessionFactory();
			 */

		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
