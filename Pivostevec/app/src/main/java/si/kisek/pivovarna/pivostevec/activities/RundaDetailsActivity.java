package si.kisek.pivovarna.pivostevec.activities;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import si.kisek.pivovarna.pivostevec.R;
import si.kisek.pivovarna.pivostevec.models.Runda;
import si.kisek.pivovarna.pivostevec.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RundaDetailsActivity extends AppCompatActivity
{
	private static final String TAG = "RundaDetailsActivity";
	private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

	private Button save;
	private Button delete;
	private TextView date;
	private TextView age;
	private TextView count05;
	private TextView ch05;
	private TextView count03;
	private TextView ch03;
	private TextView name;
	private TextView desc;

	private Context context;
	private Runda activeRunda;
	private String activeRundaStr;
	private List<Runda> list;

	private int change05 = 0;
	private int change03 = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_runda_details);
		context = this;

		activeRundaStr = getIntent().getStringExtra("runda");
		if(activeRundaStr == null)
		{
			Toast.makeText(RundaDetailsActivity.this, "Error, couldn't read intent extra", Toast.LENGTH_SHORT).show();
			finish();
		}

		try
		{
			activeRunda = Utils.rundaFromJSONObject(new JSONObject(activeRundaStr));
		} catch (JSONException e)
		{
			Toast.makeText(RundaDetailsActivity.this, "Error, couldn't convert String to JSONObject", Toast.LENGTH_SHORT);
		}


		name = (TextView) findViewById(R.id.name);
		desc = (TextView) findViewById(R.id.desc);
		date = (TextView) findViewById(R.id.date);
		age = (TextView) findViewById(R.id.age);

		count03 = (TextView) findViewById(R.id.count03);
		count05 = (TextView) findViewById(R.id.count05);
		ch03 = (TextView) findViewById(R.id.change03);
		ch05 = (TextView) findViewById(R.id.change05);

		save = (Button) findViewById(R.id.buttonSave);
		delete = (Button) findViewById(R.id.buttonDelete);

		list = Utils.getSavedRundas(context);
		Log.d(TAG, activeRunda.toString());

		name.setText(activeRunda.getPivo().getName());
		desc.setText(activeRunda.getPivo().getDesc());
		date.setText(sdf.format(activeRunda.getDate()));
		long millisAge = new Date().getTime() - activeRunda.getDate().getTime();
		long days = millisAge / (1000 * 60 * 60 * 24);
		age.setText(""+days);
		count05.setText(""+activeRunda.getCount05());
		count03.setText(""+activeRunda.getCount03());

		save.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Utils.saveRundas(context, list);
				finish();
			}
		});

		delete.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Builder dialogBuilder = new Builder(context);
				dialogBuilder.setTitle("Potrditev");
				dialogBuilder.setMessage("Zares izbrisem rundo?");
				dialogBuilder.setPositiveButton("Izbrisi", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						list.remove(activeRunda);
						Utils.saveRundas(context, list);
						finish();
					}
				});
				dialogBuilder.setNegativeButton("Preklici", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						return;
					}
				});
				dialogBuilder.show();
			}
		});
	}

	public void onIncrease(View v)
	{
		Log.d(TAG, "clicked on increase");
		switch(v.getId())
		{
			case R.id.increase03:
				activeRunda.setCount03(activeRunda.getCount03()+1);
				change03++;
				update03();
				break;
			case R.id.increase05:
				activeRunda.setCount05(activeRunda.getCount05() + 1);
				change05++;
				update05();
				break;
		}
	}

	public void onDecrease(View v)
	{
		Log.d(TAG, "clicked on decrease");
		switch(v.getId())
		{
			case R.id.decrease03:
				if(activeRunda.getCount03() == 0)
					break;
				activeRunda.setCount03(activeRunda.getCount03() - 1);
				change03--;
				update03();
				break;
			case R.id.decrease05:
				if(activeRunda.getCount05() == 0)
					break;
				activeRunda.setCount05(activeRunda.getCount05() - 1);
				change05--;
				update05();
				break;
		}
	}

	private void update03()
	{
		count03.setText("" + activeRunda.getCount03());
		if(change03 == 0)
		{
			ch03.setText("");
		}
		else if(change03 > 0)
		{
			ch03.setText("(+"+change03+")");
		}
		else
		{
			ch03.setText("("+change03+")");
		}
	}

	private void update05()
	{
		count05.setText(""+activeRunda.getCount05());
		if(change05 == 0)
		{
			ch05.setText("");
		}
		else if(change05 > 0)
		{
			ch05.setText("(+"+change05+")");
		}
		else
		{
			ch05.setText("("+change05+")");
		}
	}
}
