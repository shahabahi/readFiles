package se.quedro.challenge;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleObject {
    String type;
    int squareMeters;
    long pricePerSquareMeter;
    String city;
    String street;
    Integer floor;
}
