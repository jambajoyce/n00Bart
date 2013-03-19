/*
package edu.berkeley.cs160.JoyceLiu.proj3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BartListAdapter extends ArrayAdapter<BartData>{

	  private final Context context;
	  private final BartData[] values;

	  public BartListAdapter(Context context, BartData[] values) {

	  //call the super class constructor and provide the ID of the resource to use instead of the default list view item
	    super(context, R.layout.list_adapter_item, values);  //CHANGE LAYOUT TO OWN
	    this.context = context;
	    this.values = values;
	  }

	  //this method is called once for each item in the list
	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {

	    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View listItem = inflater.inflate(R.layout.list_adapter_item, parent, false); //CHANGE LAYOUT TO OWN

	    TextView tv = (TextView) listItem.findViewById(R.id.cityName);
	    tv.setText(values[position].name);

	    TextView infoView = (TextView) listItem.findViewById(R.id.weatherInfo);
	    infoView.setText(values[position].weatherInfo + "\n" + values[position].temp + "\n" + values[position].updateTime);

	    return listItem;

	  }

}
*/