package br.com.opaopa;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ServiceArrayAdapter extends ArrayAdapter<Service> {
	
	private final List<Service> list;
	private final Activity context;
	
	public ServiceArrayAdapter(Activity context, List<Service> list) {
		super(context, R.layout.service_listview_row, list);
		
		this.context = context;
		this.list = list;
	}
	
	static class ViewHolder {
		protected TextView serviceName;
		protected TextView description;
		protected ImageView icon; 
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent ) {
		
		View view = null;
		
		if ( convertView == null ) {
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.service_listview_row, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.serviceName = (TextView) view.findViewById(R.id.service_name);
			viewHolder.description = (TextView) view.findViewById(R.id.service_description);
			viewHolder.icon = (ImageView) view.findViewById(R.id.icon_service);
			view.setTag(viewHolder);
		} else {
			view = convertView;
		}
			
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.serviceName.setText(list.get(position).getServiceName());
		holder.description.setText(list.get(position).getDescription());
		holder.icon.setImageBitmap(list.get(position).getImage());
		
		return view;
		
	}

	
	
}
