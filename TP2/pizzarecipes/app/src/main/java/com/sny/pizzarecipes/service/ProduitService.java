package com.sny.pizzarecipes.service;

import com.sny.pizzarecipes.classes.Produit;
import com.sny.pizzarecipes.dao.IDao;

import java.util.ArrayList;
import java.util.List;

public class ProduitService implements IDao<Produit> {
    private List<Produit> produits;

    public ProduitService() {
        this.produits = new ArrayList<Produit>();
    }

    @Override
    public boolean create(Produit o) {
        return produits.add(o);
    }

    @Override
    public boolean update(Produit produit) {
        int index = produit.getId();
        if (index != -1) {
            produits.set(index, produit);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Produit o) {
        return produits.remove(o);
    }

    @Override
    public List<Produit> findAll() {
        return produits;
    }

    @Override
    public Produit findById(int id) {
        return produits.get(id);
    }
}
