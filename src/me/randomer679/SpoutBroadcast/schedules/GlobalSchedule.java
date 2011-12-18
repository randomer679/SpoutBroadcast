package me.randomer679.SpoutBroadcast.schedules;

import me.randomer679.SpoutBroadcast.SpoutFeatures;

public class GlobalSchedule implements Runnable{

	private SpoutFeatures spoutFeatures;
	
	public GlobalSchedule(SpoutFeatures spoutFeatures) {
		this.spoutFeatures = spoutFeatures;
	}

	@Override
	public void run() {
		spoutFeatures.global = false;
	}

}
