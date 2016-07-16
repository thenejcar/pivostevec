package si.kisek.pivovarna.pivostevec.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import si.kisek.pivovarna.pivostevec.models.Pivo;
import si.kisek.pivovarna.pivostevec.models.Runda;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by nejc on 3/26/16.
 */
public class Utils
{
	private static final String TAG = "Utils";

	public static final String RUNDA_FILE = "saved_runda_list.json";
	public static final String PIVO_FILE = "saved_pivo_list.json";
	public static final String ARCHIVE_FILE = "archive.json";

	public static final String[] STORAGE_PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

	private static File getPivoFile(Context context) {
		permissionCheck(context, STORAGE_PERMISSIONS);
		if (isExternalStorageWritable()){
			File dir = new File(Environment.getExternalStoragePublicDirectory(
					Environment.DIRECTORY_DOCUMENTS), "pivovarna/");
			if (!dir.mkdir() && !dir.mkdirs() && !dir.exists()) {
				Log.e(TAG, "Directory not created");
			} else {
				return new File(dir, PIVO_FILE);
			}
		}
		Toast.makeText(context, "Can't get the pivo file", Toast.LENGTH_LONG).show();
		Log.d(TAG, "cant get the pivo file");
		return null;
	}

	private static File getRundaFile(Context context) {
		permissionCheck(context, STORAGE_PERMISSIONS);
		if (isExternalStorageWritable()){
			File dir = new File(Environment.getExternalStoragePublicDirectory(
					Environment.DIRECTORY_DOCUMENTS), "pivovarna/");
			if (!dir.mkdir() && !dir.mkdirs() && !dir.exists()) {
				Log.e(TAG, "Directory not created");
			} else {
				return new File(dir, RUNDA_FILE);
			}
		}
		Toast.makeText(context, "Can't get the runda file", Toast.LENGTH_LONG).show();
		Log.d(TAG, "cant get the runda file");
		return null;
	}
	private static File getArchiveFile(Context context) {
		permissionCheck(context, STORAGE_PERMISSIONS);
		if (isExternalStorageWritable()){
			File dir = new File(Environment.getExternalStoragePublicDirectory(
					Environment.DIRECTORY_DOCUMENTS), "pivovarna/");
			if (!dir.mkdir() && !dir.mkdirs() && !dir.exists()) {
				Log.e(TAG, "Directory not created");
			} else {
				return new File(dir, ARCHIVE_FILE);
			}
		}
		Toast.makeText(context, "Can't get the archive file", Toast.LENGTH_LONG).show();

		Log.d(TAG, "cant get the archive file");
		return null;
	}

	public static boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}


	public static List<Runda> getSavedRundas(Context context)
	{
		File file = getRundaFile(context);
		if (file == null) return new ArrayList<>();
		try
		{
			long length = file.length();

			if(length >= 0 && length < Integer.MAX_VALUE)
			{
				FileReader reader = new FileReader(file);
				char[] buffer = new char[(int)length];
				reader.read(buffer);
				JSONArray array = new JSONArray(new String(buffer));
				List<Runda> list = new ArrayList<Runda>();
				for(int i=0; i<array.length(); i++)
				{
					list.add(rundaFromJSONObject(array.getJSONObject(i)));
				}
				return list;
			}
			else
			{
				Log.d(TAG, "File is empty or too large");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	public static void saveRundas(Context context, List<Runda> list)
	{
		RundaDateComparator dateComparator = new RundaDateComparator();
		Collections.sort(list, dateComparator);

		File file = getRundaFile(context);
		if (file == null) return;
		try
		{
			JSONArray arr = new JSONArray();
			for(Runda r : list)
			{
				arr.put(rundaToJSONObject(r));
			}
			Log.d(TAG, "writing JSON " + arr);
			FileWriter writer = new FileWriter(file);
			writer.write(arr.toString());
			writer.flush();
			writer.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static List<Pivo> getSavedPivos(Context context)
	{
		File file = getPivoFile(context);
		if (file == null) return new ArrayList<>();
		try
		{
			long length = file.length();

			if(length >= 1 && length < Integer.MAX_VALUE)
			{
				FileReader reader = new FileReader(file);
				char[] buffer = new char[(int)length];
				reader.read(buffer);
				JSONArray array = new JSONArray(new String(buffer));
				List<Pivo> list = new ArrayList<Pivo>();
				for(int i=0; i<array.length(); i++)
				{
					list.add(pivoFromJSONObject(array.getJSONObject(i)));
				}
				return list;
			}
			else
			{
				Log.d(TAG, "File is empty or too large");
			}
		}
		catch (FileNotFoundException fnfe)
		{
			fnfe.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		return new ArrayList<>();

	}

	public static void savePivos(Context context, List<Pivo> list)
	{
		File file = getPivoFile(context);
		if (file == null) return;
		try
		{
			JSONArray arr = new JSONArray();
			for(Pivo p : list)
			{
				arr.put(pivoToJSONObject(p));
			}
			Log.d(TAG, "writing JSON "+arr);
			FileWriter writer;
			writer = new FileWriter(file);
			writer.write(arr.toString());
			writer.flush();
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static JSONObject pivoToJSONObject(Pivo pivo)
	{
		JSONObject obj = new JSONObject();
		try
		{
			obj.put("name", pivo.getName());
			obj.put("desc", pivo.getDesc());
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		return obj;
	}

	public static JSONObject rundaToJSONObject(Runda runda)
	{
		JSONObject obj = new JSONObject();
		try
		{
			obj.put("pivo", pivoToJSONObject(runda.getPivo()));
			obj.put("date", runda.getDate().getTime());
			obj.put("count05", runda.getCount05());
			obj.put("count03", runda.getCount03());
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		return obj;
	}

	public static Pivo pivoFromJSONObject(JSONObject obj)
	{
		try
		{
			return new Pivo(obj.getString("name"), obj.getString("desc"));
		}
		catch (JSONException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	public static Runda rundaFromJSONObject(JSONObject obj)
	{
		try
		{
			return new Runda(pivoFromJSONObject(obj.getJSONObject("pivo")),
					new Date(obj.getLong("date")),
					obj.getInt("count05"),
					obj.getInt("count03"));
		}
		catch (JSONException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static void addToArchive(Context context, Runda runda)
	{
		File file = getArchiveFile(context);
		List<Runda> list = new ArrayList<Runda>();
		if (file == null) return;
		try
		{
			long length = file.length();

			if(length >= 0 && length < Integer.MAX_VALUE)
			{
				FileReader reader = new FileReader(file);
				char[] buffer = new char[(int)length];
				reader.read(buffer);
				JSONArray array = new JSONArray(new String(buffer));
				for(int i=0; i<array.length(); i++)
				{
					list.add(rundaFromJSONObject(array.getJSONObject(i)));
				}
			}
			else
			{
				Log.d(TAG, "File is empty or too large");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}

		list.add(runda);

		try
		{
			JSONArray arr = new JSONArray();
			for(Runda r : list)
			{
				arr.put(rundaToJSONObject(r));
			}
			Log.d(TAG, "writing JSON " + arr);
			FileWriter writer = new FileWriter(file);
			writer.write(arr.toString());
			writer.flush();
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static List<Runda> getArchive(Context context)
	{
		File file = getArchiveFile(context);
		List<Runda> list = new ArrayList<Runda>();
		if (file == null) return new ArrayList<>();
		try
		{
			long length = file.length();

			if(length >= 0 && length < Integer.MAX_VALUE)
			{
				FileReader reader = new FileReader(file);
				char[] buffer = new char[(int)length];
				reader.read(buffer);
				JSONArray array = new JSONArray(new String(buffer));
				for(int i=0; i<array.length(); i++)
				{
					list.add(rundaFromJSONObject(array.getJSONObject(i)));
				}
			}
			else
			{
				Log.d(TAG, "File is empty or too large");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		return list;
	}

	public static void permissionCheck(Context context, String[] permissions) {
		boolean allGranted = true;
		for (String permission : permissions) {
			if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
				allGranted = false;
				break;
			}
		}
		if (!allGranted) {
			ActivityCompat.requestPermissions((Activity) context, permissions, 1);
		}
	}
}
