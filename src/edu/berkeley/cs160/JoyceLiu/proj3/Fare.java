package edu.berkeley.cs160.JoyceLiu.proj3;

import java.util.Collections;
import java.util.HashMap;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.AutoCompleteTextView;
import android.os.Bundle;
import android.view.View;

import android.view.View.OnClickListener;


public class Fare extends Activity implements View.OnClickListener {
	
	private EditText origStat;
	private EditText destStat;
	private String origName;
	private String destName;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.fare);
        
		origStat = (EditText) findViewById(R.id.orig_box);
		destStat = (EditText) findViewById(R.id.dest_box);
		
        String[] stations = getResources().getStringArray(R.array.station_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, stations);
        
        AutoCompleteTextView origBox = (AutoCompleteTextView) findViewById(R.id.orig_box);
        origBox.setAdapter(adapter);
        
        AutoCompleteTextView destBox = (AutoCompleteTextView) findViewById(R.id.dest_box);
        destBox.setAdapter(adapter);
    }
	
	public void onClick(View view) {

		
		origName = (origStat.getText()).toString();
		destName = (destStat.getText()).toString();

	}

			   
    private static final HashMap<String, String> myMap = new HashMap<String, String>();
    static {
        myMap.put("12th St. Oakland City Center", "12th");
        myMap.put("16th St. Mission (SF)", "16th");
        myMap.put("19th St. Oakland", "19th");
        myMap.put("24th St. Mission (SF)", "24th");
        myMap.put("Ashby (Berkeley)", "ashb");
        myMap.put("Balboa Park (SF)", "balb");
        myMap.put("Bay Fair (San Leandro)", "bayf");
        myMap.put("Castro Valley", "cast");
		myMap.put("Civic Center (SF)", "civc");
		myMap.put("Coliseum/Oakland Airport", "cols");
		myMap.put("Colma", "colm");
		myMap.put("Concord", "conc");
		myMap.put("Daly City", "daly");
		myMap.put("Downtown Berkeley", "dbrk");
		myMap.put("Dublin/Pleasanton", "dubl");
		myMap.put("El Cerrito del Norte", "deln");
		myMap.put("El Cerrito Plaza", "plza");
		myMap.put("Embarcadero (SF)", "embr");
		myMap.put("Fremont", "frmt");
		myMap.put("Fruitvale (Oakland)", "ftvl");
		myMap.put("Glen", "glen");
		myMap.put("Hayward", "hayw");
		myMap.put("Lafayette", "lafy");
		myMap.put("Lake Merritt (Oakland)", "lake");
		myMap.put("MacArthur (Oakland)", "mcar");
		myMap.put("Millbrae", "mlbr");
		myMap.put("Montgomery St. (SF)", "mont");
		myMap.put("North Berkeley", "nbrk");
		myMap.put("North Concord/Martinez", "ncon");
		myMap.put("Orinda", "orin");
		myMap.put("Pittsburg/Bay Point", "pitt");
		myMap.put("Pleasant Hill", "phil");
		myMap.put("Powell St. (SF)", "powl");
		myMap.put("Richmond", "rich");
		myMap.put("Rockridge (Oakland)", "rock");
		myMap.put("San Bruno", "sbrn");
		myMap.put("San Francisco Int'l Airport", "sfia");
		myMap.put("San Leandro", "sanl");
		myMap.put("South Hayward", "shay");
		myMap.put("South San Francisco", "ssan");
		myMap.put("Union City", "ucty");
		myMap.put("Walnut Creek", "wcrk");
		myMap.put("West Oakland", "woak");

    }
	
	
	public String getOrigName() {
		return origName;
	}
	
	public String getDestName() {
		return destName;
	}
	
	
}