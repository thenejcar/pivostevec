package si.kisek.pivovarna.pivostevec.models;

import java.util.Date;

/**
 * Created by nejc on 3/26/16.
 */
public class Runda
{
	private Pivo pivo;
	private Date date;
	private int count05;
	private int count03;

	public Runda(Pivo pivo, Date date, int count05,int count03)
	{
		this.pivo = pivo;
		this.date = date;
		this.count05 = count05;
		this.count03 = count03;
	}

	public Pivo getPivo()
	{
		return pivo;
	}

	public void setPivo(Pivo pivo)
	{
		this.pivo = pivo;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public int getCount05()
	{
		return count05;
	}

	public void setCount05(int count05)
	{
		this.count05 = count05;
	}

	public int getCount03()
	{
		return count03;
	}

	public void setCount03(int count03)
	{
		this.count03 = count03;
	}

	@Override
	public String toString()
	{
		return pivo.getName()+" "+date.toString()+" "+count05+" "+count03;
	}
}
