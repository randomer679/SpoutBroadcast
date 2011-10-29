package me.randomer679.SpoutBroadcast.schedules;

import me.randomer679.SpoutBroadcast.SpoutFeatures;

public class BroadcastSchedule implements Runnable{

	private SpoutFeatures spoutFeatures = new SpoutFeatures();

	@Override
	public void run() {
		spoutFeatures.scheduledBroadcasts();
	}

}
