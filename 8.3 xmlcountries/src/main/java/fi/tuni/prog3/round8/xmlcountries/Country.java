package fi.tuni.prog3.round8.xmlcountries;

public class Country implements Comparable<Country> {
    String name;
    double area;
    long populationCount;
    double GDP;

    public Country(String name, double area, long populationCount, double GDP)
    {
        this.name = name;
        this.area = area;
        this.populationCount = populationCount;
        this.GDP = GDP;
    }

    public String getName() {
        return name;
    }

    public double getArea() {
        return area;
    }

    public long getPopulationCount() {
        return populationCount;
    }

    public double getGDP() {
        return GDP;
    }

    @Override
    public String toString()
    {

        String areaStr = String.format("%.1f", area);
        String GDPStr = String.format("%.1f", GDP);

        return name + "\n"
                + "  Area: " + areaStr + " km2" + "\n"
                + "  Population: " + populationCount + "\n"
                + "  GDP: " + GDPStr + " (2015 USD)" + "\n";

    }

    @Override
    public int compareTo(Country o)
    {
        return name.compareTo(o.getName());
    }
}