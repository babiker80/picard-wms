# WMS Shipping MVP

Ein Minimal Viable Product (MVP) für einen einfachen Versandprozess in einem Warehouse Management System (WMS).

---

## Zielsetzung

Dieses Projekt bildet den Versandprozess von Aufträgen ab:

1. **Auftrag erfassen**
2. **Sendung erzeugen**
3. **Paket(e) labeln (Tracking)**
4. **Versand bestätigen**
5. **Status einsehen**

Technologien:
- **Backend:** Java 17+, Spring Boot (REST-API)
- **Datenbank:** PostgreSQL (lokal via Docker)
- **Frontend:** nicht erforderlich

---

## Voraussetzungen

- Java 17 oder höher
- Maven 3.8+
- Docker & Docker Compose
- Git 

---

## Startanleitung

### 1️ Repository klonen oder entpacken

git clone https://github.com/<user>/wms-shipping-mvp.git
cd wms-shipping-mvp

### 2 Datenbank starten (PostgreSQL via Docker)

docker-compose up -d

Das startet eine lokale PostgreSQL-Instanz auf Port 5432
mit Benutzer 'wmsuser' und Passwort 'wmspassword'.

### 3 Spring Boot Backend starten 

mvn spring-boot:run

Die API läuft anschließend auf http://localhost:8080

## Domänenmodell (Überblick)

erDiagram
ORDERS ||--o{ ORDER_ITEMS : enthält
ORDERS ||--|| SHIPMENTS : erzeugt
SHIPMENTS ||--o{ PACKAGES : enthält
CUSTOMERS ||--o{ ORDERS : gibt auf
ADDRESSES ||--o{ ORDERS : liefert nach
PRODUCTS ||--o{ ORDER_ITEMS : wird bestellt


### Entitäten

| Entity        | Beschreibung                                                      |
| ------------- | ----------------------------------------------------------------- |
| **Customer**  | Kunde, optional verknüpft mit Aufträgen                           |
| **Address**   | Lieferadresse eines Auftrags                                      |
| **Order**     | Auftrag mit externer Nummer, Status `CREATED`                     |
| **OrderItem** | Position mit Produkt, Menge, Preis                                |
| **Shipment**  | Eine Sendung pro Auftrag, Status `CREATED` → `PACKED` → `SHIPPED` |
| **Package**   | Paket mit Tracking-Code und Carrier (z. B. DHL)                   |



### Beispiel-Endpunkte

| Methode                             | Pfad                                                       | Beschreibung |
| ----------------------------------- | ---------------------------------------------------------- | ------------ |
| `POST /api/orders`                  | Auftrag anlegen                                            |              |
| `GET /api/orders/{id}`              | Auftrag inkl. Positionen & Sendung abrufen                 |              |
| `POST /api/orders/{id}/shipment`    | Sendung zu Auftrag erzeugen                                |              |
| `POST /api/shipments/{id}/packages` | Paket mit Tracking-Code hinzufügen                         |              |
| `PUT /api/shipments/{id}/status`    | Statusübergang (`PACKED`, `SHIPPED`)                       |              |
| `GET /api/shipments`                | Suche nach Sendungen (z. B. `?status=SHIPPED&carrier=DHL`) |              |

### Datenbank-Setup

Die Datei V1__initial_schema.sql enthält das Schema für Flyway.

Beispiel für lokale Ausführung:

docker exec -it wms-postgres psql -U wms -d wms

### Beispiel: docker-compose.yml

version: "3.8"
services:
 postgres:
  image: postgres:15
  container_name: wms-postgres
  environment:
   POSTGRES_DB: wmsdb
   POSTGRES_USER: wmsuser
   POSTGRES_PASSWORD: wmspassword
  ports:
   - "5432:5432"
  volumes:
   - postgres_data:/var/lib/postgresql/data
volumes:
 postgres_data:

### API-Datenfluss

POST /api/orders → Auftrag anlegen

POST /api/orders/{id}/shipment → Sendung erzeugen

POST /api/shipments/{id}/packages → Paket mit Tracking-Code registrieren

PUT /api/shipments/{id}/PACK

PUT /api/shipments/{id}/SHIP

### Tests

Unit- und Integrationstests können mit Maven ausgeführt werden:

mvn test

## Lizenz

Dieses Projekt ist zu Lern- und Demonstrationszwecken gedacht.
(c) 2025 – Open Source Example (MIT License)
