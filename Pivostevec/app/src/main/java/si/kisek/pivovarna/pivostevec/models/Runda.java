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

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (!(o instanceof Runda)) return false;

		Runda runda = (Runda) o;

		if (count05 != runda.count05) return false;
		if (count03 != runda.count03) return false;
		if (pivo != null ? !pivo.equals(runda.pivo) : runda.pivo != null) return false;
		return !(date != null ? !date.equals(runda.date) : runda.date != null);

	}

	@Override
	public int hashCode()
	{
		int result = pivo != null ? pivo.hashCode() : 0;
		result = 31 * result + (date != null ? date.hashCode() : 0);
		result = 31 * result + count05;
		result = 31 * result + count03;
		return result;
	}
}
