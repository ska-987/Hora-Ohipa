# Hora ‘ohipa — bêta 1.15

Application Android autonome de suivi du temps de travail et de facturation, développée par **ska_987**.

- **Version :** bêta 1.15
- **Android :** 8.0 / API 26 ou plus récent
- **Licence :** GNU GPL v3 uniquement (`GPL-3.0-only`)
- **Fonctionnement :** local et hors ligne pour le pointage, les données et la création PDF

## Principales fonctions

- pointage arrivée / départ / pauses ;
- correction des plages horaires ;
- fiches clients et prestations ;
- facturation PDF A4 ;
- gestion configurable de la TVA et des mentions fiscales ;
- sauvegardes internes automatiques et export JSON/ZIP ;
- partage explicite vers Drive ou une autre application Android ;
- rappels facultatifs d'arrivée et de départ.

Aucune synchronisation cloud silencieuse n'est effectuée. Les données de travail restent locales tant que l'utilisateur ne déclenche pas lui-même un export ou un partage.

## Installation

Installez `Hora-Ohipa-beta1.15.apk` sur un appareil Android compatible. Pour mettre à jour une installation existante, installez la nouvelle APK **sans désinstaller l'ancienne application**.

Avant une désinstallation ou un changement de téléphone, exportez les données depuis les réglages de l'application.

## Compatibilité des mises à jour

Le projet conserve volontairement :

- l'identifiant Android historique `fr.ska.mesheures` ;
- la clé de stockage locale historique `mes-heures-v1` ;
- la même clé privée de signature Android lors des publications officielles.

Ces éléments ne doivent pas être renommés sans plan de migration, au risque de casser les mises à jour ou l'accès aux données existantes. La clé privée de signature et son mot de passe ne font pas partie du dépôt.

## Compilation

Prérequis :

- Android SDK avec plateforme Android 35 ;
- Build Tools 35.0.0 ;
- JDK compatible Java 8 ;
- Python 3 pour générer l'archive source intégrée à l'application.

Sous Linux/macOS ou environnement Bash :

```bash
export ANDROID_SDK_ROOT=/chemin/vers/android-sdk
./build.sh
```

Sans clé privée, le script produit `build/aligned.apk`, non signé. Pour une publication officielle :

```bash
export SKA_KEYSTORE=/chemin/prive/hora-ohipa.jks
export SKA_STORE_PASS='mot-de-passe-de-la-cle'
./build.sh
```

Ne publiez jamais le keystore ni son mot de passe.

## Tests

Les scripts de vérification métier fournis peuvent être exécutés avec Node.js :

```bash
node verify-tax.cjs
node verify-backup.cjs
```

Les tests Java présents dans `tests/` couvrent également certaines fonctions natives de sauvegarde et de rappel.

Les tests automatisés ne remplacent pas la validation sur un vrai téléphone avant diffusion générale, notamment pour les PDF, le partage, l'impression, la restauration et les notifications.

## Facturation

Les références réglementaires utilisées lors du développement sont regroupées dans [`SOURCES-FACTURATION.md`](SOURCES-FACTURATION.md). L'application aide à produire des documents mais ne détermine pas automatiquement le régime fiscal applicable à l'utilisateur.

## Licence

Hora ‘ohipa est distribué sous GNU GPL v3 uniquement. Voir [`LICENSE`](LICENSE).

Les polices DejaVu utilisées pour les PDF conservent leur licence propre dans `assets/LICENSE-DejaVu.txt`.

## Développeur

**ska_987**  
GitHub : `ska-987`  
Contact : `sav.centreprotech@proton.me`
