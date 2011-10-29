package me.randomer679.SpoutBroadcast.extra;

public class Errors {

	private String error;

	public String error(int i) {
		switch(i){
		case 0: error = "Not Enough Arguments";
				break;
		case 1: error = "You do not have permission to do that.";
				break;
		case 2:
		}
		return error;
	}

}
