## Tâche 1 : Questions sur la base de code

### 1. Rôle de l'interface `Projector`

L'interface `Projector` c'est comme le contrat de base pour transformer les événements en vues. En gros, tu prends un événement qui s'est passé, et tu le traduis en quelque chose de consultable. C'est la pièce maîtresse qui fait le lien entre les événements immuables et les données qu'on peut afficher.

### 2. Rôle du type `S` dans `Projector`

Le `S` c'est un truc générique qui dit "ça peut être n'importe quel type d'état". En vrai, ça veut dire que chaque projet peut avoir son propre type de vue. Par exemple, un projecteur pour les produits aura un `S` de type `ProductView`, un autre pour les utilisateurs aura un `UserView`, etc.

### 3. Javadoc complétée

```java
/**
 * Interface pour projeter des événements vers des vues matérialisées.
 * 
 * @param <S> le type de la vue que ce projecteur va gérer
 */
public interface Projector<S> {
    // ...
}
```

### 4. Pourquoi une interface et pas une classe ?

- **Flexibilité totale**: Tu peux swap d'implémentation quand tu veux
- **Tests plus faciles**: Tu mocks juste les reponses des meme functions
- **Découplage**: Ton code dépend d'un contrat, pas d'une implémentation spécifique
- **SOLID friendly**: Respecte le "SID" single responsabilité + interface segregation + Dependency Inversion

### 5. Rôle de `ProjectionResult`

`ProjectionResult` c'est une boîte intelligente qui contient le résultat d'une projection. Cette boîte peut contenir :
- Un succès (tout s'est bien passé, voici la nouvelle vue)
- Un échec (quelque chose a planté, voici l'erreur)
- Un no-op (rien à faire, voici pourquoi)
- Des événements supplémentaires générés

### 6. Avantages de l'approche monade

**VS les exceptions Java classiques :**
- Tu peux chaîner les opérations avec `flatMap` au lieu de faire des try-catch partout
- Les erreurs sont dans le type, tu les vois venir
- Pas de surprise, le flux de contrôle reste linéaire
- Tu peux transformer les erreurs comme des données normales

**En pratique :**
1. **Code plus lisible** : Pas de try-catch imbriqués sur 3 niveaux
2. **Gestion d'erreur déclarative** : Tu décris ce qui doit se passer en cas d'erreur
3. **Réutilisable** : Les patterns de gestion d'erreur sont les mêmes partout
4. **Testable** : Les cas d'erreur sont faciles à simuler

## Tâche 2 : Questions concernant l'Outboxing

### 1. Rôle de `OutboxRepository`

L'`OutboxRepository` c'est la boîte aux lettres des événements. Elle fait 3 choses principales :
1. Stocke les événements à envoyer
2. Donne les événements prêts à être traités
3. Marque les événements comme "envoyés" ou "échoués"

### 2. Comment l'Outbox Pattern garantit la livraison

C'est simple mais malin :
1. **Transaction atomique** : L'événement est sauvegardé AVANT d'être traité
2. **Idempotent** : Si ça plante, on peut recommencer sans tout casser
3. **Dans l'ordre** : Les événements sont traités dans l'ordre où ils arrivent (par aggregate)
4. **Persistant** : Même si le système crash, les événements sont toujours là

### 3. Comment ça marche concrètement

**Le flux :**
```
Commande → [Service] → Save dans event_log ET outbox (même transaction)
           ↓
[Poller] check outbox toutes les secondes
           ↓
[Dispatcher] projette l'événement
           ↓
Si OK → delete de l'outbox
Si KO → markFailed avec retry dans 30s
```

**Partitionnement :** Les événements sont répartis sur plusieurs threads selon leur `aggregateId`. Un même aggregate va toujours sur le même thread → ordre garanti.

### 4. Gestion des erreurs

**D'après le schéma DB :**
- `attempts` : compte combien de fois on a essayé
- `next_attempt_at` : quand réessayer
- `last_error` : la dernière erreur qui a fait planter

**Dans le code du poller :**
- `MAX_RETRIES = 3` : max 3 tentatives
- `RETRY_DELAY = 30s` : on attend 30 secondes entre chaque essai
- `blockedUntil` : après un échec, on bloque l'aggregate pendant 30s

**Pourquoi 30s et pas exponentiel ?**
- 30 secondes c'est assez pour la plupart des problèmes temporaires (DB lente, réseau)
- Pas besoin d'exponentiel car après 3 échecs, on abandonne
- Simple à comprendre et à debug

**Le workflow erreur :**
1. Première tentative → plante
2. `attempts=1`, `next_attempt_at=now+30s`, `last_error="..."`
3. Pendant 30s, le poller ignore cet événement
4. Après 30s, nouvelle tentative
5. Si 3 échecs → l'événement reste dans l'outbox mais n'est plus traité

## Tâche 3 : Questions sur le journal d'évènements

### 1. Rôle du journal d'événements

Le `event_log` c'est l'historique complet de TOUT ce qui s'est passé. C'est la source de vérité absolue. Si demain tu veux savoir ce qui s'est passé il y a 3 mois à 14h32, c'est là que tu regardes.

### 2. Pourquoi juste une méthode `append` ?

Parce que :
- **Les événements sont sacrés** : Une fois écrits, on ne les touche plus
- **Pas de DELETE** : Sinon tu perds l'historique
- **Pas de UPDATE** : Un événement passé ne change pas
- **Les lectures se font ailleurs** : Via outbox pour le traitement, via projections pour la consultation

C'est un choix architectural : le journal est append-only, comme un journal de bord de navire.

### 3. Implications et autres usages

**Ça veut dire :**
1. **La DB grossit toujours** → faut prévoir du stockage
2. **Lecture optimisée pour l'écriture** → bon pour le write, moins pour les requêtes complexes
3. **Audit parfait** → impossible de tricher avec l'historique

**Tu pourrais aussi :**
- **Refaire le monde** : Rejouer tous les événements depuis le début
- **Debugger comme dans Matrix** : Remonter le temps pour trouver un bug
- **Analyser les tendances** : Voir comment le système évolue
- **S'entraîner pour le RGPD** : Savoir exactement quelles données modifier

## Tâche 4 : Limites de CQRS

### 1. Les gros problèmes de CQRS

1. **C'est compliqué** : Write side + read side = 2x plus de code
2. **Pas en temps réel** : Les vues mettent un peu de temps à se mettre à jour
3. **Tout peut planter** : Plus de pièces mobiles = plus de choses qui cassent
4. **Apprentissage** : C'est pas intuitif au début
5. **Cher** : Plus de DB, plus de serveurs, plus de monitoring

### 2. Ce qui est déjà géré

**Dans notre app :**
- **Latence réduite** : Poller à 1 seconde, c'est rapide
- **Fiabilité** : Outbox + event_log = données safe
- **Ordre préservé** : `aggregate_version` garantit l'ordre
- **Évolutif** : Tu peux ajouter des projections sans toucher au write side

### 3. Les nouveaux problèmes créés

1. **Le poller est un single point of failure** : S'il plante, plus rien ne se passe
2. **Scaling pas ouf** : Partitionnement basé sur les threads, pas sur la charge
3. **État manuel** : Faut gérer les snapshots soi-même
4. **Debug infernal** : Suivre un événement à travers 3 couches
5. **Pas de rollback automatique** : Si une projection plante mais pas les autres → incohérence

### 4. Projection multiple = galère

**Scénario :** 1 événement → doit mettre à jour 3 vues différentes

**Problèmes :**
- Vue 1 OK, Vue 2 OK, Vue 3 KO → 2 sur 3 sont à jour
- Comment rollback Vue 1 et 2 ?
- Quel ordre ? Faut-il un ordre spécifique ?
- Comment gérer les dépendances entre vues ?

### 5. Solutions possibles

**Pour le scaling :**
- **Sharding** : Répartir les aggregates sur plusieurs plus petites instances

**Pour la fiabilité :**
- **Health checks** : Surveiller chaque thread du poller
- **Dead letter queue** : Stocker les échecs définitifs
- **Alerting** : T'envoyer une alerte quand ça plante

**Pour les projections multiples :**
- **Compensation automatique** : Annuler ce qui a été fait en cas d'échec
- **État intermédiaire** : Marquer "en cours de traitement" pour éviter les lectures sales