## Tâche 1 : javadoc
done 

# Tâche 5 : Questions

## 1. Différence entre tests unitaires et tests d'intégration

Les tests unitaires testent une seule partie du code (comme une classe ou méthode) en isolation. Les tests d'intégration testent comment plusieurs parties travaillent ensemble.

Exemple :
- **Test unitaire** : Tester si la classe Product crée bien un produit
- **Test d'intégration** : Tester si l'API POST /api/products enregistre bien un produit dans la base de données

## 2. Est-ce pertinent de couvrir 100% du code par des tests ?

Non, ce n'est pas pertinent de toujours viser 100% de couverture car :

1. **Cela prend trop de temps** pour peu d'avantages
2. **Ce n'est pas utile** de tester du code simple (comme les getters/setters)
3. **Ça ne garantit pas** que l'application fonctionne bien
4. **Ça ralentit** le développement

Il vaut mieux se concentrer sur les parties importantes du code.

## 3. Avantages de l'architecture en couches pour les tests

L'architecture en couches (comme l'architecture oignon) facilite les tests car :

1. **On peut tester chaque partie séparément**
2. **La logique métier est isolée** - on peut tester Product sans base de données
3. **Les tests sont plus rapides** car on n'a pas besoin de tout lancer
4. **C'est plus simple à comprendre** où placer les tests

Exemple avec Product : on peut tester la création d'un produit sans se soucier de comment il est sauvegardé.

## 4. Explication des noms de packages

- **`model`** : Les objets métiers (Product, ProductId)
- **`application`** : La logique qui utilise ces objets (services applicatifs)
- **`infra`** : L'infrastructure technique (connexion base de données)
- **`jpa`** : La partie spécifique à la base de données
- **`web`** : Les APIs et contrôleurs
- **`client`** : Les clients qui appellent d'autres services

Chaque package a un rôle clair, ce qui aide à organiser le code et les tests.