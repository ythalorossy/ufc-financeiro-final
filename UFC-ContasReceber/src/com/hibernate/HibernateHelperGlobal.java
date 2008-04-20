package com.hibernate;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import br.com.entities.UsuarioEntity;

import com.Auxiliar.Clientes;
import com.Auxiliar.Divisao;
import com.Auxiliar.Laboratorio;



/**
 * @generated
 */
public class HibernateHelperGlobal {
	/**
	 * @generated
	 */
	private static HibernateHelperGlobal singleton = new HibernateHelperGlobal();
	/**
	 * @generated
	 */
	private org.hibernate.SessionFactory factory;
	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	private ThreadLocal currentSession = new ThreadLocal();

	/**
	 * @generated
	 */
	private HibernateHelperGlobal() throws org.hibernate.HibernateException {
	}

	/**
	 * @generated
	 */
	public static void main(String[] args) throws Exception {
		String sqlFile = null;
		if (args.length > 0) {
			sqlFile = args[0];
		}
		boolean print = (sqlFile == null);
		boolean export = (sqlFile == null);
		AnnotationConfiguration config = getInstance().getConfiguration();
		SchemaExport exporter = new SchemaExport(config);
		if (sqlFile != null) {
			exporter.setOutputFile(sqlFile);
		}
		exporter.create(print, export);
	}

	/**
	 * @generated
	 */
	public static HibernateHelperGlobal getInstance() {
		return singleton;
	}

	/**
	 * @generated
	 */
	public synchronized org.hibernate.SessionFactory getFactory()
			throws org.hibernate.HibernateException {
		if (factory == null) {
			AnnotationConfiguration config = getConfiguration();
			factory = config.configure("hibernateGlobal.cfg.xml").buildSessionFactory();
		}
		return factory;
	}

	/**
	 * @generated
	 */
	public synchronized void close() throws org.hibernate.HibernateException {
		closeSession();
		if (factory != null) {
			factory.close();
			factory = null;
		}
	}

	/**
	 * @generated
	 */
	public AnnotationConfiguration getConfiguration()
			throws org.hibernate.MappingException {
		AnnotationConfiguration config = new AnnotationConfiguration();
		config.configure("hibernateGlobal.cfg.xml");
		config.addAnnotatedClass(Clientes.class);
		config.addAnnotatedClass(Divisao.class);
		config.addAnnotatedClass(Laboratorio.class);
		config.addAnnotatedClass(UsuarioEntity.class);
		return config;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("deprecation")
	public org.hibernate.Session openSession()
			throws org.hibernate.HibernateException {
		org.hibernate.Session session = getFactory().openSession();
		return session;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public org.hibernate.Session getSession()
			throws org.hibernate.HibernateException {
		org.hibernate.Session session = (org.hibernate.Session) currentSession
				.get();
		if (session == null || !session.isOpen()) {
			session = openSession();
			currentSession.set(session);
		}
		return session;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public void closeSession() throws org.hibernate.HibernateException {
		org.hibernate.Session session = (org.hibernate.Session) currentSession
				.get();
		if (session != null && session.isOpen()) {
			session.close();
		}
		currentSession.set(null);
	}

	/**
	 * @generated
	 */
	public void save(Object object) throws org.hibernate.HibernateException {
		getSession().save(object);
	}

	/**
	 * @generated
	 */
	public void delete(Object object) throws org.hibernate.HibernateException {
		getSession().delete(object);
	}

	/**
	 * @generated
	 */
	public String toString() {
		return "HibernateHelper";
	}
}