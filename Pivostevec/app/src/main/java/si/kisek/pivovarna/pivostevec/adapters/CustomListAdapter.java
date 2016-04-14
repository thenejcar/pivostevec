package si.kisek.pivovarna.pivostevec.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import si.kisek.pivovarna.pivostevec.R;
import si.kisek.pivovarna.pivostevec.models.Runda;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by nejc on 3/26/16.
 */
public class CustomListAdapter extends ArrayAdapter<Runda> {
	DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
	private int resourceId;


	public CustomListAdapter(Context context, int resourceId) {
		super(context, resourceId);
		this.resourceId = resourceId;
	}

	public CustomListAdapter(Context context, int resourceId, List<Runda> list) {
		super(context, resourceId, list);
		this.resourceId = resourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater li = LayoutInflater.from(getContext());
			convertView = li.inflate(resourceId, null);
		}

		Runda runda = getItem(position);

		if (runda != null) {
			TextView name = (TextView) convertView.findViewById(R.id.name);
			TextView starost = (TextView) convertView.findViewById(R.id.starost);
			TextView date = (TextView) convertView.findViewById(R.id.starost);
			TextView count05 = (TextView) convertView.findViewById(R.id.count05);
			TextView count03 = (TextView) convertView.findViewById(R.id.count03);
			TextView suma = (TextView) convertView.findViewById(R.id.suma);

			if (name != null)
				name.setText(runda.getPivo().getName());
			if (starost != null) {
				date.setText(formatter.format(runda.getDate()));
				long millisAge = new Date().getTime() - runda.getDate().getTime();
				long days = millisAge / (1000 * 60 * 60 * 24);
				starost.setText("" + days);
				String ime = runda.getPivo().getName();
				if (days < 8) {
					starost.setTextColor(Color.RED);
				} else if (days < 21) {
					starost.setTextColor(Color.BLUE);
				} else {
					switch (ime) {
						case "Sigismundus":
							starost.setTextColor(Color.BLUE);
							break;
						default:
							starost.setTextColor(Color.BLACK);
							break;
					}
					if (days > 59) {
						starost.setTextColor(Color.BLACK);
					}
				}
			}

				if (count05 != null)
					count05.setText(String.format("%2d", runda.getCount05()));
				if (count03 != null)
					count03.setText(String.format("%2d", runda.getCount03()));
				suma.setText(String.format("%2d", runda.getCount05() + runda.getCount03()));
			}
			return convertView;
		}
	}