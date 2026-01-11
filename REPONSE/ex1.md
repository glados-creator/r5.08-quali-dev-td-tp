# **TP Exercice 1 : Analyse de l'application Order flow**

## **Tâche 1 : Ségrégation des responsabilités**

### **1. Domaines métiers principaux :**
D'après les dossiers, l'application gère principalement :
- **Gestion des produits** (Product Registry) - pour ajouter/modifier/supprimer des produits
- **Boutique en ligne** (Store) - interface pour les clients
- **Commandes** (Order Processing) - mentionné mais pas encore implémenté
- **Panier** (Shopping Cart) - mentionné mais pas encore implémenté

### **2. Comment les services sont conçus :**
Chaque partie métier est un service séparé :
- Un service pour gérer les produits (`product-registry-domain-service`)
- Un service pour lire les produits (`product-registry-read-service`)
- Un service principal qui fait la liaison (`store-back`)
- Une application web pour les utilisateurs (`store-front`)

### **3. Responsabilités des modules :**

**`apps/store-back`** : Service principal
- Reçoit les requêtes du site web
- Appelle les autres services
- Fait le pont entre le front et les services

**`apps/store-front`** : Site web
- Interface utilisateur (Angular)
- Pages pour voir/ajouter des produits
- Communique avec `store-back`

**`libs/kernel`** : Cœur de l'application
- Définit ce qu'est un produit (nom, prix, description)
- Contient les règles métier de base

**`apps/product-registry-domain-service`** : Service d'écriture
- Ajoute des nouveaux produits
- Modifie les produits existants
- Supprime des produits

**`apps/product-registry-read-service`** : Service de lecture
- Liste tous les produits
- Cherche un produit spécifique
- Donne les détails d'un produit

**`libs/bom-platform`** : Gestionnaire de versions
- S'assure que toutes les bibliothèques ont les bonnes versions
- Évite les conflits entre versions

**`libs/cqrs-support`** : Support pour l'architecture
- Aide à séparer la lecture et l'écriture
- Gère les événements (quand un produit est créé/modifié)

**`libs/sql`** : Bases de données
- Définit les tables dans la base de données
- Gère les migrations (changements de structure)

## **Tâche 2 : Identifier les concepts principaux**

### **1. Concepts principaux :**
- **Séparation lecture/écriture** : Un service écrit, un autre lit
- **Événements** : Quand quelque chose se passe (ex: produit créé), on enregistre l'événement
- **Base de données relationnelle** : PostgreSQL pour stocker les données
- **APIs REST** : Les services communiquent entre eux via des URLs
- **Frontend/Backend séparés** : Le site web et le serveur sont indépendants

### **2. Comment c'est implémenté :**
**Pour stocker les données** :
- PostgreSQL comme base principale
- Tables pour produits, événements, etc.

**Pour gérer les transactions** :
- Quand on modifie un produit, tout se fait en une seule opération
- Si une partie échoue, tout est annulé

**Pour les événements** :
- Quand un produit est créé, on enregistre "produit créé"
- D'autres services peuvent écouter ces événements

**Pour les erreurs** :
- Validation des données d'entrée
- Messages d'erreur clairs

### **3. Que fait `libs/cqrs-support` ?**
C'est une bibliothèque qui aide à :
- Enregistrer les événements (ex: "produit X créé")
- Relire ces événements plus tard
- Synchroniser les données entre services

### **4. Que fait `libs/bom-platform` ?**
C'est une liste de toutes les bibliothèques utilisées avec leurs versions.
Ça évite qu'un service utilise une version différente d'une autre.

### **5. Comment l'application reste fiable ?**
- **Validation** : On vérifie les données avant de les enregistrer
- **Transactions** : Tout ou rien pour éviter les données corrompues
- **Sauvegarde des changements** : On garde l'historique de tous les changements
- **Séparation des rôles** : Chaque service fait une chose et la fait bien

## **Tâche 3 : Problèmes de qualité identifiés**
