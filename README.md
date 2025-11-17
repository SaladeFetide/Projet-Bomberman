# Bomberman (Interface graphique)

Petit projet Java \- implémentation d'une interface graphique pour un clone de Bomberman.\  
Conçu pour démontrer l'utilisation de Java, JavaFX et Gradle dans le cadre d'un TP universitaire.

## Aperçu
- Interface graphique pour gérer le jeu et l'inventaire de bombes.
- Ressources graphiques sous `src/main/resources/fr/univartois/butinfo/ihm/view` et `src/main/resources/images`.

## Fonctionnalités
- Affichage de la map et des sprites (joueurs, ennemis, bombes).
- Fenêtre d'inventaire pour sélectionner et poser des bombes.
- Contrôles basiques du personnage et gestion des explosions (simulation TP).

## Technologies
- Java (JDK 11+ recommandé)
- JavaFX (version compatible avec le JDK utilisé)
- Gradle (wrapper fourni : `gradlew`, `gradlew.bat`)
- IDE conseillé : `IntelliJ IDEA 2025.1.1`

## Structure du projet (extrait)
- `build.gradle` — configuration Gradle
- `src/main/java` — code source (`fr.univartois.butinfo.ihm.*`)
- `src/main/resources` — FXML et images (`/images`, `/view`)
- `gradlew.bat` — wrapper Windows

## Prérequis (Windows)
1. JDK installé (11 ou plus).
2. JavaFX disponible (ou configurer le plugin Gradle / modules).
3. `IntelliJ IDEA` ou terminal avec `gradlew.bat`.

## Exécution
### Avec IntelliJ
1. Ouvrir le projet (`File > Open` sur le dossier du repo).
2. Configurer le SDK Java.
3. Exécuter la classe principale : `fr.univartois.butinfo.ihm.BombermanApplication` en tant qu'application JavaFX.

### En ligne de commande (Windows)
- Compiler :  
  `.\gradlew.bat build`
- Exécuter (si une tâche `run` est configurée) :  
  `.\gradlew.bat run`
- Lancer les tests :  
  `.\gradlew.bat test`

Si l'exécution JavaFX échoue, vérifier la configuration des modules JavaFX ou utiliser la configuration d'exécution fournie par IntelliJ.

## Tests
- Tests unitaires via Gradle : `.\gradlew.bat test`
- Couverture et rapports selon configuration Gradle (non fournie par défaut).

## Contribution
- Ce dépôt est une présentation de projet personnel. Pour contributions réelles : ouvrir une issue ou proposer une pull request.

## Licence
- À préciser (par ex. `MIT`, `Apache-2.0`). Par défaut, ajouter un fichier `LICENSE` si nécessaire.

## Contact
- GitHub : `SaladeFetide`  
- Email : louka.riquoir@gmail.com
