package se.quedro.challenge;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class AdfenixApplication {
    static String filePath;

    private static SaleObjectConsumerImp saleObjectConsumerImp ;
    private static SaleObjectConsumer.PriorityOrderAttribute orderAttribute;
    public static void main(String[] args) {
        saleObjectConsumerImp = new SaleObjectConsumerImp();
        orderAttribute= saleObjectConsumerImp.getPriorityOrderAttribute();
        while (true) {
            Scanner scanner = new Scanner(new
                    InputStreamReader(System.in));

            System.out.println("Enter file path or exit: ");
            filePath = scanner.nextLine();
            if (filePath.equals("exit")) {
                break;
            }
            doSalesReporting();
        }
    }

    public static void doSalesReporting() {
        saleObjectConsumerImp.startSaleObjectTransaction(filePath, orderAttribute);
        for (SaleObject saleObject : saleObjectConsumerImp.saleObjectList) {
            saleObjectConsumerImp.reportSaleObject(saleObject.getSquareMeters(),saleObject.getPricePerSquareMeter(),saleObject.getCity(),saleObject.getStreet(),saleObject.getFloor());
        }
        saleObjectConsumerImp.commitSaleObjectTransaction();
    }

}

