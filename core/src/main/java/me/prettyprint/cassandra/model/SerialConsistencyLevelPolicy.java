package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.service.OperationType;
import me.prettyprint.hector.api.ConsistencyLevelPolicy;
import me.prettyprint.hector.api.HConsistencyLevel;

/**
 * A simple implementation of {@link ConsistencyLevelPolicy} which returns Serial as the desired
 * consistency level for CAS writes.
 *
 * @author Peter Lin
 *
 */
public final class SerialConsistencyLevelPolicy implements ConsistencyLevelPolicy {

	@Override
	public HConsistencyLevel get(OperationType op) {
		return HConsistencyLevel.SERIAL;
	}

	@Override
	public HConsistencyLevel get(OperationType op, String cfName) {
		return HConsistencyLevel.SERIAL;
	}

}
