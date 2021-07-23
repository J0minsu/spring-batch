package kr.lifesemantics.test.windows.config;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

	// DataSource를 호출하기 위해
	@Autowired
	private DataSourceConfig dataSourceConfig;

	// Hibernate 옵션
	private final static String DIALECT = "hibernate.dialect";
	private final static String SHOW_SQL = "hibernate.show_sql";
	private final static String HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
	private final static String PACKAGES_TO_SCAN = "entitymanager.packagesToScan";

//	@Bean
//	public LocalSessionFactoryBean sessionFactory() {
//
//		DataSource dataSource = dataSourceConfig.getDataSource();
//
//		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
//		sessionFactoryBean.setDataSource(dataSource);
//		sessionFactoryBean.setPackagesToScan(PACKAGES_TO_SCAN);
//
//		// Hibernate의 설정값을 저장하기 위한 프로퍼티
//		Properties hibernateProperties = new Properties();
//		hibernateProperties.put(DIALECT, "org.hibernate.dialect.MySQL8Dialect");
//		hibernateProperties.put(SHOW_SQL, "true");
//		hibernateProperties.put(HBM2DDL_AUTO, "update");
//
//		sessionFactoryBean.setHibernateProperties(hibernateProperties);
//
//		return sessionFactoryBean;
//	}

	@Bean
	public FactoryBean<EntityManagerFactory> entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean containerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		
			containerEntityManagerFactoryBean.setDataSource(dataSourceConfig.getDataSource());
			JpaVendorAdapter adaptor = new HibernateJpaVendorAdapter();
			containerEntityManagerFactoryBean.setJpaVendorAdapter(adaptor);
			containerEntityManagerFactoryBean.setPackagesToScan("kr.lifesemantics.test.windows");
		
		Properties props = new Properties();
			props.setProperty(DIALECT, "org.hibernate.dialect.MySQL8Dialect");
			props.setProperty(SHOW_SQL, "true");
			props.setProperty(HBM2DDL_AUTO, "update");
			
			containerEntityManagerFactoryBean.setJpaProperties(props);
			
		return containerEntityManagerFactoryBean;
	}

//	@Bean
//	public HibernateTransactionManager transactionManager() {
//		
//		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
//		transactionManager.setSessionFactory(sessionFactory().getObject());
//		
//		return transactionManager;
//		
//	}

	@Bean
	public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
		return entityManagerFactory.createEntityManager();
	}

//	@Bean
//	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
//		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
//		jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
//		return jpaTransactionManager;
//	}

}
