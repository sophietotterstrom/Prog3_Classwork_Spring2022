package fi.tuni.prog3.round8.xmlcountries;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class CountryData extends Country
{

    public CountryData(String name, double area, long populationCount, double GDP)
    {
        super(name, area, populationCount, GDP);
    }

    static HashMap<String, HashMap<String, String>> countryMap = new HashMap<>();

    public static List<Country> readFromXmls(String areaFile, String populationFile, String gdpFile)
            throws IOException, JDOMException
    {
        List<Country> countryList = new ArrayList();

        List<String> fileList = Arrays.asList(areaFile, populationFile, gdpFile);

        for (String fileName: fileList)
        {
            SAXBuilder sax = new SAXBuilder();
            // XML is a local file
            Document areaDoc = sax.build(new File(fileName));

            Element rootNode = areaDoc.getRootElement();
            Element dataNode = rootNode.getChild("data");
            List<Element> recordList = dataNode.getChildren("record");


            for (Element record : recordList)
            {
                List<Element> fieldList = record.getChildren("field");

                String name = "";
                String value = "";
                String key = "";

                for (Element field : fieldList)
                {
                    if (field.getAttributeValue("name").equals("Country or Area"))
                        name = field.getText();
                    if (field.getAttributeValue("name").equals("Item"))
                        key = field.getText();
                    if (field.getAttributeValue("name").equals("Value"))
                        value = field.getText();
                }


                switch (key) {
                    case "Surface area (sq. km)":
                        if (countryMap.get(name) == null) {
                            HashMap<String, String> temp = new HashMap<>();
                            temp.put("area", value);
                            countryMap.put(name, temp);
                        } else {
                            countryMap.get(name).put("area", value);
                        }
                        break;
                    case "Population, total":
                        if (countryMap.get(name) == null) {
                            HashMap<String, String> temp = new HashMap<>();
                            temp.put("population", value);
                            countryMap.put(name, temp);
                        } else {
                            countryMap.get(name).put("population", value);
                        }

                        break;
                    case "GDP (constant 2015 US$)":
                        if (countryMap.get(name) == null) {
                            HashMap<String, String> temp = new HashMap<>();
                            temp.put("gdp", value);
                            countryMap.put(name, temp);
                        } else {
                            countryMap.get(name).put("gdp", value);
                        }
                        break;
                }
            }
        }

        for (Map.Entry<String, HashMap<String, String>> country : countryMap.entrySet())
        {
            String name = country.getKey();
            double area = Double.parseDouble(country.getValue().get("area"));
            long populationCount = Long.parseLong(country.getValue().get("population"));
            double GDP = Double.parseDouble(country.getValue().get("gdp"));

            Country newCountry = new Country(name, area, populationCount, GDP);
            countryList.add(newCountry);
        }

        return countryList;
    }

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
}