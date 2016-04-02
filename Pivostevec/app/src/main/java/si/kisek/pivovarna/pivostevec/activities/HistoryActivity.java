package si.kisek.pivovarna.pivostevec.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import si.kisek.pivovarna.pivostevec.R;
import si.kisek.pivovarna.pivostevec.adapters.CustomListAdapter;
import si.kisek.pivovarna.pivostevec.models.Runda;
import si.kisek.pivovarna.pivostevec.utils.RundaDateComparator;
import si.kisek.pivovarna.pivostevec.utils.Utils;

import java.util.Collections;
import java.util.List;

public class HistoryActivity extends AppCompatActivity
{
	private Context context;
	private List<Runda> list;
	private ListView listView;

	private TextView count05total;
	private TextView count03total;
	private TextView sumaTotal;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		context = HistoryActivity.this;

		listView = (ListView) findViewById(R.id.listView);

		count05total = (TextView) findViewById(R.id.count05Total);
		count03total = (TextView) findViewById(R.id.count03Total);
		sumaTotal = (TextView) findViewById(R.id.sumaTotal);

		list = Utils.getArchive(context);
		if(list.size() > 0)
		{
			RundaDateComparator comparator = new RundaDateComparator();
			Collections.sort(list, comparator);

			TextView emptyText = (TextView) findViewById(R.id.emptyListText);
			emptyText.setVisibility(View.INVISIBLE);
			CustomListAdapter adapter = new CustomListAdapter(this, R.layout.list_row_runda, list);
			listView.setAdapter(adapter);

			int sum05 = 0;
			int sum03 = 0;
			for(Runda r : list) {
				sum03 += r.getCount03();
				// isto kot sum03 = sum03 + r.getCount03();
				sum05 += r.getCount05();
			}

			count05total.setText("" + sum05);
			count03total.setText("" + sum03);
			sumaTotal.setText("" + (sum05 + sum03));
		}
		else
		{
			TextView emptyText = (TextView) findViewById(R.id.emptyListText);
			emptyText.setVisibility(View.VISIBLE);
		}
	}
}
