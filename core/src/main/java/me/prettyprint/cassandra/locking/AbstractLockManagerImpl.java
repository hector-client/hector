package me.prettyprint.cassandra.locking;

import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.locking.HLockManager;
import me.prettyprint.hector.api.locking.HLockManagerConfigurator;

public abstract class AbstractLockManagerImpl implements HLockManager {

	protected Cluster cluster;
	protected Keyspace keyspace;
	protected HLockManagerConfigurator lockManagerConfigurator;

	public AbstractLockManagerImpl(Cluster cluster, Keyspace keyspace, HLockManagerConfigurator lockManagerConfigurator) {
		if (cluster == null) throw new RuntimeException("Cluster cannot be null for LockManager");
		
		this.cluster = cluster;
		
		if (lockManagerConfigurator == null) {
			this.lockManagerConfigurator = new HLockManagerConfigurator();
		}
		
		if (keyspace == null) {
			this.keyspace = HFactory.createKeyspace(lockManagerConfigurator.getKeyspaceName(), cluster);
		}

	}

	public AbstractLockManagerImpl(Cluster cluster) {
		this(cluster, null, null);
	}

	public AbstractLockManagerImpl(Cluster cluster, Keyspace keyspace) {
		this(cluster, keyspace, null);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public Cluster getCluster() {
		return cluster;
	}

	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}

	@Override
	public Keyspace getKeyspace() {
		return keyspace;
	}

	public void setKeyspace(Keyspace keyspace) {
		this.keyspace = keyspace;
	}

	@Override
	public HLockManagerConfigurator getLockManagerConfigurator() {
		return lockManagerConfigurator;
	}

	public void setLockManagerConfigurator(HLockManagerConfigurator lockManagerConfigurator) {
		this.lockManagerConfigurator = lockManagerConfigurator;
	}

}
