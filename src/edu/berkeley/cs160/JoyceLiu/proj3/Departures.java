
package edu.berkeley.cs160.JoyceLiu.proj3;

import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import java.io.StringReader;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xmlpull.v1.*;

import android.content.res.ColorStateList;
import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;	
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import android.util.Log;
import android.util.Xml;


public class Departures extends Activity implements LocationListener, View.OnClickListener {

	LocationManager manager;
	double currentLat;
	double currentLon;
	TextView myLat;
	TextView myLon;
	TextView theStat;
    Button getLocButton;
    Node node;
    NodeList nodes;
	String curStat;
	double curStatLat;
	double curStatLon;
	double curDist;
	public static String nearestStat;
	double shortestDist;
	ArrayList<Ent> entries = new ArrayList<Ent>();
	TextView dest1;
	TextView dest2;
	TextView dest3;
	TextView dest4;
	TextView dest5;
	TextView eta1;
	TextView eta2;
	TextView eta3;
	TextView eta4;
	TextView eta5;
	Node currentNode;
	int childSize;
	Ent ent;

	
	static final HashMap<String, double[]> coordMap = new HashMap<String, double[]>(){
		{
			put("12th", new double[] {37.803664,-122.271604});
			put("16th", new double[] {37.765062,-122.419694});
			put("19th", new double[] {37.80787,-122.269029});
			put("24th", new double[] {37.752254,-122.418466});
			put("ashb", new double[] {37.853024,-122.26978});
			put("balb", new double[] {37.72198087,-122.4474142});
			put("bayf", new double[] {37.697185,-122.126871});
			put("cast", new double[] {37.690754,-122.075567});
			put("civc", new double[] {37.779528,-122.413756});
			put("cols", new double[] {37.754006,-122.197273});
			put("colm", new double[] {37.684638,-122.466233});
			put("conc", new double[] {37.973737,-122.029095});
			put("daly", new double[] {37.70612055,-122.4690807});
			put("dbrk", new double[] {37.869867,-122.268045});
			put("dubl", new double[] {37.701695,-121.900367});
			put("deln", new double[] {37.925655,-122.317269});
			put("plza", new double[] {37.9030588,-122.2992715});
			put("embr", new double[] {37.792976,-122.396742});
			put("frmt", new double[] {37.557355,-121.9764});
			put("ftvl", new double[] {37.774963,-122.224274});
			put("glen", new double[] {37.732921,-122.434092});
			put("hayw", new double[] {37.670399,-122.087967});
			put("lafy", new double[] {37.893394,-122.123801});
			put("lake", new double[] {37.797484,-122.265609});
			put("mcar", new double[] {37.828415,-122.267227});
			put("mlbr", new double[] {37.599787,-122.38666});
			put("mont", new double[] {37.789256,-122.401407});
			put("nbrk", new double[] {37.87404,-122.283451});
			put("ncon", new double[] {38.003275,-122.024597});
			put("orin", new double[] {37.87836087,-122.1837911});
			put("pitt", new double[] {38.018914,-121.945154});
			put("phil", new double[] {37.928403,-122.056013});
			put("powl", new double[] {37.784991,-122.406857});
			put("rich", new double[] {37.936887,-122.353165});
			put("rock", new double[] {37.844601,-122.251793});
			put("sbrn", new double[] {37.637753,-122.416038});
			put("sfia", new double[] {37.6159,-122.392534});
			put("sanl", new double[] {37.72261921,-122.1613112});
			put("shay", new double[] {37.63479954,-122.0575506});
			put("ssan", new double[] {37.664174,-122.444116});
			put("ucty", new double[] {37.591208,-122.017867});
			put("wcrk", new double[] {37.905628,-122.067423});
			put("wdub", new double[] {37.699759,-121.928099});
			put("woak", new double[] {37.80467476,-122.2945822});
		}
	};
	
	static final HashMap<String, String> abbrMap = new HashMap<String, String>(){
		{
			put("12th", "12th St. Oakland City Center");
			put("16th", "16th St. Mission (SF)");
			put("19th", "19th St. Oakland");
			put("24th", "24th St. Mission (SF)");
			put("ashb", "Ashby (Berkeley)");
			put("balb", "Balboa Park (SF)");
			put("bayf", "Bay Fair (San Leandro)");
			put("cast", "Castro Valley");
			put("civc", "Civic Center (SF)");
			put("cols", "Coliseum/Oakland Airport");
			put("colm", "Colma");
			put("conc", "Concord");put("daly", "Daly City");
			put("dbrk", "Downtown Berkeley");
			put("dubl", "Dublin/Pleasanton");
			put("deln", "El Cerrito del Norte");
			put("plza", "El Cerrito Plaza");put("embr", "Embarcadero (SF)");
			put("frmt", "Fremont");
			put("ftvl", "Fruitvale (Oakland)");
			put("glen", "Glen Park (SF)");
			put("hayw", "Hayward");
			put("lafy", "Lafayette");
			put("lake", "Lake Merritt (Oakland)");
			put("mcar", "MacArthur (Oakland)");
			put("mlbr", "Millbrae");
			put("mont", "Montgomery St. (SF)");
			put("nbrk", "North Berkeley");
			put("ncon", "North Concord/Martinez");
			put("orin", "Orinda");put("pitt", "Pittsburg/Bay Point");
			put("phil", "Pleasant Hill");
			put("powl", "Powell St. (SF)");
			put("rich", "Richmond");
			put("rock", "Rockridge (Oakland)");
			put("sbrn", "San Bruno");
			put("sfia", "SFO Airport");
			put("sanl", "San Leandro");
			put("shay", "South Hayward");
			put("ssan", "South San Francisco");
			put("ucty", "Union City");
			put("wcrk", "Walnut Creek");
			put("woak", "West Oakland");
		}
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location);
    	myLat = (TextView) findViewById(R.id.curLat);
    	myLon = (TextView) findViewById(R.id.curLon);
    	theStat = (TextView) findViewById(R.id.nearestStation);
        getLocButton = (Button) findViewById(R.id.locationButton);
        getLocButton.setOnClickListener(this);
    	dest1 = (TextView) findViewById(R.id.d1);
    	dest2 = (TextView) findViewById(R.id.d2);
    	dest3 = (TextView) findViewById(R.id.d3);
    	dest4 = (TextView) findViewById(R.id.d4);
    	dest5 = (TextView) findViewById(R.id.d5);
    	eta1 = (TextView) findViewById(R.id.e1);
    	eta2 = (TextView) findViewById(R.id.e2);
    	eta3 = (TextView) findViewById(R.id.e3);
    	eta4 = (TextView) findViewById(R.id.e4);
    	eta5 = (TextView) findViewById(R.id.e5);
		checkLocation();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.location, menu);
		return true;
	}
	
	public void onClick(View view) {
	    switch (view.getId()) {
	    case R.id.locationButton:
			updateNearestStation();
			CheckETATask task = new CheckETATask();
			task.execute();
			break;
	    }
	}
	
	public void checkLocation() {

		//initialize location manager
		manager =  (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		//check if GPS is enabled
		//if not, notify user with a toast
		if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
	    	Toast.makeText(this, "GPS is disabled.", Toast.LENGTH_SHORT).show();
	    } else {

	    	//get a location provider from location manager
	    	//empty criteria searches through all providers and returns the best one
	    	String providerName = manager.getBestProvider(new Criteria(), true);
	    	Location location = manager.getLastKnownLocation(providerName);
	    	if (location != null) {
	    		currentLat = location.getLatitude();
	    		myLat.setText(currentLat + " latitude, ");
	    		currentLon = location.getLongitude();
	    		myLon.setText(currentLon + " longitude");
	    	} else {
	    		myLat.setText("Cannot obtain location");
	    		myLon.setText("Cannot obtain location");
	    	}

	    	//sign up to be notified of location updates every 15 seconds - for production code this should be at least a minute
	    	manager.requestLocationUpdates(providerName, 400, 1, this);
	    }
	}
	
	@Override
	public void onLocationChanged(Location location) {
		TextView myLat = (TextView)findViewById(R.id.curLat);
		TextView myLon = (TextView)findViewById(R.id.curLon);
    	if (location != null) {
    		currentLat = location.getLatitude();
    		myLat.setText(currentLat + " latitude, ");
    		currentLon = location.getLongitude();
    		myLon.setText(currentLon + " longitude");
    	} else {
    		myLat.setText("Cannot obtain location");
    		myLon.setText("Cannot obtain location");
    	}
	}

	@Override
	public void onProviderDisabled(String arg0) {}

	@Override
	public void onProviderEnabled(String arg0) {}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {}
	
    private void updateNearestStation() { 	    	
    	curStat = "";
    	nearestStat = "";
    	shortestDist = Double.POSITIVE_INFINITY;
    	
    	for (String key : coordMap.keySet()) {
    		curStat = key;
    		curStatLat = coordMap.get(key)[0];
    		curStatLon = coordMap.get(key)[1];
    		curDist = calcDistance(currentLat, currentLon, curStatLat, curStatLon);
    		if (curDist < shortestDist) {
    			shortestDist = curDist;
    			curStat = abbrMap.get(curStat);
    			nearestStat = curStat;    			
    		}
    	}
        //set text of nearest station
        theStat.setText(nearestStat);
        theStat.setTextColor(getResources().getColorStateList(R.color.black));
    }
	
	
	// Calculates closest Station
    public double calcDistance(double lat1, double lon1, double lat2, double lon2) {
    	double d2r = (180 / Math.PI);

        double dlong = (lon2 - lon1) * d2r;
        double dlat = (lat2 - lat1) * d2r;
        double a =
            Math.pow(Math.sin(dlat / 2.0), 2)
                + Math.cos(lat1 * d2r)
                * Math.cos(lat2 * d2r)
                * Math.pow(Math.sin(dlong / 2.0), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = 6367 * c;

        return d;
    }

    private void updateDestinationView(ArrayList<Ent> entries) {
    	for (int i = 0; i < entries.size(); i++) {
    		ent = entries.get(i);
    		String dep = ent.getDesti();
    		String es = ent.getETA();
    		
    		switch (i) {
    		case 0:
    			dest1.setText(dep);
    			dest1.setTextColor(getResources().getColorStateList(R.color.black));
    			
    			eta1.setText(es);
    			eta1.setTextColor(getResources().getColorStateList(R.color.Gray));
    			break;
    		case 1:
    			dest2.setText(dep);
    			dest2.setTextColor(getResources().getColorStateList(R.color.black));
    			eta2.setText(es);
    			eta2.setTextColor(getResources().getColorStateList(R.color.Gray));
    			break;
    		case 2:
    			dest3.setText(dep);
    			dest3.setTextColor(getResources().getColorStateList(R.color.black));
    			eta3.setText(es);
    			eta3.setTextColor(getResources().getColorStateList(R.color.Gray));
    			break;
    		case 3:
    			dest4.setText(dep);
    			dest4.setTextColor(getResources().getColorStateList(R.color.black));
    			eta4.setText(es);
    			eta4.setTextColor(getResources().getColorStateList(R.color.Gray));
    			break;
    		case 4:
    			dest5.setText(dep);
    			dest5.setTextColor(getResources().getColorStateList(R.color.black));
    			eta5.setText(es);
    			eta5.setTextColor(getResources().getColorStateList(R.color.Gray));
    			break;
    		
    		}
    	}
    }
    
    
    private class CheckETATask extends AsyncTask<String, Void, String> {

        private final String API_KEY = "2GK2-2JEH-69J5-KWUL";
        private final String baseXML = "http://www.bart.gov/dev/eta/bart_eta.xml";
    	private Document xmlDocument;
    	
        @Override
        protected String doInBackground(String... stats)
        {
           if (stats != null) {
                    
    		   String url = baseXML;
    		   
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
    	       	Log.d("responseSTring", responseString);
    	        return responseString;
    	        } else {       
    	        	throw new IllegalArgumentException("Must provide terms");
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
						updateDests(xmlDocument);
						updateDestinationView(entries);
						
					}
            	}
            	catch (Exception ex) {
            		//do something
            	}
            }
        }
        
        protected void updateDests(Document doc) {
        	Node root = doc.getDocumentElement();
        	Log.d("root", root.getNodeName());
        	int kidsLength = root.getChildNodes().getLength();
        	Log.d("kids", Integer.toString(kidsLength));
        	Node firstStationNode = root.getFirstChild().getNextSibling();
        	Node nameChild;
        	Node abbrChild;
        	Node dateChild;
        	Node timeChild;
        	Node etaChild;
        	Node destChild;
        	Node estimateChild;
        	String nodeTagName;
        	String nodeStatName;
        	String abbrChildName;
        	String currentAb;
        	Node des;
        	String desName;
        	Node est;
        	String estName;
        	int i = 0;
        	Ent e;


        	
        	while (i < kidsLength - 1) {
        		if (i == 0) {
        			currentNode = firstStationNode;
        		} else {
        			currentNode = currentNode.getNextSibling();
        			Log.d("currentNodeName", currentNode.getNodeName());
        		}
        			
        			nodeTagName = currentNode.getFirstChild().getNodeName();
        			//Log.d("node station tag name", nodeTagName);
        			
        			nodeStatName = currentNode.getFirstChild().getTextContent();
        			//Log.d("Actual Station Name", nodeStatName);
        			
                	nameChild = currentNode.getFirstChild();
                	//Log.d("name", nameChild.getNodeName());
                	
                	abbrChild = nameChild.getNextSibling();
                	//Log.d("name", abbrChild.getNodeName());
                	
                	abbrChildName = abbrChild.getFirstChild().getTextContent();
                	Log.d("abbr", abbrChildName);
        			String nearestStatAb = MainActivity.map.get(nearestStat).toUpperCase();
        			Log.d("nearest", nearestStatAb);
            		

            		if ( (currentNode.getNodeName().equals("station")) &&
            				(abbrChildName.equals(nearestStatAb))) {
            			int stationKids = currentNode.getChildNodes().getLength();
        				nameChild = currentNode.getFirstChild();
                    	Log.d("name", nameChild.getNodeName());
                    	
                    	abbrChild = nameChild.getNextSibling();
                    	Log.d("name", abbrChild.getNodeName());
                    	
                    	currentAb = abbrChild.getTextContent();
                    	Log.d("abbr", currentAb);
                    	
                    	dateChild = abbrChild.getNextSibling();
                    	Log.d("name", dateChild.getNodeName());
                    	
                    	timeChild = dateChild.getNextSibling();
                    	Log.d("name", timeChild.getNodeName());
                    	
                    	etaChild = timeChild.getNextSibling();
                    	Log.d("name", etaChild.getNodeName());
                    	
            			for (int j = 0; j < stationKids; j++) {
            				if (j > 3) {
            					des = etaChild.getFirstChild();
            					desName = etaChild.getFirstChild().getTextContent();
            					Log.d("des", desName);
            				
            					est = des.getNextSibling();
            					estName = est.getTextContent();
            					Log.d("est", estName);
            					
            					e = new Ent(desName, estName);
            					entries.add(e);
            					
            					
            					etaChild = etaChild.getNextSibling();
            				}
            			}
            			Log.d("h", Integer.toString(i));
            		}
            		i += 1;
            		
        		}
        		
        	}
        	
        	
        	
        	
        }
        
    }



/*	
	private class DepTimesTask extends AsyncTask<String, Void, String> {

    	private final String API_KEY = "2GK2-2JEH-69J5-KWUL";
        private final String depXML = "http://www.bart.gov/dev/eta/bart_eta.xml";
    	private Document xmlDocument;


		public String doInBackground(String... stats)
        {
           if (stats != null) {
        	   
        		   String url = depXML;
        		   
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
        	                Log.d("rString", responseString);
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
            		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    factory.setNamespaceAware(true);
                    XmlPullParser xpp = factory.newPullParser();
                    
                    xpp.setInput( new StringReader (results) );
                    
                    int eventType = xpp.getEventType();
                    while (eventType != XmlPullParser.END_DOCUMENT) {
                    	Log.d("name", xpp.getName());
                    	if (eventType == XmlPullParser.START_TAG && xpp.getName().equals("name") && xpp.nextText().equals(Nearest.nearestStat)) {
                    		if (eventType == XmlPullParser.END_TAG && xpp.getName().equals("station")) {
                    			break;
                    		} else if (eventType == XmlPullParser.START_TAG && xpp.getName().equals("destination")) {
                    			dests.add(xpp.nextText());
                    		} else if (eventType == XmlPullPars	er.START_TAG && xpp.getName().equals("estimate")) {
                    			dests.add(xpp.nextText());
                    		}
                    	}
                    	xpp.next();
                    }
                    for (int i = 0; i < dests.size(); i++) {
                    	Log.d("List Elements", dests.get(i));
                    }
                    updateDest(dests);
            	} catch (XmlPullParserException xe) {
            		//handle probs
            	} catch (IOException e) {
            		//handle probs
            	}
            }
        }
}
*/