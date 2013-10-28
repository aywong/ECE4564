package org.vt.ece4564.myitineraryapp;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;

public class PostInfo extends Fragment{
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";
	public static String server_ = "";

	private Button submit;
	private EditText comments;
	private RatingBar rating;
	private TextView warnings;
	private String url_final = "";
	private static String activity_ = "none";
	private RadioButton rEat;
	private RadioButton rPlay;
	private RadioButton rSleep;
	
	private String rated;
	private String commented;
	
	public PostInfo(){

	}
	
	Runnable post = new Runnable() {
		@Override
		public void run() {
			try{
				final HttpTaskPost newtask = new HttpTaskPost(PostInfo.this);
				SensorActivity sense = new SensorActivity();
				float temp;
				float light;
				
				temp = sense.getTemp();
				light = sense.getLight();
				
				newtask.execute(url_final, rated, commented, "loc", Float.toString(temp), Float.toString(light));
			}
			catch(Exception e) {
				e.printStackTrace();
				warnings.setText(e.getMessage());
			}
			
		}
	};
	
	private Thread t1 = new Thread(post, "post");
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//		((LocationManager) getSystemService(Context.LOCATION_SERVICE))
//		.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
//				this);
		
		
		View rootView = inflater.inflate(R.layout.post_info, container, false);
		
		//TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
		//dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
		
		url_final = getArguments().getString(server_);
		
		submit = (Button) rootView.findViewById(R.id.post_submit);
		comments = (EditText) rootView.findViewById(R.id.comments);
		rating = (RatingBar) rootView.findViewById(R.id.rating);
		warnings = (TextView) rootView.findViewById(R.id.post_warnings);
		rEat = (RadioButton) rootView.findViewById(R.id.post_eat);
		rPlay = (RadioButton) rootView.findViewById(R.id.post_play);
		rSleep = (RadioButton) rootView.findViewById(R.id.post_sleep);
		
		
		
		submit.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View view) {
		    	try {
					if(activity_ == "none") warnings.setText("you must select an activity!");
					else if (comments.getText().toString().length() == 0) warnings.setText("you must enter a comment!");
					else if (url_final == "") warnings.setText("you must set a server!");
					else{
						url_final = url_final + "/" + activity_;
						rated = Float.toString(rating.getRating());
						commented = comments.getText().toString();
					
						warnings.setText("Posting Information");
						
						if((t1.getState() == Thread.State.NEW)){
							t1.start();
						}else{
							t1.run();
						}
					}
				}
				catch(Exception e) {
					e.printStackTrace();
					warnings.setText(e.getMessage());
				}
		    }
		});
		
		rEat.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View view) {
		    	activity_ = "eat";
		    }
		});
		rPlay.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View view) {
		    	activity_ = "play";
		    }
		});
		rSleep.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View view) {
		    	activity_ = "sleep";
		    }
		});
		
		return rootView;
	}
	
	public void last(String result){
		warnings.setText(result);
	}
	
}