package com.sny.contact.beans;

public class Contact {
    private int id;
    private String nom;
    private String prenom;
    private String tel;

    private static int comp;

    public Contact(String nom, String prenom, String tel) {
        this.id = ++comp;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        if (nom.length() > 1) {
            return nom.charAt(0) + ".";
        } else {
            // If the name has only one character, return just that character
            return nom;
        }
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTel() {
        return tel.substring(0, tel.length() - 4) + "xxxx";
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
