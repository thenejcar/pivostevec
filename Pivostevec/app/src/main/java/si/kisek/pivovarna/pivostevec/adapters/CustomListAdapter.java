package si.kisek.pivovarna.pivostevec.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import si.kisek.pivovarna.pivostevec.R;
import si.kisek.pivovarna.pivostevec.models.Runda;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by nejc on 3/26/16.
 */
public class CustomListAdapter extends ArrayAdapter<Runda>
{
	DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
	private int resourceId;

	public CustomListAdapter(Context context, int resourceId)
	{
		super(context, resourceId);
		this.resourceId = resourceId;
	}

	public CustomListAdapter(Context context, int resourceId, List<Runda> list)
	{
		super(context, resourceId, list);
		this.resourceId = resourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if(convertView == null)
		{
			LayoutInflater li = LayoutInflater.from(getContext());
			convertView = li.inflate(resourceId, null);
		}

		Runda runda = getItem(position);

		if(runda != null)
		{
			TextView name = (TextView) convertView.findViewById(R.id.name);
			TextView date = (TextView) convertView.findViewById(R.id.date);
			TextView count05 = (TextView) convertView.findViewById(R.id.count05);
			TextView count03 = (TextView) convertView.findViewById(R.id.count03);

			if(name != null)
				name.setText(runda.getPivo().getName());
			if(date != null)
				date.setText(formatter.format(runda.getDate()));
			if(count05 != null)
				count05.setText(String.format("%2d", runda.getCount05()));
			if(count03 != null)
				count03.setText(String.format("%2d", runda.getCount03()));
		}
		return convertView;
	}
}
