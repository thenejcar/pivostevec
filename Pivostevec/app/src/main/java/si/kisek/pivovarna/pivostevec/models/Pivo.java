package si.kisek.pivovarna.pivostevec.models;

/**
 * Created by nejc on 3/26/16.
 */
public class Pivo
{
	private String name;
	private String desc;

	public Pivo(String name, String desc)
	{
		this.name = name;
		this.desc = desc;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	@Override
	public String toString()
	{
		return name;
	}
}
