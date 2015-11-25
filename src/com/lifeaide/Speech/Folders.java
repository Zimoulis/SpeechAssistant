package com.lifeaide.Speech;

/*
 GETTERS AND SETTERS THIS CLASS IS USED SO THAT WE CAN STORE OBJECTS IN LIST SO WE CAN PASS FULL ORGANIZED QUERIES LATER 
 MORE STURCTURE THAN PASSING CURSORS
 * */


public class Folders {
	
	private long id;
	private String comment;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
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