package com.sny.contact.service;

import com.sny.contact.beans.Contact;
import com.sny.contact.dao.IDao;

import java.util.ArrayList;
import java.util.List;

public class ContactService implements IDao<Contact> {
        private List<Contact> contacts;
        private static ContactService instance;
        private ContactService() {
            this.contacts = new ArrayList<>();
        }
        public static ContactService getInstance() {
            if(instance == null)
                instance = new ContactService();
            return instance;
        }
        @Override
        public boolean create(Contact o) {
            return contacts.add(o);
        }
        @Override
        public boolean update(Contact o) {
            return true;
        }
        @Override
        public boolean delete(Contact o) {
            return contacts.remove(o);
        }
        @Override
        public Contact findById(int id) {
            for(Contact s : contacts){
                if(s.getId()== id)
                    return s;
            }
            return null;
        }
        @Override
        public List<Contact> findAll() {
            return contacts;
        }
    }

