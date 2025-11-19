# 🏦 BanqueWSREST - API REST de Gestion Bancaire

> Application Spring Boot démontrant deux implémentations d'API REST (JAX-RS/Jersey et Spring MVC) pour la gestion de comptes bancaires avec support JSON/XML.

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

## 📋 Table des Matières

- [Fonctionnalités](#-fonctionnalités)
- [Technologies Utilisées](#-technologies-utilisées)
- [Prérequis](#-prérequis)
- [Installation](#-installation)
- [Configuration](#-configuration)
- [Démarrage](#-démarrage)
- [Endpoints API](#-endpoints-api)
- [Tests avec POSTMAN](#-tests-avec-postman)
- [Structure du Projet](#-structure-du-projet)
- [Exemples d'Utilisation](#-exemples-dutilisation)
- [Contribution](#-contribution)
- [Licence](#-licence)

## ✨ Fonctionnalités

- ✅ **CRUD complet** sur les comptes bancaires
- 🔄 **Deux implémentations REST** : JAX-RS et Spring MVC
- 📊 **Multi-format** : JSON et XML
- 💱 **Conversion monétaire** : Dinar Tunisien → Euro
- 🗄️ **Base de données H2** en mémoire
- 📡 **API RESTful** conforme aux standards
- 🧪 **Tests** avec POSTMAN/SOAP UI

## 🛠 Technologies Utilisées

| Technologie | Version | Description |
|------------|---------|-------------|
| **Java** | 17 | Langage de programmation |
| **Spring Boot** | 3.5.6 | Framework principal |
| **Spring Data JPA** | - | Couche de persistance |
| **Jersey (JAX-RS)** | - | Implémentation REST Java EE |
| **H2 Database** | - | Base de données en mémoire |
| **Lombok** | - | Réduction du code boilerplate |
| **Maven** | - | Gestion des dépendances |
| **Jackson** | - | Sérialisation JSON/XML |
| **JAXB** | 4.0.0 | Binding XML |

## 📦 Prérequis

Avant de commencer, assurez-vous d'avoir installé :

- ☕ **Java JDK 17** ou supérieur
- 📦 **Maven 3.6+**
- 🔧 **IDE** (IntelliJ IDEA, Eclipse, VS Code, STS)
- 📮 **POSTMAN** ou **SOAP UI** (pour tester l'API)

## 🚀 Installation

### 1. Cloner le repository

```bash
git clone https://github.com/RanaRomdhane/BanqueWSREST.git
cd BanqueWSREST
```

### 2. Installer les dépendances

```bash
mvn clean install
```

### 3. Compiler le projet

```bash
mvn compile
```

## ⚙️ Configuration

### application.properties

Le fichier `src/main/resources/application.properties` contient :

```properties
# Configuration H2
spring.datasource.url=jdbc:h2:mem:banque
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# Console H2
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true

# Serveur
server.port=8089
```

### Choisir l'implémentation REST

#### Option A : Utiliser JAX-RS (Jersey)

Dans `MyConfig.java`, **activez** le bean :

```java
@Bean
public ResourceConfig resourceConfig() {
    ResourceConfig jerseyServlet = new ResourceConfig();
    jerseyServlet.register(CompteJAXRSAPI.class);
    return jerseyServlet;
}
```

#### Option B : Utiliser Spring MVC

Dans `MyConfig.java`, **commentez** le bean :

```java
//@Bean
public ResourceConfig resourceConfig() {
    // ...
}
```

⚠️ **Important** : Les deux ne peuvent pas être actifs simultanément !

## 🎯 Démarrage

### Lancer l'application

```bash
mvn spring-boot:run
```

Ou depuis votre IDE, exécutez `BanqueWsrestApplication.java`

### Vérifier le démarrage

- 🌐 **Application** : http://localhost:8089
- 🗄️ **Console H2** : http://localhost:8089/h2-console
- 📊 **API** : http://localhost:8089/banque/comptes

## 📡 Endpoints API

### Base URL

```
http://localhost:8089/banque
```

### Liste des Endpoints

| Méthode | Endpoint | Description | Body |
|---------|----------|-------------|------|
| `GET` | `/comptes` | Récupérer tous les comptes | - |
| `GET` | `/comptes/{id}` | Récupérer un compte par ID | - |
| `POST` | `/comptes` | Créer un nouveau compte | JSON/XML |
| `PUT` | `/comptes/{id}` | Mettre à jour un compte | JSON/XML |
| `DELETE` | `/comptes/{id}` | Supprimer un compte | - |
| `GET` | `/convertir/{montant}` | Convertir TND → EUR | - |
| `GET` | `/health` | Health check (Spring MVC) | - |

### Formats Supportés

- **JSON** : `Accept: application/json`
- **XML** : `Accept: application/xml`

## 🧪 Tests avec POSTMAN

### Collection POSTMAN

Importez la collection suivante dans POSTMAN :

#### 1. GET - Tous les comptes (JSON)

```http
GET http://localhost:8089/banque/comptes
Accept: application/json
```

**Réponse attendue (200):**
```json
[
    {
        "id": 1,
        "solde": 543.21,
        "dateCreation": "2025-01-15",
        "type": "COURANT"
    }
]
```

#### 2. POST - Créer un compte

```http
POST http://localhost:8089/banque/comptes
Content-Type: application/json
Accept: application/json

{
    "solde": 5000.00,
    "dateCreation": "2025-01-20",
    "type": "EPARGNE"
}
```

**Réponse attendue (201):**
```json
{
    "id": 4,
    "solde": 5000.00,
    "dateCreation": "2025-01-20",
    "type": "EPARGNE"
}
```

#### 3. PUT - Modifier un compte

```http
PUT http://localhost:8089/banque/comptes/1
Content-Type: application/json
Accept: application/json

{
    "solde": 7500.00,
    "type": "COURANT"
}
```

#### 4. DELETE - Supprimer un compte

```http
DELETE http://localhost:8089/banque/comptes/3
```

**Réponse attendue (204):** Aucun contenu

#### 5. GET - Conversion monétaire

```http
GET http://localhost:8089/banque/convertir/1000
```

**Réponse attendue (200):**
```
1000.0 TND = 300.00 EUR
```

#### 6. GET - Format XML

```http
GET http://localhost:8089/banque/comptes
Accept: application/xml
```

**Réponse attendue (200):**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<List>
    <item>
        <id>1</id>
        <solde>543.21</solde>
        <dateCreation>2025-01-15T00:00:00+01:00</dateCreation>
        <type>COURANT</type>
    </item>
</List>
```

### Variables d'Environnement POSTMAN

Créez un environnement avec :

```
base_url = http://localhost:8089/banque
```

## 📂 Structure du Projet

```
BanqueWSREST/
│
├── src/
│   ├── main/
│   │   ├── java/com/example/banquewsrest/
│   │   │   ├── BanqueWsrestApplication.java          # Point d'entrée
│   │   │   │
│   │   │   ├── config/
│   │   │   │   └── MyConfig.java                     # Configuration Jersey
│   │   │   │
│   │   │   ├── entities/
│   │   │   │   ├── Compte.java                       # Entité JPA
│   │   │   │   └── TypeCompte.java                   # Enum (COURANT/EPARGNE)
│   │   │   │
│   │   │   ├── repositories/
│   │   │   │   └── CompteRepository.java             # Repository JPA
│   │   │   │
│   │   │   └── web/
│   │   │       ├── CompteJAXRSAPI.java              # API JAX-RS
│   │   │       └── CompteRESTController.java         # API Spring MVC
│   │   │
│   │   └── resources/
│   │       └── application.properties                 # Configuration
│   │
│   └── test/
│       └── java/com/example/banquewsrest/
│           └── BanqueWsrestApplicationTests.java
│
├── pom.xml                                            # Dépendances Maven
├── README.md                                          # Ce fichier
└── .gitignore
```

## 💡 Exemples d'Utilisation

### Avec cURL

#### Récupérer tous les comptes

```bash
curl -X GET http://localhost:8089/banque/comptes \
  -H "Accept: application/json"
```

#### Créer un compte

```bash
curl -X POST http://localhost:8089/banque/comptes \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{
    "solde": 3000.00,
    "dateCreation": "2025-01-22",
    "type": "EPARGNE"
  }'
```

#### Mettre à jour un compte

```bash
curl -X PUT http://localhost:8089/banque/comptes/1 \
  -H "Content-Type: application/json" \
  -d '{
    "solde": 8500.00,
    "type": "COURANT"
  }'
```

#### Supprimer un compte

```bash
curl -X DELETE http://localhost:8089/banque/comptes/2
```

### Avec JavaScript (Fetch API)

```javascript
// GET - Récupérer tous les comptes
fetch('http://localhost:8089/banque/comptes', {
  headers: { 'Accept': 'application/json' }
})
  .then(response => response.json())
  .then(data => console.log(data));

// POST - Créer un compte
fetch('http://localhost:8089/banque/comptes', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
    'Accept': 'application/json'
  },
  body: JSON.stringify({
    solde: 4500.00,
    dateCreation: '2025-01-23',
    type: 'COURANT'
  })
})
  .then(response => response.json())
  .then(data => console.log(data));
```

## 🔧 Dépannage

### Problème : Port déjà utilisé

**Erreur** : `Port 8089 is already in use`

**Solution** : Changez le port dans `application.properties`
```properties
server.port=8090
```

### Problème : Base de données vide

**Vérification** : Consultez les logs au démarrage. Le `CommandLineRunner` doit créer 3 comptes.

**Solution** : Vérifiez que `BanqueWsrestApplication.java` contient le bean `CommandLineRunner`

### Problème : XML ne fonctionne pas

**Vérification** : 
1. `@XmlRootElement` présent sur `Compte.java` ✓
2. Dépendances JAXB dans `pom.xml` ✓
3. Header `Accept: application/xml` dans la requête ✓

### Problème : Lombok ne compile pas

**Solution IDE IntelliJ** :
1. Settings → Build, Execution, Deployment → Compiler → Annotation Processors
2. ✅ Enable annotation processing

**Solution Eclipse** :
1. Installez le plugin Lombok
2. Redémarrez Eclipse

## 🎓 Concepts Clés

### JAX-RS vs Spring MVC

| Aspect | JAX-RS (Jersey) | Spring MVC |
|--------|-----------------|------------|
| Standard | Java EE | Spring Framework |
| Portabilité | ✅ Haute | ❌ Dépendant de Spring |
| Configuration | Manuel | Auto-configuration |
| Annotation classe | `@Path` | `@RestController` |
| Annotation GET | `@GET` | `@GetMapping` |
| Paramètre URL | `@PathParam` | `@PathVariable` |
| Corps requête | Direct | `@RequestBody` |

### Architecture REST

Cette application suit les principes REST :
- **Ressources** : Comptes bancaires (`/comptes`)
- **Représentations** : JSON et XML
- **Méthodes HTTP** : GET, POST, PUT, DELETE
- **Stateless** : Chaque requête est indépendante
- **Négociation de contenu** : Via header `Accept`

## 📚 Documentation Complémentaire

- [Spring Boot Reference](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [JAX-RS Specification](https://jakarta.ee/specifications/restful-ws/)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [H2 Database](https://www.h2database.com/)

## 👥 Contribution

Les contributions sont les bienvenues ! Pour contribuer :

1. 🍴 Forkez le projet
2. 🌿 Créez une branche (`git checkout -b feature/AmazingFeature`)
3. 💾 Committez vos changements (`git commit -m 'Add AmazingFeature'`)
4. 📤 Pushez vers la branche (`git push origin feature/AmazingFeature`)
5. 🔃 Ouvrez une Pull Request

## 📄 Licence

Ce projet est sous licence MIT. Voir le fichier [LICENSE](LICENSE) pour plus de détails.

## 👨‍💻 Auteur

**Rana ROMDHANE**
- Cours : Service Oriented Computing
- Année : 2025-2026
- Niveau : 3ème année Génie Informatique

---

⭐ **N'oubliez pas de mettre une étoile si ce projet vous a aidé !** ⭐

## 📞 Support

Pour toute question ou problème :
- 💬 Issues : [GitHub Issues](https://github.com/RanaRomdhane/BanqueWSREST/issues)

---

Fait avec ❤️ par Rana !