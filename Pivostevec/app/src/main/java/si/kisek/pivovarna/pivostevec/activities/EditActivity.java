package si.kisek.pivovarna.pivostevec.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import si.kisek.pivovarna.pivostevec.R;
import si.kisek.pivovarna.pivostevec.models.Pivo;
import si.kisek.pivovarna.pivostevec.models.Runda;
import si.kisek.pivovarna.pivostevec.utils.Utils;

public class EditActivity extends AppCompatActivity
{
	private static final String TAG = "EditActivity";

	private EditText count05;
	private EditText count03;
	private Spinner spinnerPivo;
	private EditText date;
	private EditText name;
	private Button addButton;

	private Context context;

	SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

	String[] array;
	List<Pivo> list;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_pivo_dialog);

		spinnerPivo = (Spinner) findViewById(R.id.spinner);
		name = (EditText) findViewById(R.id.name);
		//date = (EditText) findViewById(R.id.date);
		//addButton = (Button) findViewById(R.id.save);

		context = EditActivity.this;

		list = Utils.getSavedPivos(this);
		array = arrayFromList(list);

		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, array);
		spinnerPivo.setAdapter(adapter);

		spinnerPivo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				Log.d(TAG, "selected " + position + " out of " + array.length);
				if (position == array.length - 1)
				{
					//add new Pivo

					AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditActivity.this);
					alertDialog.setTitle("Novo Pivo");
					View v = View.inflate(alertDialog.getContext(), R.layout.new_pivo_dialog, null);

					alertDialog.setView(v);

					final EditText name = (EditText) v.findViewById(R.id.name);
					final EditText opis = (EditText) v.findViewById(R.id.desc);

					alertDialog.setPositiveButton("Shrani", new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							String nameStr = name.getText().toString();
							String descStr = opis.getText().toString();
							if (nameStr == null || nameStr.isEmpty() || descStr == null || descStr.isEmpty())
							{
								Toast.makeText(context, "Neizpolnjena polja, pivo ni bilo dodano", Toast.LENGTH_LONG).show();
							}
							else
							{
								/*
								Pivo newPivo = new Pivo(name.getText().toString(), opis.getText().toString());

								Log.d(TAG, "new Pivo " + newPivo.toString());
								list.add(newPivo);
								Utils.savePivos(context, list);
								array = arrayFromList(list);
								ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, array);
								spinnerPivo.setAdapter(adapter);
								spinnerPivo.setSelection(array.length - 2);
								*/
							}
						}
					});

					alertDialog.setNegativeButton("Preklici", new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							spinnerPivo.setSelection(0);
						}
					});

					alertDialog.show();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{

			}
		});

		date.setText(sdf.format(new Date()));
		final Calendar calendar = Calendar.getInstance();
		date.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener()
				{
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
					{
						calendar.set(year, monthOfYear, dayOfMonth);
						date.setText(sdf.format(calendar.getTime()));
					}
				}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
				datePickerDialog.show();
			}
		});

		addButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(spinnerPivo.getSelectedItemPosition() == 0 || spinnerPivo.getSelectedItemPosition() == array.length-1)
				{
					Toast.makeText(EditActivity.this, "Izberi pivo", Toast.LENGTH_LONG).show();
					return;
				}

				Pivo selectedPivo = list.get(spinnerPivo.getSelectedItemPosition()-1);
				String dateStr = date.getText().toString();
				String count05Str = count05.getText().toString();
				String count03Str = count03.getText().toString();

				Date dateDate;
				try
				{
					dateDate = sdf.parse(dateStr);
				}
				catch (ParseException e)
				{
					Toast.makeText(EditActivity.this, "Datum mora biti v formatu dd.MM.yyyy", Toast.LENGTH_LONG).show();
					return;
				}

				int count05Int;
				int count03Int;
				try
				{
					count05Int = Integer.parseInt(count05Str);
					count03Int = Integer.parseInt(count03Str);
				}
				catch(NumberFormatException nfe)
				{
					Toast.makeText(EditActivity.this, "Stevilo steklenic ni v pravilni obliki", Toast.LENGTH_LONG).show();
					return;
				}
/*
				Runda newRunda = new Runda(selectedPivo, dateDate, count05Int, count03Int);

				List<Runda> rundas = Utils.getSavedRundas(context);
				rundas.add(newRunda);
				Utils.saveRundas(context, rundas);
				Utils.addToArchive(context, newRunda);
*/
				finish();
			}
		});


	}

	private String[] arrayFromList(List<Pivo> list)
	{
		String[] array = new String[list.size()+2];
		array[0] = "Izberi pivo...";
		int i=0;
		for(Pivo p : list)
		{
			array[i+1] = p.getName();
			i++;
		}
		array[array.length-1] = "+ Dodaj";
		return array;
	}
}
