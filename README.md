# STDISCM-PS4-parking-system

Distributed Parking System with Fault Tolerance for P4

## System Architecture

- **parking-auth-service** - JWT authentication & authorization
- **parking-catalog-service** - Parking lots & spots management
- **parking-reservation-service** - Parking spot reservations
- **parking-profile-service** - User profiles & management
- **parking-reporting-service** - Reservation history & reports
- **api-gateway** - Central routing with JWT validation
- **frontend-react** - React-based web interface

## Quick Start

1. Run `docker-compose up -d` to start all services
2. Access frontend at `http://localhost:3000`
3. API endpoints available on respective ports

## Fault Tolerance Features

- Independent microservices
- Health checks & graceful degradation
- Circuit breaker patterns
- Distributed session management
