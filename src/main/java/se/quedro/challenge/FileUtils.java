package se.quedro.challenge;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileUtils {
    List<SaleObject> saleObjects ;

    public List<SaleObject> readFile(String filePath) throws IOException, ParseException {
        saleObjects = new ArrayList<>();
        Optional<String> fileExtension = getFileExtension(filePath);
        if ("csv".equals(fileExtension.get())) {
            return readCSV(filePath);
        } else if ("json".equals(fileExtension.get())) {
            return readJson(filePath);
        }
        return null;
    }

    public List<SaleObject> readCSV(String filePath) throws IOException {
        String line = "";
        String splitBy = ";";
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        while ((line = br.readLine()) != null) {
            SaleObject saleObject = new SaleObject();
            String[] fileSaleObject = line.split(splitBy);
            if ((fileSaleObject[0].equals("A") && fileSaleObject.length == 6) || (fileSaleObject[0].equals("H") && fileSaleObject.length == 5)) {
                saleObject.setType(fileSaleObject[0]=="A"?"APT":"HOUSE");
                saleObject.setSquareMeters(Integer.parseInt(fileSaleObject[1]));
                saleObject.setPricePerSquareMeter(Long.parseLong(fileSaleObject[2]));
                saleObject.setCity(fileSaleObject[3]);
                saleObject.setStreet(fileSaleObject[4]);
                saleObject.setFloor(Integer.parseInt(fileSaleObject[0].equals("H") ? "0" : fileSaleObject[5]));
                saleObjects.add(saleObject);
            }
        }
        return saleObjects;
    }

    public List<SaleObject> readJson(String filePath) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(filePath);
        Object obj = jsonParser.parse(reader);
        JSONObject jsonSaleObject = (JSONObject) obj;
        JSONArray saleList =(JSONArray) jsonSaleObject.get("saleObjects");
        saleList.forEach(sale -> parseSaleObject((JSONObject) sale));
        return saleObjects;
    }

    public Optional<String> getFileExtension(String filePath) {
        return Optional.ofNullable(filePath)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filePath.lastIndexOf(".") + 1));
    }

    private void parseSaleObject(JSONObject sale)
    {
        SaleObject saleObject=new SaleObject();
        JSONObject postalAddress = (JSONObject) sale.get("postalAddress");
            saleObject.setType((String)sale.get("type"));
            saleObject.setSquareMeters(Integer.parseInt((String)sale.get("sizeSqm")));
            saleObject.setPricePerSquareMeter((long)sale.get("startingPrice"));
            saleObject.setCity((String)postalAddress.get("city"));
            saleObject.setStreet((String)postalAddress.get("street"));
            saleObject.setFloor(Integer.parseInt(saleObject.getType().equals("HOUSE") ? "0" : postalAddress.get("floor").toString()));
            saleObjects.add(saleObject);
    }
}
