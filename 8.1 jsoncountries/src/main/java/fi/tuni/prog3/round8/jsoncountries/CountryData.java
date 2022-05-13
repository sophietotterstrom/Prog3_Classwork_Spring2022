package fi.tuni.prog3.round8.jsoncountries;

import com.google.gson.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class CountryData extends Country
{

    public CountryData(String name, double area, long populationCount, double GDP)
    {
        super(name, area, populationCount, GDP);
    }

    static HashMap<String, HashMap<String, String>> countryMap = new HashMap<>();

    public static List<Country> readFromJsons(String areaFile, String populationFile, String gdpFile)
            throws IOException {
        List<Country> countryList = new ArrayList();

        List<String> fileList = Arrays.asList(areaFile, populationFile, gdpFile);

        for (String fileName : fileList) {

            // create Gson instance
            Gson gson = new Gson();

            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get(fileName));

            // convert JSON file to map
            Map<?, ?> map = gson.fromJson(reader, Map.class);
            Map<?, ?> rootMap = (Map<?, ?>) map.get("Root");
            Map<?, ?> dataMap = (Map<?, ?>) rootMap.get("data");
            List<Map<String, List<Map<String, ?>>>> recordList =
                    (List<Map<String, List<Map<String, ?>>>>) dataMap.get("record");


            for (Map<String, List<Map<String, ?>>> record : recordList) {
                List<Map<String, ?>> recordData = record.get("field");

                String countryName = "";
                String value = "";
                String itemKey = "";

                for (Map<String, ?> field : recordData)
                {
                    String command = (String) ((Map) field.get("attributes")).get("name");

                    if (command.equals("Country or Area"))
                        countryName = (String) field.get("value");

                    if (command.equals("Item"))
                        itemKey = (String) field.get("value");

                    if (command.equals("Value"))
                        value = (String) field.get("value");
                }

                switch (itemKey) {
                    case "Surface area (sq. km)":
                        if (countryMap.get(countryName) == null)
                        {
                            HashMap<String, String> temp = new HashMap<>();
                            temp.put("area", value);
                            countryMap.put(countryName, temp);
                        }
                        else
                        {
                            countryMap.get(countryName).put("area", value);
                        }
                        break;

                    case "Population, total":
                        if (countryMap.get(countryName) == null)
                        {
                            HashMap<String, String> temp = new HashMap<>();
                            temp.put("population", value);
                            countryMap.put(countryName, temp);
                        }
                        else
                        {
                            countryMap.get(countryName).put("population", value);
                        }
                        break;

                    case "GDP (constant 2015 US$)":
                        if (countryMap.get(countryName) == null)
                        {
                            HashMap<String, String> temp = new HashMap<>();
                            temp.put("gdp", value);
                            countryMap.put(countryName, temp);
                        }
                        else
                        {
                            countryMap.get(countryName).put("gdp", value);
                        }
                        break;
                }
            }
            // close reader
            reader.close();
        }

        for (Map.Entry<String, HashMap<String, String>> country : countryMap.entrySet()) {
            String name = country.getKey();
            double area = Double.parseDouble(country.getValue().get("area"));
            long populationCount = Long.parseLong(country.getValue().get("population"));
            double GDP = Double.parseDouble(country.getValue().get("gdp"));

            Country newCountry = new Country(name, area, populationCount, GDP);
            countryList.add(newCountry);
        }

        return countryList;

    }


    public static void writeToJson(List<Country> countries, String countryFile) throws IOException
    {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        Writer writer = new FileWriter(countryFile);
        gson.toJson(countries, writer);
        writer.flush();
        writer.close();

    }

    /*

    public static void writeToXml(List<Country> countries, String countryFile) throws IOException {
        Document doc = new Document();
        doc.setRootElement(new Element("countries"));

        for (Country countryObj : countries)
        {
            Element country = new Element("country");
            country.addContent(new Element("name").setText(countryObj.getName()));
            country.addContent(new Element("area").setText(String.valueOf(countryObj.getArea())));
            country.addContent(new Element("population")
                    .setText(String.valueOf(countryObj.getPopulationCount())));
            country.addContent(new Element("gdp").setText(String.valueOf(countryObj.getGDP())));

            doc.getRootElement().addContent(country);
        }

        XMLOutputter xmlOutputter = new XMLOutputter();

        // pretty print
        xmlOutputter.setFormat(Format.getPrettyFormat());

        try (FileOutputStream f =
                     new FileOutputStream(countryFile)) {
            BufferedOutputStream bf = new BufferedOutputStream(f);

            xmlOutputter.output(doc, bf);
        }

    }
    */
}