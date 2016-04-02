package si.kisek.pivovarna.pivostevec.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import si.kisek.pivovarna.pivostevec.R;
import si.kisek.pivovarna.pivostevec.adapters.CustomListAdapter;
import si.kisek.pivovarna.pivostevec.models.Pivo;
import si.kisek.pivovarna.pivostevec.models.Runda;
import si.kisek.pivovarna.pivostevec.utils.RundaDateComparator;
import si.kisek.pivovarna.pivostevec.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
	private static final String TAG = "MainActivity";

	private ListView listView;
	private Button addButton;

	List<Runda> list;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listView = (ListView) findViewById(R.id.listView);
		addButton = (Button) findViewById(R.id.buttonAdd);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Log.d(TAG, "clicked " + position + " " + list.get(position).toString());
				Intent intent = new Intent(MainActivity.this, RundaDetailsActivity.class);
				intent.putExtra("runda", position);
				startActivity(intent);
			}
		});

		addButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(MainActivity.this, AddActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		list = Utils.getSavedRundas(MainActivity.this);

		RundaDateComparator comp = new RundaDateComparator();
		Collections.sort(list, comp);

		if(list.size() > 0)
		{
			TextView emptyText = (TextView) findViewById(R.id.emptyListText);
			emptyText.setVisibility(View.INVISIBLE);
			CustomListAdapter adapter = new CustomListAdapter(this, R.layout.list_row_runda, list);
			listView.setAdapter(adapter);
		}
		else
		{
			TextView emptyText = (TextView) findViewById(R.id.emptyListText);
			emptyText.setVisibility(View.VISIBLE);
		}
	}

	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.history:
				startActivity(new Intent(MainActivity.this, HistoryActivity.class));
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
