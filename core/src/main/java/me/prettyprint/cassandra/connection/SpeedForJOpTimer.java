package me.prettyprint.cassandra.connection;

import com.ecyrd.speed4j.StopWatch;
import com.ecyrd.speed4j.StopWatchFactory;
import com.ecyrd.speed4j.log.PeriodicalLog;

public class SpeedForJOpTimer implements HOpTimer {

	private final StopWatchFactory stopWatchFactory;
	
	public SpeedForJOpTimer(String clusterName) {
	    //
	    //  This sets up the Speed4J logging system.  Alternatively, we could
	    //  use the speed4j.properties -file.  This was chosen just so that
	    //  it wouldn't confuse anyone and would work pretty much the same
	    //  way as what the old hector config does.
	    //
	    PeriodicalLog slog = new PeriodicalLog();
	    slog.setName("hector-"+clusterName);
	    slog.setPeriod(60); // 60 seconds
	    slog.setSlf4jLogname( "me.prettyprint.cassandra.hector.TimingLogger" );
	    
	    stopWatchFactory = StopWatchFactory.getInstance( slog );
	}
	
	@Override
	public Object start() {
		return stopWatchFactory.getStopWatch();
	}

	@Override
	public void stop(Object token, String tagName, boolean success) {
		((StopWatch) token).stop(tagName.concat(success ? ".success_" : ".fail_"));
	}
	
}
