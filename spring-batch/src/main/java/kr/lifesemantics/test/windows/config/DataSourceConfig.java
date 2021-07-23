package kr.lifesemantics.test.windows.config;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
public class DataSourceConfig {
	
	private final static String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
	private final static String URL = "jdbc:mysql://127.0.0.1:3306/Test?useSSL=false&serverTimezone=Asia/Seoul";
	private final static String USERNAME = "root";
	private final static String PASSWORD = "25351124";
	
	@Bean
	public DataSource getDataSource() {
		
		DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
		
		dataSourceBuilder.driverClassName(DRIVER_CLASS_NAME)
						 .url(URL)
						 .username(USERNAME)
						 .password(PASSWORD);
		
		return dataSourceBuilder.build();
	}
	
	

}
