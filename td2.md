# TD2 : Gérer un dépôt de code

## Les méthodes de gestion
Définissez au mieux les méthodes de gestion suivantes :
(Aidez-vous du CM2 et des outils de recherche)
- Git Trunk -> petit proj , tout le monde communique bien , il peuvent faire des branchs et ils peuvent faire une PR ou push sur main direct
- Git Flow -> chaqu'un a ca branch pour une fonctionalité ensuite il y a le staging avec main dev fonctionalité , et il faut passer par des PR avec les maintainers ect

Indiquez les cas d’usage typique de chaque méthode et leurs avantages et inconvénients.
- Git Trunk : petit project , plutot senior , communique bien
- Git Flow : beaucoup de monde, très procédural / hiérarchiser , CI/CD 

## Git Trunk
1. Définissez le feature-flags (aussi appelé feature-toggles) une méthode de config ou toute une fonctionalité peut être indépendament dé/activé
2. Indiquez les moyens usuels d’implémenter du feature-flags make-config , config file , config server/web
3. Décrire le flux de travail du Trunk-Based Repository , chaque personne a sa branch , ils font leurs truc et push dans main , et peut être ca casse

## Git flow
1. Décrire le flux de travail du Git Flow
   - chaque personne a sa branch , ils font leurs truc et push dans main , et peut être ca casse
2. Décrire la méthode préférée pour gérer plusieurs versions majeures/mineures en
parallèle
    - chaqu'un a ca branch pour une fonctionalité ensuite il y a le staging avec main dev fonctionalité , et il faut passer par des PR avec les maintainers ect
    - il peux y avoir un roadmap ect , organiser les personnes par groupes ect

## Noms de branche (GitFlow)
Donnez les noms de branches correspondant aux situations suivantes :
- Une fonctionnalité « Gestion des utilisateurs – suppression » (ticket n°B-768) : v0.9.9 - ticket B-768 - implement user CRUD
- Un fix « Mauvaise redirection après ajout d’un email à l’utilisateur » (ticket A-46) : v0.9.9 - ticket A-46 - fix bad redirection after user email added 
- L’ajout d’une configuration « devcontainer » pour l’environnement de développement : v0.9.9 - creating devcontainer 
- Un hotfix pour préparer un patch depuis une version 1.3.1 : 1.3.2
- Une release mineure après 1.4.17 : 1.5.0
- Une branche support après release 12.5.6 (support version mineure) : 12.5.6-lts+1 / 12.5.7

## Commit messages

Indiquez les informations importantes qu’un message de commit devrait indiquer d’un coup
d’œil (obligatoires et souhaitées).

Voici plusieurs messages de commit :

```
feat[B-658]: side-menu statistics page link

+ adding a side menu item
+ adding a static link to the stats page
~ making various minor fixes on CSS

Co-author : Kamel Debbiche
Refs: https://doc.myapp.acompany.net/gui-rules/
```

```
docs[A-245]: C4 modeling – books micro-service

+ init structurizr file (..books.service.structurizr)
- removing outdated ADL and documentation
```

```
chore!: drop support of PHP 7

BREAKING CHANGE: use PHP features not available in PHP 7
```

```
feat: add French language support with i18n
```

Identifier les éléments un à un et déterminer leur caractère obligatoire ou optionnel. Enfin,
déterminez une structure générale applicable à cet ensemble.

- qu'est ce que c'est (feature / bug ticker / doc / test)
- the changes
- autor
- changes to come / what's left to do
- any references links

Lister les types de commit possibles et décrire leur utilisation.

Qu’est qu’un breaking-change ?
un changement qu'il fait que la nouvelle version n'est pas compatible avec une version précédente

## Semantic versioning

REF : https://semver.org/lang/fr/

Décrire en français les numéros de version suivants ces numéros :
- 1.1.0                             : verion publique stable major 1 minor 1 patch 0
- 1.0.0-RC.2                        : ver stable major 1 relase condidate patch 2 (dev) 
- 1.0.0-snapshot+build.9cbd45f6     : ver stable 1 snapshot of build N°
- 3.0.0-beta.1                      : ver stable 3 relase beta patch 1 (dev)
- 2.3.1+nightly.230524.0114         : ver stable 2.3.1 build every night + (date / build n°)
