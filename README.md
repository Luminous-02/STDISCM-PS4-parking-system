# STDISCM-PS4-parking-system

Distributed Parking System with Fault Tolerance for P4

## 🚗 System Overview

A microservices-based parking management system demonstrating distributed fault tolerance with:

- **parking-auth-service** - JWT authentication & authorization
- **parking-catalog-service** - Parking lots & spots management
- **frontend-react** - React-based web interface

## 🚨 Current Issues & Solutions

### Issue 1: Database Connection (UnknownHostException: postgres)

**Problem:** Services can't connect to PostgreSQL when running outside Docker
**Solution:** Update application.properties to use `localhost` instead of `postgres`

### Issue 2: Frontend Missing Files

**Problem:** `index.html` not found in public folder
**Solution:** Ensure all frontend files are properly structured

## 🛠️ Quick Setup Instructions

### Prerequisites

- Java 21
- Maven 3.9+
- Node.js 18+
- Docker & Docker Compose

### Method 1: Docker Compose (Recommended)

```bash
# 1. Start only database and Redis
docker-compose up -d postgres redis

# 2. Verify database is running
docker ps
# Should see: postgres and redis containers

# 3. Update application properties for local development
# Change in parking-auth-service/src/main/resources/application.properties:
# spring.datasource.url=jdbc:postgresql://localhost:5432/campus_parking_system?currentSchema=auth
#
# Change in parking-catalog-service/src/main/resources/application.properties:
# spring.datasource.url=jdbc:postgresql://localhost:5432/campus_parking_system?currentSchema=catalog

# 4. Start services manually in separate terminals
```
