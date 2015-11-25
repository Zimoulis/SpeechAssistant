package com.lifeaide.Speech;

/*
GETTERS AND SETTERS THIS CLASS IS USED SO THAT WE CAN STORE OBJECTS IN LIST SO WE CAN PASS FULL ORGANIZED QUERIES LATER 
MORE STURCTURE THAN PASSING CURSORS
* */

public class Words {
	
	private long id;
	private long refrence_id;
	private String comment;
	
		//GETS
	public long getId() {
		return id;
	}
	
	public long get_refrence_id() {
		return refrence_id;
	}
	
	public String getComment() {
		return comment;
	}
	
		//SETS
	public void set_refrence_id(long refrence_id) {
		this.refrence_id = refrence_id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return comment;
	}
}