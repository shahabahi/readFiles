package se.quedro.challenge;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class SaleObjectConsumerImp implements SaleObjectConsumer {
    List<SaleObject> saleObjectList = new ArrayList<>();
    FileUtils fileUtils = new FileUtils();

    @Override
    public PriorityOrderAttribute getPriorityOrderAttribute() {
        int orderAttribute = 1;
        Scanner scanner = new Scanner(new
                InputStreamReader(System.in));
        while (true) {
            try {
                System.out.println("Enter order attribute: ");
                System.out.println("1: City");
                System.out.println("2: SquareMeters");
                System.out.println("3: PricePerSquareMeter");

                orderAttribute = Integer.parseInt(scanner.nextLine());
                if (orderAttribute >= 1 && orderAttribute <= 3) {
                    break;
                }
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
        return switch (orderAttribute) {
            case 1 -> PriorityOrderAttribute.City;
            case 2 -> PriorityOrderAttribute.SquareMeters;
            case 3 -> PriorityOrderAttribute.PricePerSquareMeter;
            default -> throw new IllegalStateException("Unexpected value: " + orderAttribute);
        };
    }

    @Override
    public void startSaleObjectTransaction(String filePath, PriorityOrderAttribute orderAttribute) {
        try {
            saleObjectList = fileUtils.readFile(filePath);
            //saleObjectList= fileUtils.readFile("/home/arousha/Shahab/adfenix-code-challange/SaleObject data/SaleObjects.json");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        sortOutput(saleObjectList, orderAttribute);
    }

    @Override
    public void reportSaleObject(int squareMeters, long pricePerSquareMeter, String city, String street, Integer floor) throws TechnicalException {
        System.out.println(squareMeters);
    }

    @Override
    public void commitSaleObjectTransaction() {

    }

    public static List<SaleObject> sortOutput(List<SaleObject> saleObjectList, SaleObjectConsumer.PriorityOrderAttribute priorityOrderAttribute) {
        switch (priorityOrderAttribute) {
            case City -> Collections.sort(saleObjectList, Comparator.comparing(o -> o.city));
            case SquareMeters -> Collections.sort(saleObjectList, Comparator.comparingInt(o -> o.squareMeters));
            case PricePerSquareMeter -> Collections.sort(saleObjectList, Comparator.comparing(SaleObject::getPricePerSquareMeter));
        }
        return saleObjectList;
    }
}
