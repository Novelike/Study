package com.kjh.config;

import com.kjh.vo.CodeVO;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@MapperScan(basePackages = "com.kjh.**.mapper")
@EnableTransactionManagement
@PropertySource("classpath:/application.properties")
public class DatabaseConfig {
	private static final Logger log = LoggerFactory.getLogger(DatabaseConfig.class);

	@Primary
	@Bean(name = "masterDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.master.hikari")
	public HikariDataSource masterDataSource() {

		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}

	@Bean(name = "slaveDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.slave.hikari")
	public DataSource slaveDataSource() {

		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}

	@Bean(name = "routingDataSource")
	public DataSource routingDataSource(@Qualifier("masterDataSource") DataSource masterDataSource,
		@Qualifier("slaveDataSource") DataSource slaveDataSource) {

		ReplicationRoutingDataSource replicationRoutingDataSource = new ReplicationRoutingDataSource();

		Map<Object, Object> dataSourceMap = new LinkedHashMap<>();
		dataSourceMap.put(CodeVO.MASTER, masterDataSource);
		dataSourceMap.put(CodeVO.SLAVE, slaveDataSource);

		replicationRoutingDataSource.setTargetDataSources(dataSourceMap);
		replicationRoutingDataSource.setDefaultTargetDataSource(masterDataSource);
		return replicationRoutingDataSource;
	}

	@Bean(name = "dataSource")
	public DataSource dataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {

		return new LazyConnectionDataSourceProxy(routingDataSource);
	}

	@Bean
	@Primary
	public PlatformTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Primary
	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource,
		ApplicationContext applicationContext) throws Exception {

		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setTypeAliasesPackage("com.messagesend.**.dto, com.messagesend.**.vo"); // 쿼리 xml parameterType 에 dto 패키지 경로 지정
		sqlSessionFactoryBean
			.setMapperLocations(applicationContext.getResources("classpath*:com/messagesend/**/mapper/*.xml"));
		sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
		return sqlSessionFactoryBean.getObject();
	}

	@Primary
	@Bean(name = "sqlSessionTemplate")
	public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory)
		throws Exception {

		return new SqlSessionTemplate(sqlSessionFactory);
	}

}

