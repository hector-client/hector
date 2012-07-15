package me.prettyprint.cassandra.locking;

import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.locking.HLock;
import me.prettyprint.hector.api.locking.HLockManagerConfigurator;

public class HLockManagerImpl extends AbstractLockManager {
	
	public HLockManagerImpl(Cluster cluster, Keyspace keyspace) {
		super(cluster, keyspace);
	}
	
	public HLockManagerImpl(Cluster cluster, Keyspace keyspace, HLockManagerConfigurator lockManagerConfigurator) {
		super(cluster, keyspace, lockManagerConfigurator);
	}
	
	public HLockManagerImpl(Cluster cluster) {
		super(cluster);
	}

	@Override
	public void acquire(HLock lock) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void release(HLock lock) {
		// TODO Auto-generated method stub
		
	}

}
