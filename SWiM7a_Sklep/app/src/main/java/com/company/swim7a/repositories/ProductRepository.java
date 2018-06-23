package com.company.swim7a.repositories;

import com.company.swim7a.entities.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private static final List<Product> productsList;

    static {
        productsList = new ArrayList<>();
        productsList.add(new Product("5900259059642", "Chipsy Lay's MAXX Zielona Cebulka 155g", 3.91F));
        productsList.add(new Product("5902403568024", "Woda Oaza - niegazowana 1.5l", 0.89F));
        productsList.add(new Product("5901067401968", "DrWitt multiwitamina 750ml", 2.49F));
        productsList.add(new Product("5900334000255", "Tymbark Jablko-Mieta 500ml", 2.69F));
    }

    public Product getProduct(String code){
        int i = 0;
        while(i < productsList.size() && !productsList.get(i).getCode().equals(code)){i++;}
        return i != productsList.size() ? productsList.get(i) : null;
    }
}
