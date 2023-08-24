package com.kjh.config;

import com.kjh.vo.CodeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class ReplicationRoutingDataSource extends AbstractRoutingDataSource {
	private static final Logger log = LoggerFactory.getLogger(ReplicationRoutingDataSource.class);

	@Override
	protected Object determineCurrentLookupKey() {
		return TransactionSynchronizationManager
			.isCurrentTransactionReadOnly() ? CodeVO.SLAVE : CodeVO.MASTER;
	}
}
