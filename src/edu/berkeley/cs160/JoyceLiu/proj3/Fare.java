package edu.berkeley.cs160.JoyceLiu.proj3;

import java.text.DecimalFormat;
import java.util.Collections;

import java.util.HashMap;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.AutoCompleteTextView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;


import android.widget.TextView;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import android.view.View.OnClickListener;


public class Fare extends Activity implements View.OnClickListener {
	
	private AutoCompleteTextView origStat;
    private AutoCompleteTextView destStat;
    private Button calcFareButton;
    private String origName;
    private String destName;
    private String finalFare;
    private TextView fare; 
    private TextView roundFare;
	private HashMap<String, String> map = MainActivity.getHM();
	
    private String orig;
    private String dest;

	//private ViewGroup group = (ViewGroup)findViewById(R.id.fareLayout);

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.fare);        
        
		origStat =  (AutoCompleteTextView) findViewById(R.id.orig_box);
		
		destStat =  (AutoCompleteTextView) findViewById(R.id.dest_box);
        fare = (TextView) findViewById(R.id.calculated_fare);
        roundFare = (TextView) findViewById(R.id.roundFare);
        calcFareButton = (Button) findViewById(R.id.calcFareButt);
        calcFareButton.setOnClickListener(this);
        
        String[] stations = getResources().getStringArray(R.array.station_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, stations);
        
        AutoCompleteTextView origBox = (AutoCompleteTextView) findViewById(R.id.orig_box);
        origBox.setAdapter(adapter);
        
        AutoCompleteTextView destBox = (AutoCompleteTextView) findViewById(R.id.dest_box);
        destBox.setAdapter(adapter);
        
        
        }
    
	
	public void onClick(View view) {
		origName = origStat.getText().toString();
		destName = destStat.getText().toString();
	    switch (view.getId()) {
	    case R.id.calcFareButt:
			if ((origName != null) &&  (destName != null)) {
		        orig = map.get(origName);
		        dest = map.get(destName);
				FareCheckTask task = new FareCheckTask();
		        task.execute(orig, dest);
				}
			break;
	    case R.id.calc_again:
	    	Intent myIntent2 = new Intent(view.getContext(), Fare.class); 
            startActivity(myIntent2);
			break;
	    }
	}
	
    //called when an async task returns successfully. 
    private void updateFare(Document doc) {
    	
    	XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			try {
				//create xpath expressions to pull out the data we want from the XML response from the BART API
				XPathExpression myFare = xpath.compile("/root/trip/fare");
				String s = myFare.evaluate(doc);
				fare.setText(s);
				Double d = Double.parseDouble(s);
				Double d1 = (2 * d);
				roundFare.setText(String.valueOf(roundTwoDecimalPlaces(d1)));
            
			} catch (Exception ex) {
				ex.printStackTrace();
			}
    	
    }
    
	public double roundTwoDecimalPlaces(double d) {
        DecimalFormat twoPlaces = new DecimalFormat("##.##");
    return Double.valueOf(twoPlaces.format(d));
	}
	
	private class FareCheckTask extends AsyncTask<String, Void, String> {

    	private final String API_KEY = "2GK2-2JEH-69J5-KWUL";
    	//private final String endPoint = "http://api.wunderground.com/api/" + API_KEY + "/conditions/q/CA/";
        private final String baseFareXML = "http://api.bart.gov/api/sched.aspx?cmd=fare&key="+API_KEY;
    	private Document xmlDocument;


		public String doInBackground(String... stats)
        {
           if (stats != null) {
        	   		
        	   		//replace spaces in url with "_"
        		   String url = baseFareXML+"&orig="+orig+"&dest="+dest;
        		   
        		   HttpClient httpclient = new DefaultHttpClient();
        	       HttpResponse response;
        	       String responseString = null;
        	       
        	       
        	       try {
        	            response = httpclient.execute(new HttpGet(url));
        	            StatusLine statusLine = response.getStatusLine();
        	            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
        	                ByteArrayOutputStream out = new ByteArrayOutputStream();
        	                response.getEntity().writeTo(out);
        	                out.close();
        	                responseString = out.toString();
        	            } else{
        	                //Closes the connection.
        	                response.getEntity().getContent().close();
        	                throw new IOException(statusLine.getReasonPhrase());
        	            }
        	        } catch (ClientProtocolException e) {
        	            //TODO Handle problems..
        	        } catch (IOException e) {
        	            //TODO Handle problems..
        	        }
                    // for testing.
        	        return responseString;

           }
           else {       
        	   throw new IllegalArgumentException("Stations not selected.");
           }
        }
        
        @Override
        protected void onPostExecute(String results)
        {       
            if(results != null)
            {   
            	try {
    	        	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    	 		   	DocumentBuilder builder = factory.newDocumentBuilder();
    	 		   	InputSource is = new InputSource(new StringReader(results));
    	 	        xmlDocument = builder.parse(is); 
    	 	        if (xmlDocument != null) {
						updateFare(xmlDocument);
					}
            	}
            	catch (Exception ex) {
            		//do something
            	}
            }
        }
        
    }

	
	
	public String getOrigName() {
		return origName;
	}
	
	public String getDestName() {
		return destName;
	}
	
	
}