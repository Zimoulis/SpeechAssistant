package com.lifeaide.Speech;

/*
GETTERS AND SETTERS THIS CLASS IS USED SO THAT WE CAN STORE OBJECTS IN LIST SO WE CAN PASS FULL ORGANIZED QUERIES LATER 
MORE STURCTURE THAN PASSING CURSORS
* */
public class Spaces {
	
	private long s_id;
	private long position;
	private String icon_value;
	private long type_value;
	
	
	//Getters
	public long getSpaceId() {
		return s_id;
	}
	public long getPosition() {
		return position;
	}
	public String geticon_value() {
		return icon_value;
	}
	public long gettype_value() {
		return type_value;
	}
	
	//Setters
		public void set_SpaceId(long SpaceId) {
			this.s_id = SpaceId;
		}
		
		public void set_position(long position) {
			this.position = position;
		}
		
		public void set_iconvalue(String value) {
			this.icon_value = value;
		}
		
		
		public void set_type_value(long intger_value) {
			this.type_value = intger_value;
		}
	

	public String toString() {
		return icon_value;
	}
}