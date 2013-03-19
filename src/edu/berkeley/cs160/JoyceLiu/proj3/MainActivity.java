package edu.berkeley.cs160.JoyceLiu.proj3;

import java.util.Collections;
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;


public class MainActivity extends Activity implements View.OnClickListener{
	
	private Button fareButton;
	private Button mapButton;
	private Button buyButton;
	private Button nextLeaveButton;
	
	public final static HashMap<String, String> map = new HashMap<String, String>();
	static 
	{
			map.put("12th St. Oakland City Center", "12th");
			map.put("16th St. Mission (SF)", "16th");
			map.put("19th St. Oakland", "19th");
			map.put("24th St. Mission (SF)", "24th");
			map.put("Ashby (Berkeley)", "ashb");
			map.put("Balboa Park (SF)", "balb");
			map.put("Bay Fair (San Leandro)", "bayf");
			map.put("Castro Valley", "cast");
			map.put("Civic Center (SF)", "civc");
			map.put("Coliseum/Oakland Airport", "cols");
			map.put("Colma", "colm");
			map.put("Concord", "conc");
			map.put("Daly City", "daly");
			map.put("Downtown Berkeley", "dbrk");
			map.put("Dublin/Pleasanton", "dubl");
			map.put("El Cerrito del Norte", "deln");
			map.put("El Cerrito Plaza", "plza");
			map.put("Embarcadero (SF)", "embr");
			map.put("Fremont", "frmt");
			map.put("Fruitvale (Oakland)", "ftvl");
			map.put("Glen", "glen");
			map.put("Hayward", "hayw");
			map.put("Lafayette", "lafy");
			map.put("Lake Merritt (Oakland)", "lake");
			map.put("MacArthur (Oakland)", "mcar");
			map.put("Millbrae", "mlbr");
			map.put("Montgomery St. (SF)", "mont");
			map.put("North Berkeley", "nbrk");
			map.put("North Concord/Martinez", "ncon");
			map.put("Orinda", "orin");
			map.put("Pittsburg/Bay Point", "pitt");
			map.put("Pleasant Hill", "phil");
			map.put("Powell St. (SF)", "powl");
			map.put("Richmond", "rich");
			map.put("Rockridge (Oakland)", "rock");
			map.put("San Bruno", "sbrn");
			map.put("San Francisco Int'l Airport", "sfia");
			map.put("San Leandro", "sanl");
			map.put("South Hayward", "shay");
			map.put("South San Francisco", "ssan");
			map.put("Union City", "ucty");
			map.put("Walnut Creek", "wcrk");
			map.put("West Oakland", "woak");
		}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	      
		fareButton = (Button) findViewById(R.id.fare_button);
		mapButton = (Button) findViewById(R.id.map_button);
		buyButton = (Button) findViewById(R.id.buy_button);
		nextLeaveButton = (Button) findViewById(R.id.next_leave_button);
		
		fareButton.setOnClickListener(this);
		mapButton.setOnClickListener(this);
		buyButton.setOnClickListener(this);
		nextLeaveButton.setOnClickListener(this);
		
		
	}
	
	public void onClick(View view) {
	    switch (view.getId()) {
	    

	    case R.id.buy_button:
	    	Intent myIntent = new Intent(view.getContext(), Buy.class);
            startActivity(myIntent);
			break;

			
	    case R.id.map_button:
	    	Intent myIntent1 = new Intent(view.getContext(), Zoom.class);
            startActivity(myIntent1);
			break;
			
	    case R.id.fare_button:
	    	Intent myIntent2 = new Intent(view.getContext(), Fare.class); 
            startActivity(myIntent2);
			break;
/*			
	    case R.id.next_leave_button:
	    	Intent myIntent3 = new Intent(view.getContext(), Next.class);
            startActivity(myIntent3);
			break;
*/
	    }
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}
	        
	        

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

    public HashMap<String, String> getHM() {
    	return map;
    }

}

