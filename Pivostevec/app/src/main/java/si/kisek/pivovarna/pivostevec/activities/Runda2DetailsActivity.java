package si.kisek.pivovarna.pivostevec.activities;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

public class Runda2DetailsActivity extends AppCompatActivity
{
	private static final String TAG = "Runda2DetailsActivity";
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
	private int activeRundaPos;
	private List<Runda> list;

	private int change05 = 0;
	private int change03 = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pivo_info);
		context = this;

	}



}
