package cafe.services.barman.drinks;

import java.util.Objects;

/**
 * The drinks that are available in the CAFE application.
 * 
 * @author Saúl Rodríguez Naranjo
 */
public class Drink
{

    private String name;
    private String type;
    private String brand;
    private String quantityServed;
    private String servingStyle;

    public Drink(String name, String type, String brand, String quantityServed,
            String servingStyle)
    {
        this.name = name;
        this.type = type;
        this.brand = brand;
        this.quantityServed = quantityServed;
        this.servingStyle = servingStyle;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getBrand()
    {
        return brand;
    }

    public void setBrand(String brand)
    {
        this.brand = brand;
    }

    public String getQuantityServed()
    {
        return quantityServed;
    }

    public void setQuantityServed(String quantityServed)
    {
        this.quantityServed = quantityServed;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.name);
        hash = 89 * hash + Objects.hashCode(this.type);
        hash = 89 * hash + Objects.hashCode(this.brand);
        hash = 89 * hash + Objects.hashCode(this.quantityServed);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Drink other = (Drink) obj;
        if (!Objects.equals(this.name, other.name))
        {
            return false;
        }
        if (!Objects.equals(this.type, other.type))
        {
            return false;
        }
        if (!Objects.equals(this.brand, other.brand))
        {
            return false;
        }
        return Objects.equals(this.quantityServed, other.quantityServed);
    }

    @Override
    public String toString()
    {
        return "Drink{" + "name=" + name + ", type=" + type + ", brand=" + brand + ", quantityServed=" + quantityServed + '}';
    }

    public String getServingStyle()
    {
        return servingStyle;
    }

    public void setServingStyle(String servingStyle)
    {
        this.servingStyle = servingStyle;
    }

}
