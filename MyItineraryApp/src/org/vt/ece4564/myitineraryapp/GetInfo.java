package org.vt.ece4564.myitineraryapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class GetInfo extends Fragment{
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";
	public static String server_ = "";

	private Button submit;
	private TextView update;
	private static String activity_ = "none";
	private String url_final = "";
	private RadioButton rEat;
	private RadioButton rPlay;
	private RadioButton rSleep;
	
	Runnable get = new Runnable() {
		@Override
		public void run() {
			try{
				final HttpTaskGet newtask = new HttpTaskGet(GetInfo.this);
				newtask.execute(url_final);
			}
			catch(Exception e) {
				e.printStackTrace();
				update.setText(e.getMessage());
			}
			
		}
	};
	
	private Thread t1 = new Thread(get, "get");
	
	public GetInfo(){
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.get_info, container, false);
		
		//TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
		url_final = getArguments().getString(server_);
		
		
		submit = (Button) rootView.findViewById(R.id.get_submit);
		update = (TextView) rootView.findViewById(R.id.get_update);
		rEat = (RadioButton) rootView.findViewById(R.id.get_eat);
		rPlay = (RadioButton) rootView.findViewById(R.id.get_play);
		rSleep = (RadioButton) rootView.findViewById(R.id.get_sleep);
		
		submit.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View view) {
		    	try {
					if(activity_ == "none") update.setText("you must select an activity!");
					else if (url_final == "") update.setText("you must set a server!");
					else{
						url_final = url_final + "/" + activity_;	
						
						update.setText("Getting Information");
						
						if((t1.getState() == Thread.State.NEW)){
							t1.start();
						}else{
							t1.run();
						}
					}
				}
				catch(Exception e) {
					e.printStackTrace();
					update.setText(e.getMessage());
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
		update.setText(result);
	}
}