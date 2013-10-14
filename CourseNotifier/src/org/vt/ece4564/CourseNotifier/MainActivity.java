package org.vt.ece4564.CourseNotifier;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends Activity {
	private Button start;
	private Button stop;
	private EditText crn;
	private TextView warnings;
	private TextView attempts;
	private String termsel = "none";
	private int a_ctr = 0; //attempts counter
	private boolean started = false;
	private int t_ctr = 0; //thread counter
	private String t_out = ""; //thread output
	private String crn_used;
	
	Runnable post1 = new Runnable() {
		@Override
		public void run() {
			try{
				final HttpTask newtask = new HttpTask(MainActivity.this);
				newtask.execute(termsel, crn_used, "");
			}
			catch(Exception e) {
				e.printStackTrace();
				warnings.setText(e.getMessage());
			}
			
		}
	};
		
	Runnable post2 = new Runnable() {
		@Override
		public void run() {
			try{
				final HttpTask newtask = new HttpTask(MainActivity.this);
				newtask.execute(termsel, crn_used, "on");
			}
			catch(Exception e) {
				e.printStackTrace();
				warnings.setText(e.getMessage());
			}
		}
	};
	
	private Thread t1 = new Thread(post1, "first post");
	private Thread t2 = new Thread(post2, "second post");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		start = (Button)findViewById(R.id.start);
		stop = (Button)findViewById(R.id.stop);
		crn = (EditText)findViewById(R.id.crn);
		warnings = (TextView)findViewById(R.id.warnings);
		attempts = (TextView)findViewById(R.id.attempts);
		
		start.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View view) {
		    	try {
					if(started == true) warnings.setText("Stop recurring event / Wait for last thread to finish!");
					else if (termsel.equals("none")) warnings.setText("You must select a term!");
					else if ((crn.getText().toString().length() > 5) | (crn.getText().toString().length() < 3)) warnings.setText("CRN needs to be between 3 to 5 numbers");
					else{
						//String url = "https://banweb.banner.vt.edu/ssb/prod/HZSKVTSC.P_DispRequest?" + termsel;	
						a_ctr = 0;
						started = true;
						crn_used = crn.getText().toString();
						
						warnings.setText("Looking up CRN: " + crn_used);
						attempts.setText(" ");
						
						if((t1.getState() == Thread.State.NEW) & (t2.getState() == Thread.State.NEW)){
							t1.start();
							t2.start();
						}else{
							t1.run();
							t2.run();
						}
					}
				}
				catch(Exception e) {
					e.printStackTrace();
					warnings.setText(e.getMessage());
				}
		    }
		});
		
		stop.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View view) {
		    	try {
		    		warnings.setText("Thread Attempts Stopped: Please wait for last attempt to finish running");
		    		started = false;
				}
				catch(Exception e) {
					e.printStackTrace();
					warnings.setText(e.getMessage());
				}
		    }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
		
	public void onTermClick(View view) {
	    boolean checked = ((RadioButton)view).isChecked();
		
	    // term selection & set termsel
	    switch(view.getId()) {
	        case R.id.fall:
	            if (checked)
	                termsel = "201309";
	            break;
	        case R.id.winter:
	            if (checked)
	            	termsel = "201312";
	            break;
	    }
	}

	public void onError(Exception e) {
		// TODO Auto-generated method stub
		e.printStackTrace();
		warnings.setText(e.getMessage());
	}
	public void last(String result){
		if (t_ctr == 0){
			t_out = result;
			t_ctr++;
		}else if (t_ctr == 1){
			t_ctr = 0;
			//compare post results
			if (result.contains("SEATS AVAILABLE!") & t_out.contains("SEATS AVAILABLE!")){
				warnings.setText(t_out);
				a_ctr = 0;
				started = false;
			}else if(result.contains("SEATS AVAILABLE!") | t_out.contains("SEATS AVAILABLE!")){
				a_ctr++;
				//retry until seats available or stop pressed
				if (started == true){
					warnings.setText("CLASS SECTION FULL. RETRYING");
					attempts.setText("Attempts: " + a_ctr);
					
					t1.run();
					t2.run();
				}else{
					warnings.setText("CLASS SECTION FULL. Stopped Attempts");
					attempts.setText("Total Attempts: " + a_ctr);
		    		a_ctr = 0;
				}
			}else{
				warnings.setText(t_out + " Please Try Again");
				started = false;
			}					
		}
	}
}
