package org.vt.ece4564.myitineraryapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SetServer extends Fragment{
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";
	public static String url_final = "";
	
	private Button submit;
	private EditText url;
	private EditText port;
	private TextView url_out;

	public SetServer(){
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.set_server, container, false);
		
		TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
		dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));

		submit = (Button) rootView.findViewById(R.id.set_submit);
		url = (EditText) rootView.findViewById(R.id.url);
		port = (EditText) rootView.findViewById(R.id.port);
		url_out = (TextView) rootView.findViewById(R.id.url_out);
		
		submit.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View view) {
		    	try {
					if(url.getText().toString().length() == 0) url_out.setText("you must enter a server!");
					else if (port.getText().toString().length() == 0) url_out.setText("you must enter a port!");
					else{
						url_final = url.getText().toString() + ":" + port.getText().toString();	
						
						//getActivity().se
						
						url_out.setText("Your Server Is: " + url_final);
					}
				}
				catch(Exception e) {
					e.printStackTrace();
					url_out.setText(e.getMessage());
				}
		    }
		});
		
		return rootView;
	}
}