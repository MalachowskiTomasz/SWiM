package com.company.swim7a.services;

import com.company.swim7a.entities.Product;
import com.company.swim7a.repositories.ProductRepository;
import com.company.swim7a.utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartService {
    private final ProductRepository productRepository;

    private List<Pair<Product, Integer>> shoppingCartItems;
    private float totalValue = 0;

    public ShoppingCartService(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.shoppingCartItems = new ArrayList<>();
    }

    public List<Pair<Product, Integer>> getShoppingCartItems() {
        return shoppingCartItems;
    }

    public float getTotalValue() {
        return totalValue;
    }

    public boolean changeQuantityByOne(Product product, boolean increase) {
        int i = 0;
        while(shoppingCartItems.get(i).getKey() != product) {i++;}
        Pair<Product, Integer> p = shoppingCartItems.get(i);

        if(increase){
            p.setValue(p.getValue() + 1);
        } else {
            p.setValue(p.getValue() - 1);
            if (p.getValue() == 0) {
                shoppingCartItems.remove(p);
            }
        }
        totalValue += (increase ? 1 : -1) * product.getPrice();
        return shoppingCartItems.size() == 0;
    }

    public boolean addProduct(String code){
        int i = 0;
        while(i < shoppingCartItems.size() && !shoppingCartItems.get(i).getKey().getCode().equals(code)){i++;}

        if(i < shoppingCartItems.size()){
            Pair<Product, Integer> p = shoppingCartItems.get(i);
            totalValue += p.getKey().getPrice();
            p.setValue(p.getValue() + 1);
            return true;
        } else {
            Product p = productRepository.getProduct(code);
            if (p != null) {
                shoppingCartItems.add(new Pair<>(p, 1));
                totalValue += p.getPrice();
                return true;
            }
        }
        return false;
    }

    public void removeProduct(Pair<Product, Integer> product) {
        shoppingCartItems.remove(product);
        totalValue -= product.getValue() * product.getKey().getPrice();
    }

    public void emptyShoppingCart() {
        shoppingCartItems.clear();
        totalValue = 0;
    }
}
