package me.randomer679.SpoutBroadcast.schedules;

import me.randomer679.SpoutBroadcast.SpoutBroadcast;
import me.randomer679.SpoutBroadcast.SpoutFeatures;

public class BroadcastSchedule implements Runnable{

	private SpoutFeatures spoutFeatures;

	public BroadcastSchedule(SpoutBroadcast spoutBroadcast) {
		this.spoutFeatures = spoutBroadcast.spoutFeatures;
	}

	@Override
	public void run() {
		spoutFeatures.scheduledBroadcasts();
	}

}
