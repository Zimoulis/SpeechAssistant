package com.lifeaide.Speech;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class initialize_database {

	private SQLiteDatabase database;

	/*
	 THIS CLASS CONTAINS ALL THE INSERTS NESSASARY TO POPULATE THE DATABASE
	 THESE VALUES ARE PRESET INFORMATION THAT LINKS UP WITH PICTURES.
	 THIS MEANS THAT WHEN THE PROGRAM IS FIRST INSTALLED EVERY USER WILL
	 HAVE THE SAME DEFAULT ENVIORMENT UNTIL THEY START MAKING MODIFICATIONS
	 THIS ALSO ALOWS THE DEVELOPER TO QUICKLY MAKE CHANGES OR ADD NEW ITEMS INTO THE 
	INITIAL DATA BASE 
	 * */
	
	
	
	public initialize_database(SQLiteDatabase database2) {
		database = database2;
	}


	public void initializeDB()
	{		
		
		String[] folderinserts = {"About Me",           //1
								  "Little Words",       //2
								  "Questions",          //3
								  "People",             //4
								  "Places",             //5
								  "Things",             //6
								  "Pronouns",           //7
								  "Verbs",              //8
								  "Emotions",           //9
								  "Describe",           //10 
								  "Household",          //11
								  "School",             //12
								  "Bathroom",           //13
								  "Clothes",            //14
								  "Food",               //15
								  "Electronics",        //16
								  "Health",             //17
								  "Colors",             //18
								  "Hanging Out",        //19
								  "Activities",         //20
								  "Phrases"};           //21
		
		String[] wordnameinserts = {"and", "a", "an", "the", "or", "to", "for", "are", "is", "am", "was", "up", "down", 
				                    "top", "bottom", "over", "under", "in", "on", "off", "all", "about", "at", "this", 
				                    "these", "those", "that", "because", "but", "so", "too", "of", "between", "as", "with", 
				                    "none", "some", "here", "there",   //39 Little Words Folder 2
				                    
									"Who", "What", "When", "Where", "How", "Can I", "Can you", "Can we", "Can it",
									"Which", "Would", "Would you", "Would it", "Should", "Should I", "Should we", "Should it", 
									"What time", "How come", "Why", "Why not",  //21 Questions Folder 3
									
									"mom", "dad", "family", "God", "friend", "friends", "teacher", "sister", "brother", "baby",
									"boy", "girl", "child", "children", "daughter", "son", "aide", "person", "police man", "firefighter", "librarian",
									"people", "grandma", "grandpa", "man", "woman", "aunt", "uncle", "cousin", "neighbor", "doctor",
									"husband", "wife", //33 People Folder 4
									
									"United States", "New York City", "home", "backyard", "forest", "school", "restaurant", "store", "church", "airport",
									"pool", "pharmacy", "grocery store", "mall", "hospital", "outside", "inside", "bank", "library", "post office",
									"beach", "movie theater", "park",  //23 Places Folder 5
									
				                    "money", "picture", "cat", "dog", "toys", "animal", "map", "sun", "movie", "book", "rain", "game", "phone", "clock", 
				                    "food", "trip", "car", "weather", "storm", "music", "candy", "flowers", "tree", "paper", "clothes",  //25 Things Folder 6
				                    
				                    "me", "my", "mine", "myself", "yours", "him", "her", "it", "us", "ours", "them", "your", "his",
				                    "hers", "its", "we", "our", "their", "I am", "I can", "I will", "he's", "she's", "it's", "we've", "they've",
				                    "I've", "you've", "you'll", "he'll", "she'll", "it'll", "we'll", "they'll", "I", "you", "he", "she", "they",  //39 Pronouns Folder 7
				                    
				                    "go", "have", "want", "like", "don't like", "bring", "buy", "close", "open", "come", "cook", "ask", "do",
				                    "drive", "fall", "feel", "fight", "find", "forget", "get", "give", "hate", "hear", "hide", "know", "learn",
				                    "let", "live", "look", "must", "need", "pull", "make", "care", "sit", "describe", "stop", "start", "explain", "take",
				                    "teach", "tell", "try", "use", "wait", "help", "listen", "wear", "win", "wish", "love", //51 Verbs Folder 8
				                    
				                    "I'm", "I feel", "happy", "angry", "sad", "lazy", "nervous", "sleepy", "scared", "shy", "depressed", "upset", "tired", "hungry",   //14 Emotions Folder 9
				                    
				                    "clean", "dirty", "beautiful", "ugly", "easy", "hard", "important", "scary", "big", "small", "noisy", "quiet", "slow", "fast",
				                    "nice", "mean", "hot", "cold", "sweet", "bitter", "good", "not good", "many", "few", //24 Describe Folder 10
				                    
				                    "kitchen", "living room", "dining room", "bedroom", "bathroom", "basment", "closet", "couch", "chair", "refrigerator", "table", "microwave",
				                    "oven", "bed", "desk", "spoon", "fork", "plate", "glass", "pillow", "blanket", "remote", "mirror", //23 Household Folder 11
				                    
				                    "principal", "student", "classmates", "cafeteria", "recess", "textbooks", "pencil", "calculator", "pen", 
				                    "ruler", "I learned", "I don't understand", "notebook", "homework", "test", "blackboard", "chalk", "I like", "I don't like", "question", "answer",
				                    "class", "gym", "math", "english", "history", "science", "art", "favorite",   //29 School Folder 12
				                    
				                    "bathtub", "sink", "toilet", "towel", "toothbrush", "toothpaste", "shampoo", "conditioner", "antiperspirant", "wash",
				                    "shower", "brush my teeth", "floss", "wash my hair", "wash my hands", "go to the bathroom", "tissue", "toilet paper",   //18 Bathroom Folder 13
				                    
				                    "t-shirt", "jeans", "sweater", "shirt", "pants", "skirt", "dress", "bathing-suit", "socks", "underwear", "shorts",
				                    "sneakers", "coat", "hat", "scarf", "gloves", "shoes", "flip-flops", "suit", "jacket", 
				                    "pajamas", "fit", "fits", "doesn't fit", "don't fit",  //25 Clothes Folder 14
				                    
				                    "I'd like", "to eat", "it tastes", "breakfast", "lunch", "dinner", "vegetables", "fruit", "chocolate", "snack", 
				                    "drink", "macaroni", "cheese", "cereal", "milk", "yogurt", "sandwhich", "ham", "pizza",
				                    "french fries", "cake", "apple", "rice", "banana", "strawberries", "chicken", "hamburger", 
				                    "salad", "juice", "water", "pancakes", "soup", "fish", "grapes", "eggs", "hot dog", "soda",
				                    "cookies", "bread", "toast", "salt", "pepper", "sugar", "tea", "coffee", "ketchup", "mustard", "butter",   //48 Food Folder 15
				                    
				                    "computer", "t.v.", "stereo", "mp3 player", "cd", "camera", "dvd player", "dvd", "cell phone", "video game",
				                    "laptop", "radio",   //12 Electronics Folder 16	
				                    
				                    "I have", "I feel sick", "hurts", "bandaid", "fever", "tooth", "head", "headache", "I am bleeding", "ear ache", "stomach ache",
				                    "stomach", "throat", "cough", "sneeze", "medicine", "tylenol", "flu", "emergency", "doctor's office", "allergies",  //21 Health Folder 17
				                    
				                    "blue", "red", "orange", "green", "purple", "pink", "brown", "black", "white", "yellow",  //10 Colors Folder 18
				                    
				                    "I want to hangout", "fun", "Let's", "party", "play a game", "have dinner", "talk", "visit", "go shopping", "travel", "go outside",
				                    "stay inside", "watch a movie", "go bike riding",    //14 Hanging Out Folder 19
				                    
				                    "dance", "swim", "jump", "jog", "walk", "change", "read", "sing", "sleep", "laugh", "play", "watch",
				                    "eat", "hug", "joke", "cry", "work", "shop", "dream", "exercise",  //20  Activities Folder 20
				                    
				                    "How are you?", "Are you ok?", "hi", "thank you", "please", "I want", "I need", "good luck", "I'm sorry", "bye",
				                    "see you later", "I think so", "I agree", "I disagree", "don't worry", "one moment please", "ok", "no"," "};  //18 Phrases
		
		
		int[] wordrefrenceinserts = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,
				                     3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,
				                     4,4,4,4,4,4,4,4,4,4,4,4,4,4,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,6,6,6,
				                     6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,
				                     7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,
				                     8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,9,9,9,9,9,9,9,9,9,9,9,
				                     9,9,9,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,11,
				                     11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12,12,12,12,12,12,
				                     12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,13,13,13,13,13,
				                     13,13,13,13,13,13,13,13,13,13,13,13,13,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,
				                     14,14,14,14,14,14,14,14,14,14,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,
				                     15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,
				                     15,16,16,16,16,16,16,16,16,16,16,16,16,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,
				                     17,17,17,17,17,18,18,18,18,18,18,18,18,18,18,19,19,19,19,19,19,19,19,19,19,19,19,19,19,
				                     20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,21,21,21,21,21,21,21,21,21,
				                     21,21,21,21,21,21,21,21,21,21};     
		
		// Space Inserts
		String[] spacenameinserts	= {"Health","Questions","Describe","Activities","Everyday","Folders","Favorites","Phrases","School","Household","Emotions","Eating","Other"};
		int    idval[]				= {425,2,4,426,143,427,432,436,437,438,442,444,17,176,211,2,181,220,    //space7 (Health)
									   41,45,46,59,57,40,3,182,181,223,224,186,4,2,24,2,149,7,               //space6 (Questions)
									   24,27,25,26,9,8,2,126,125,10,15,340,344,14,6,143,7,5,                 //space5 (Describe)
									   176,183,6,181,4,5,35,143,66,4,480,462,463,19,482,476,468,20, 		 //space4 (Activities)
									   1,176,4,6,10,2,2,183,182,211,217,196,201,181,8,62,4,9,                //space3 (Everyday)
									   1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18, 						 //Space2 (folders)
									   495,496,492,2,6,181,232,245,375,381,386,460,336,337,468,462,490,506,   //Space1 (Favorites)
									   490,491,492,493,494,495,496,497,498,499,500,501,502,503,504,505,506,507, //space8 (Phrases)
									   143,67,295,298,176,6,206,317,316,306,304,315,12,9,8,2,251,250,         //space9 (School)
									   4,9,2,270,273,274,247,246,10,414,413,143,7,11,181,183,332,13,           //space10 (Household)
									   232,233,234,235,240,244,238,236,9,59,44,9,8,2,177,178,179,7,            //space11 (Emotions)
									   365,366,37,1,2,368,369,370,7,310,311,183,378,393,390,381,402,15,     //space12 (Eating)
									   2,143,176,182,183,413,414,16,422,480,10,18,344,340,360,342,356,14};     //space13 (Other)
		
		String typevale[] 			= {"w","w","w","w","w","w","w","w","w","w","w","w","f","w","w","f","w","w",  //space7			 
									   "w","w","w","w","w","w","f","w","w","w","w","w","w","w","w","f","w","f",  //space6
									   "w","w","w","w","w","w","f","w","w","f","f","w","w","f","f","w","f","f",  //space5
									   "w","w","w","w","w","f","w","w","w","f","w","w","w","f","w","w","w","f",  //space4
		                               "f","w","w","w","w","w","f","w","w","w","w","w","w","w","f","w","f","f",   //space3
		                               "f","f","f","f","f","f","f","f","f","f","f","f","f","f","f","f","f","f",	 //space2
		                               "w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w",  //space1
		                               "w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w",  //space8
									   "w","w","w","w","w","w","w","w","w","w","w","w","f","w","w","f","w","w",    //space9
									   "w","w","f","w","w","w","w","w","f","w","w","w","f","f","w","w","w","f",    //space10
									   "w","w","w","w","w","w","w","w","f","w","w","w","w","f","w","w","w","f",    //space11
									   "w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","f",    //space12
									   "f","w","w","w","w","w","w","f","w","w","f","f","w","w","w","w","w","f"};   //space13
		                                
		
		
		
		 
		for(int i =0; i< folderinserts.length; i++)
			{
			createFolder(folderinserts[i]);
			}
		Log.v("Space value","Total number of Words" + wordrefrenceinserts.length);
		for(int i =0; i< wordnameinserts.length; i++)
			{
			createWord(wordnameinserts[i],wordrefrenceinserts[i]);
			Log.v("Space value",i+ " --Words-- " + wordnameinserts[i]);
			Log.v("Space value",i+ " --Folder id-- " + wordrefrenceinserts[i]);
			}
		for(int i =0; i< spacenameinserts.length; i++)
			{
			createSpace(spacenameinserts[i]);
			}
		
		int k = 0; //for total array values so  i= space j=position k= idval and typeval
		for(int j =1; j<=13; j++) //2 number of spaces // loops spaces
			{	
			for(int i =0; i< 18; i++)	//loops positions
				{
				Log.v("Space value", j+","+ (i+1) +","+ typevale[k]+","+idval[k]);
				CreateSpaceINFO(j,(i+1),typevale[k],idval[k]); //i+1 to db compatible no 0
				k++;
				}
			}
		
		
	}
	
	

	
	/*
	 * ALL THE BELOW METHODS CYCLE THROUGH THESE LOOPS AND PLACE THE VALUES INTO THE DATABASE
	 * 
	 * */
	private void createFolder(String foldername_text) 
		{
			ContentValues values = new ContentValues();
			values.put(MySQLiteHelper.FOLDER_NAME, foldername_text);
			database.insert(MySQLiteHelper.FOLDERS_TABLE, null,	values);	
		}

	private void createWord (String wordname_text, int folder_id) 
		{
			ContentValues values = new ContentValues();
			values.put(MySQLiteHelper.FOLDER_ID, folder_id);
			values.put(MySQLiteHelper.WORD_NAME, wordname_text);			
			database.insert(MySQLiteHelper.WORDS_TABLE, null,values);
		}
	
	private void createSpace (String spacename_text)  
		{
			ContentValues values = new ContentValues();
			values.put(MySQLiteHelper.SPACE_NAME, spacename_text);			
			database.insert(MySQLiteHelper.SPACES_TABLE, null,values);
		}
	private void CreateSpaceINFO (int s_id,int pos_id,String icon_type,int type_id) 
	{
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.SPACE_ID, s_id);			
		values.put(MySQLiteHelper.POSITION, pos_id);
		values.put(MySQLiteHelper.ICON_TYPE, icon_type);
		values.put(MySQLiteHelper.ID_VALUE, type_id);
		database.insert(MySQLiteHelper.SPACES_INFO_TABLE, null,values);
	}
	
	
	
	
}
