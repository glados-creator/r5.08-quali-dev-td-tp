## Tâche 1 : "Fuite technique" et "Fuite métier"

**Problème identifié :** Les méthodes `streamProductEvents` et `streamProductListEvents` du `ReadProductService` contiennent des **fuites techniques** - elles exposent des détails d'implémentation qui devraient être cachés.

**Concrètement :**
- Le parsing manuel du `lastEventId` (pour SSE/Server-Sent Events)
- La logique de filtrage des événements par ID ou aggregate
- La transformation des événements en DTO
- La gestion de la projection événement → vue métier

**Pourquoi c'est un problème :**
1. **Le service connaît trop de détails** : Il sait comment parser les IDs, filtrer les flux, mapper les données
2. **Difficile à tester** : Beaucoup de responsabilités mélangées
3. **Évolution compliquée** : Si on change la logique SSE, faut modifier le service
4. **Violation single responsabilité principle** : Le service fait du streaming, du parsing, du mapping, de la projection...

**Solution :** Déplacer toute cette logique dans `ProductEventBroadcaster` :
- `ProductEventBroadcaster.getEventsSince(lastEventId)` : gère le parsing et le filtrage
- `ProductEventBroadcaster.streamProductSummaries(aggregateId)` : gère la projection et le mapping
- Le service appelle juste ces méthodes propres