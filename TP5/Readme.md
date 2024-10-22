# TP 5: Application Gestion des Etudiants avec Volley + Drawer et API PHP

## Objectifs:
- Intégrer l'API PHP pour la gestion des données.
- Utiliser la bibliothèque Volley pour la communication réseau avec l'API.
- Implémenter un `RecyclerView` pour afficher les données des étudiants récupérés.
- Implémenter un filtrage dynamique des données dans le `RecyclerView`.
- Utiliser `Glide` pour charger et afficher des images.

---
## Structure de tp:
#### Le Repo contient le projet Andoid Studio + PHP API files avec la fichier SQL pour la creation de db

## Vedio demo de l'application:
[Vedio demo](https://github.com/user-attachments/assets/381b2ed9-67f3-44f2-9cbd-14f26d068816
)

## Tester de Api PHP sur navigateur:
### Tester CRUD actions dans la version web. 
[Vedio test API](https://github.com/user-attachments/assets/cb3e7ca2-d941-4fc4-95ff-3c5fc038b4f1
)
## Vue d'ensemble du projet:

Ce projet consiste à créer une application Android avec un menu de navigation (DrawerLayout). L'application permet de lister des étudiants récupérés depuis une API PHP, de les afficher dans un `RecyclerView`, et de permettre aux utilisateurs de filtrer cette liste via une barre de recherche. La bibliothèque `Volley` est utilisée pour effectuer des requêtes HTTP et récupérer les données depuis l'API.

---

## Fonctionnalités:

- **Volley**: Intégration de la bibliothèque Volley pour effectuer des requêtes réseau et récupérer les données sous format JSON depuis une API.

- **Navigation Drawer**: Utilisation de `DrawerLayout` pour gérer un menu de navigation permettant de basculer entre les fragments de l'application.
  
- **RecyclerView**: Affichage d'une liste d'étudiants récupérés via une API PHP. Le `RecyclerView` est utilisé dans le `HomeFragment` pour montrer ces étudiants de façon dynamique.

- **Recherche et Filtrage**: Utilisation d'une barre de recherche dans la `MainActivity` pour filtrer dynamiquement les résultats affichés dans le `RecyclerView`.


- **Gestion des images**: Utilisation de la bibliothèque `Glide` pour charger et afficher les images des étudiants dans le `RecyclerView`.

---


> Année universitaire: 2024-2025
