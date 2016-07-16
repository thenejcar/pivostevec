package si.kisek.pivovarna.pivostevec.activities;

import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import si.kisek.pivovarna.pivostevec.R;
import si.kisek.pivovarna.pivostevec.adapters.CustomListAdapter;
import si.kisek.pivovarna.pivostevec.models.Pivo;
import si.kisek.pivovarna.pivostevec.models.Runda;
import si.kisek.pivovarna.pivostevec.utils.RESTSingleton;
import si.kisek.pivovarna.pivostevec.utils.RundaDateComparator;
import si.kisek.pivovarna.pivostevec.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{
	private static final String TAG = "MainActivity";

	private ListView listView;

	private TextView count05total;
	private TextView count03total;
	private TextView sumaTotal;

	private List<Runda> batchList;
	private Map<Integer, Pivo> beerMap;

	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listView = (ListView) findViewById(R.id.listView);
		context = this;

		count05total = (TextView) findViewById(R.id.count05Total);
		count03total = (TextView) findViewById(R.id.count03Total);
		sumaTotal = (TextView) findViewById(R.id.sumaTotal);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Log.d(TAG, "clicked " + position + " " + batchList.get(position).toString());
				Intent intent = new Intent(MainActivity.this, RundaDetailsActivity.class);
				intent.putExtra("runda", Utils.rundaToJSONObject(batchList.get(position)).toString());
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onResume()
	{
		super.onResume();

		String beerPath = Utils.getApiAddress(context) + "beer";
		JsonArrayRequest beerReq = new JsonArrayRequest(Request.Method.GET, beerPath, null,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						Log.d(TAG, "Batch response " + response);
						try {

							beerMap = Utils.pivoMapFromJSONArray(response);

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e(TAG, "Beer Error response " + error);
					}
		});

		batchList = new ArrayList<>();

		String batchPath = Utils.getApiAddress(context) + "batch/" + Utils.getUserId(context);
		JsonArrayRequest batchReq = new JsonArrayRequest(Request.Method.GET, batchPath, null,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						Log.d(TAG, "Batch response " + response);
						for (int i=0; i<response.length(); i++)
							try {

								batchList.add(Utils.rundaFromJSONObject(response.getJSONObject(i), beerMap));

							} catch (JSONException e) {
								e.printStackTrace();
							}

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e(TAG, "Batch Error response " + error);
					}
		});

		RESTSingleton.getInstance(context).addToRequestQueue(batchReq);


		if(batchList.size() > 0)
		{
			RundaDateComparator comp = new RundaDateComparator();
			Collections.sort(batchList, comp);

			TextView emptyText = (TextView) findViewById(R.id.emptyListText);
			emptyText.setVisibility(View.INVISIBLE);
			CustomListAdapter adapter = new CustomListAdapter(this, R.layout.list_row_runda, batchList);

			int sum05 = 0;
			int sum03 = 0;
			for(Runda r : batchList) {
				sum03 += r.getCount03();
				// isto kot sum03 = sum03 + r.getCount03();
				sum05 += r.getCount05();
			}

			count05total.setText("" + sum05);
			count03total.setText("" + sum03);
			sumaTotal.setText("" + (sum05+sum03));

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
			case R.id.uredi_pivo:
				startActivity(new Intent(MainActivity.this, EditActivity.class));
				return true;
			case R.id.dodaj:
				Intent intent = new Intent(MainActivity.this, AddActivity.class);
				startActivity(intent);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
