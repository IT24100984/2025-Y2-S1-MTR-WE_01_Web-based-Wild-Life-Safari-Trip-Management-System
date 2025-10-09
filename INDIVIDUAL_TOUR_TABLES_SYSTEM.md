# Individual Tour Tables System

## Overview

This system creates individual tables for each driver and guide that display all available tours. When one driver/guide accepts a tour, it disappears from all other driver/guide tables. Each tour can only have 1 driver and 1 guide assigned.

## Database Tables

### 1. Driver Tours Table (`driver_tours`)

```sql
CREATE TABLE driver_tours (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    driver_id BIGINT NOT NULL,
    tour_id BIGINT NOT NULL,
    tour_name VARCHAR(255) NOT NULL,
    tour_date DATE NOT NULL,
    number_of_people INT NOT NULL,
    special_instructions TEXT,
    tourist_id BIGINT NOT NULL,
    tourist_name VARCHAR(255) NOT NULL,
    tourist_contact VARCHAR(20) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'AVAILABLE',
    accepted_date DATE,
    created_date DATE NOT NULL
);
```

### 2. Guide Tours Table (`guide_tours`)

```sql
CREATE TABLE guide_tours (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    guide_id BIGINT NOT NULL,
    tour_id BIGINT NOT NULL,
    tour_name VARCHAR(255) NOT NULL,
    tour_date DATE NOT NULL,
    number_of_people INT NOT NULL,
    special_instructions TEXT,
    tourist_id BIGINT NOT NULL,
    tourist_name VARCHAR(255) NOT NULL,
    tourist_contact VARCHAR(20) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'AVAILABLE',
    accepted_date DATE,
    created_date DATE NOT NULL
);
```

## How It Works

### 1. Tour Creation Process

When a tourist creates a new tour:

1. **Tour Record Created**: A new record is created in the `tours` table
2. **Individual Driver Entries**: For EVERY existing driver, a new entry is created in `driver_tours` table
3. **Individual Guide Entries**: For EVERY existing guide, a new entry is created in `guide_tours` table
4. **All entries start with status**: `AVAILABLE`

### 2. Tour Acceptance Process

#### **Driver Accepts Tour:**
1. **Driver accepts tour** via `/api/driver-tours/accept`
2. **Tour status changes** to `ACCEPTED` for that driver
3. **Tour disappears** from all other drivers' tables (status changes to `REMOVED`)
4. **Only 1 driver** can be assigned to each tour

#### **Guide Accepts Tour:**
1. **Guide accepts tour** via `/api/guide-tours/accept`
2. **Tour status changes** to `ACCEPTED` for that guide
3. **Tour disappears** from all other guides' tables (status changes to `REMOVED`)
4. **Only 1 guide** can be assigned to each tour

### 3. Dashboard Display

#### **Driver Dashboard:**
- **Available Tours**: Shows all tours with status `AVAILABLE`
- **Accepted Tours**: Shows tours accepted by this driver
- **Tour Details**: Tour name, date, number of people, special instructions, tourist details

#### **Guide Dashboard:**
- **Available Tours**: Shows all tours with status `AVAILABLE`
- **Accepted Tours**: Shows tours accepted by this guide
- **Tour Details**: Tour name, date, number of people, special instructions, tourist details

#### **Tourist Dashboard:**
- **Assigned Driver**: Shows details of the driver assigned to their tours
- **Assigned Guide**: Shows details of the guide assigned to their tours
- **Tour Status**: Shows which tours have been accepted by drivers/guides

## API Endpoints

### Driver Tours API (`/api/driver-tours`)

#### **Get Tours for Driver:**
- **GET** `/api/driver-tours/driver/{driverId}` - All tours for a driver
- **GET** `/api/driver-tours/driver/{driverId}/available` - Available tours for a driver
- **GET** `/api/driver-tours/driver/{driverId}/accepted` - Accepted tours for a driver

#### **Accept Tour:**
- **POST** `/api/driver-tours/accept`
```json
{
  "driverId": 1,
  "tourId": 123
}
```

#### **Get Tour Assignments:**
- **GET** `/api/driver-tours/tour/{tourId}/accepted` - Get accepted driver for a tour
- **GET** `/api/driver-tours/tourist/{touristId}` - Get tours by tourist

### Guide Tours API (`/api/guide-tours`)

#### **Get Tours for Guide:**
- **GET** `/api/guide-tours/guide/{guideId}` - All tours for a guide
- **GET** `/api/guide-tours/guide/{guideId}/available` - Available tours for a guide
- **GET** `/api/guide-tours/guide/{guideId}/accepted` - Accepted tours for a guide

#### **Accept Tour:**
- **POST** `/api/guide-tours/accept`
```json
{
  "guideId": 1,
  "tourId": 123
}
```

#### **Get Tour Assignments:**
- **GET** `/api/guide-tours/tour/{tourId}/accepted` - Get accepted guide for a tour
- **GET** `/api/guide-tours/tourist/{touristId}` - Get tours by tourist

### Tour Assignments API (`/api/tour-assignments`)

#### **Get Tour Assignments:**
- **GET** `/api/tour-assignments/tour/{tourId}` - Get assigned driver and guide for a tour
- **GET** `/api/tour-assignments/tourist/{touristId}/drivers` - Get driver assignments for tourist
- **GET** `/api/tour-assignments/tourist/{touristId}/guides` - Get guide assignments for tourist

## Example Workflow

### 1. Tourist Creates Tour
```json
POST /api/tours/book
{
  "tourName": "Yala National Park",
  "tourDate": "2024-02-15",
  "numberOfPeople": 4,
  "specialInstructions": "Vegetarian meals required",
  "userId": 1
}
```

**Result:**
- 1 tour created in `tours` table
- 3 entries created in `driver_tours` table (if 3 drivers exist)
- 2 entries created in `guide_tours` table (if 2 guides exist)
- All entries have status `AVAILABLE`

### 2. Driver Accepts Tour
```json
POST /api/driver-tours/accept
{
  "driverId": 1,
  "tourId": 123
}
```

**Result:**
- Driver 1's entry status changes to `ACCEPTED`
- All other drivers' entries for this tour are deleted
- Tour disappears from other drivers' dashboards

### 3. Guide Accepts Tour
```json
POST /api/guide-tours/accept
{
  "guideId": 2,
  "tourId": 123
}
```

**Result:**
- Guide 2's entry status changes to `ACCEPTED`
- All other guides' entries for this tour are deleted
- Tour disappears from other guides' dashboards

### 4. Tourist Views Assignments
```json
GET /api/tour-assignments/tour/123
```

**Response:**
```json
{
  "driver": {
    "driverId": 1,
    "licenseNumber": "DL123456",
    "vehicleType": "Jeep",
    "experienceYears": 5,
    "languages": "English, Sinhala",
    "description": "Experienced safari driver",
    "rating": 4.8,
    "acceptedDate": "2024-01-15"
  },
  "guide": {
    "guideId": 2,
    "experienceYears": 3,
    "languages": "English, Tamil",
    "specializations": "Bird Watching, Photography",
    "description": "Wildlife expert",
    "rating": 4.9,
    "acceptedDate": "2024-01-15"
  }
}
```

## Dashboard Integration

### Driver Dashboard Features:
- **Available Tours List**: Shows all tours available to accept
- **Accept Tour Button**: Allows driver to accept a tour
- **Accepted Tours List**: Shows tours the driver has accepted
- **Tour Details**: Full tour information including tourist contact details

### Guide Dashboard Features:
- **Available Tours List**: Shows all tours available to accept
- **Accept Tour Button**: Allows guide to accept a tour
- **Accepted Tours List**: Shows tours the guide has accepted
- **Tour Details**: Full tour information including tourist contact details

### Tourist Dashboard Features:
- **My Tours List**: Shows all tours created by the tourist
- **Assigned Driver**: Shows driver details for each tour
- **Assigned Guide**: Shows guide details for each tour
- **Tour Status**: Shows which tours have been accepted

## Status Values

- **AVAILABLE**: Tour is available for acceptance
- **ACCEPTED**: Tour has been accepted by a driver/guide
- **COMPLETED**: Tour has been completed
- **CANCELLED**: Tour has been cancelled

## Benefits

- ✅ **Individual Tables**: Each driver/guide has their own tour table
- ✅ **Automatic Distribution**: New tours appear in all driver/guide dashboards
- ✅ **Exclusive Assignment**: Only 1 driver and 1 guide per tour
- ✅ **Real-time Updates**: Tours disappear from other dashboards when accepted
- ✅ **Complete Information**: Full tourist details available to drivers/guides
- ✅ **Assignment Tracking**: Tourists can see assigned driver/guide details
- ✅ **Status Management**: Clear status tracking for all tours
- ✅ **Dashboard Integration**: Seamless integration with existing dashboards

This system ensures that every new tour is distributed to all drivers and guides, but only one of each can be assigned to each tour, providing a fair and efficient tour assignment system.




