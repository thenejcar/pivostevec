package si.kisek.pivovarna.pivostevec.models;

/**
 * Created by nejc on 3/26/16.
 */
public class Pivo
{
	private int id;
	private String name;
	private String desc;

	public Pivo(int id, String name, String desc)
	{
		this.id = id;
		this.name = name;
		this.desc = desc;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (!(o instanceof Pivo)) return false;

		Pivo pivo = (Pivo) o;

		if (name != null ? !name.equals(pivo.name) : pivo.name != null) return false;
		return !(desc != null ? !desc.equals(pivo.desc) : pivo.desc != null);

	}

	@Override
	public int hashCode()
	{
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (desc != null ? desc.hashCode() : 0);
		return result;
	}
}
