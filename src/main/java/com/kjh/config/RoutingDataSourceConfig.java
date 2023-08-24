package com.kjh.config;

import com.kjh.vo.CodeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import javax.sql.DataSource;
import java.util.HashMap;

public class RoutingDataSourceConfig {
	private static final Logger log = LoggerFactory.getLogger(ReplicationRoutingDataSource.class);

	@Bean(name = "routingDataSource")
	public DataSource routingDataSource(
		@Qualifier("masterDataSource") final DataSource masterDataSource
		, @Qualifier("slaveDataSource") final DataSource slaveDataSource) {
		ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();
		HashMap<Object, Object> dataSourceMap = new HashMap<>();
		dataSourceMap.put(CodeVO.MASTER, masterDataSource);
		dataSourceMap.put(CodeVO.SLAVE, slaveDataSource);

		routingDataSource.setTargetDataSources(dataSourceMap);
		routingDataSource.setDefaultTargetDataSource(masterDataSource);

		return routingDataSource;
	}

	@Bean(name = "dataSource")
	public DataSource dataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {
		log.info("dataSource : " + routingDataSource);
		return new LazyConnectionDataSourceProxy(routingDataSource);
	}

}
