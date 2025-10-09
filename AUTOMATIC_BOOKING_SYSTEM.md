# Automatic Booking System

## Overview

When a new tour is created in the main tours table, the system automatically creates corresponding booking records in both the driver and guide booking tables for ALL existing drivers and guides.

## How It Works

### 1. Tour Creation Process

When a new tour is created via the `/api/tours/book` or `/api/tours/create` endpoints:

1. **Tour Record Created**: A new record is created in the `tours` table
2. **Driver Bookings Created**: For EVERY existing driver, a new booking record is created in `driver_bookings` table
3. **Guide Bookings Created**: For EVERY existing guide, a new booking record is created in `guide_bookings` table

### 2. Automatic Booking Creation

#### **For Drivers:**
- **Table**: `driver_bookings`
- **Process**: Loop through all drivers in `drivers` table
- **Booking ID Format**: `DB{timestamp}_{driverId}`
- **Status**: `PENDING` (default)
- **Fields Populated**:
  - `driver_id` - ID of the driver
  - `tour_name` - Name of the tour
  - `date` - Tour date
  - `number_of_people` - Number of people
  - `special_instruction` - Special instructions
  - `status` - PENDING
  - `action` - NULL (can be updated later)

#### **For Guides:**
- **Table**: `guide_bookings`
- **Process**: Loop through all guides in `guides` table
- **Booking ID Format**: `GB{timestamp}_{guideId}`
- **Status**: `PENDING` (default)
- **Fields Populated**:
  - `guide_id` - ID of the guide
  - `tour_name` - Name of the tour
  - `date` - Tour date
  - `number_of_people` - Number of people
  - `special_instruction` - Special instructions
  - `status` - PENDING
  - `action` - NULL (can be updated later)

### 3. API Endpoints

#### **Create Tour with Automatic Bookings:**

##### **POST** `/api/tours/book` (Existing endpoint - now with automatic bookings)
```json
{
  "tourName": "Yala National Park",
  "tourDate": "2024-02-15",
  "numberOfPeople": 4,
  "specialInstructions": "Vegetarian meals required",
  "userId": 1
}
```

##### **POST** `/api/tours/create` (New endpoint with DTO)
```json
{
  "tourName": "Sinharaja Forest",
  "tourDate": "2024-02-20",
  "numberOfPeople": 6,
  "specialInstructions": "Bird watching tour",
  "userId": 2
}
```

#### **Response:**
```json
{
  "success": true,
  "message": "Tour created successfully. Driver and guide bookings created automatically.",
  "tourId": 123
}
```

### 4. Database Impact

#### **Example Scenario:**
- **3 Drivers** in the system
- **2 Guides** in the system
- **1 New Tour** created

#### **Result:**
- **1 Tour** created in `tours` table
- **3 Driver Bookings** created in `driver_bookings` table
- **2 Guide Bookings** created in `guide_bookings` table
- **Total: 6 new booking records** created automatically

### 5. Code Implementation

#### **TourService.createTour() Method:**
```java
@Transactional
public Tour createTour(String tourName, LocalDate tourDate, Integer numberOfPeople, 
                      String specialInstructions, Long userId) {
    // Create the tour
    Tour tour = new Tour(tourName, tourDate, numberOfPeople, specialInstructions, userId);
    Tour savedTour = tourRepository.save(tour);
    
    // Create bookings for all drivers
    List<Driver> allDrivers = driverRepository.findAll();
    for (Driver driver : allDrivers) {
        driverBookingService.createBooking(
            driver.getId(),
            tourName,
            tourDate,
            numberOfPeople,
            specialInstructions
        );
    }
    
    // Create bookings for all guides
    List<Guide> allGuides = guideRepository.findAll();
    for (Guide guide : allGuides) {
        guideBookingService.createBooking(
            guide.getId(),
            tourName,
            tourDate,
            numberOfPeople,
            specialInstructions
        );
    }
    
    return savedTour;
}
```

### 6. Booking Status Management

#### **Initial Status:**
- All automatically created bookings start with status `PENDING`

#### **Status Updates:**
- Drivers and guides can update their booking status
- Available statuses: `PENDING`, `CONFIRMED`, `CANCELLED`, `COMPLETED`
- Actions can be added to track what was done

#### **API Endpoints for Status Updates:**

##### **Update Driver Booking Status:**
```
PUT /api/driver-bookings/{bookingId}/status
{
  "status": "CONFIRMED"
}
```

##### **Update Guide Booking Status:**
```
PUT /api/guide-bookings/{bookingId}/status
{
  "status": "CONFIRMED"
}
```

### 7. Querying Automatic Bookings

#### **Get All Driver Bookings:**
```
GET /api/driver-bookings
```

#### **Get Driver Bookings by Driver ID:**
```
GET /api/driver-bookings/driver/{driverId}
```

#### **Get Guide Bookings by Guide ID:**
```
GET /api/guide-bookings/guide/{guideId}
```

#### **Get Bookings by Status:**
```
GET /api/driver-bookings/status/PENDING
GET /api/guide-bookings/status/CONFIRMED
```

### 8. Benefits

- ✅ **Automatic Distribution**: Every new tour is automatically distributed to all drivers and guides
- ✅ **No Manual Work**: No need to manually create bookings for each driver/guide
- ✅ **Consistent Data**: All bookings have the same tour information
- ✅ **Status Tracking**: Each driver/guide can manage their own booking status
- ✅ **Scalable**: Works with any number of drivers and guides
- ✅ **Transactional**: All operations are wrapped in a transaction for data consistency

### 9. Example Workflow

1. **Admin creates a new tour** via `/api/tours/book`
2. **System automatically creates:**
   - 1 tour record in `tours` table
   - 5 driver booking records in `driver_bookings` table (if 5 drivers exist)
   - 3 guide booking records in `guide_bookings` table (if 3 guides exist)
3. **Drivers and guides see the new tour** in their respective booking tables
4. **They can update their status** to CONFIRMED, CANCELLED, etc.
5. **System tracks all responses** for the tour

### 10. Database Schema Impact

#### **Tables Affected:**
- `tours` - Main tour table
- `driver_bookings` - Individual driver booking table
- `guide_bookings` - Individual guide booking table

#### **Relationships:**
- `tours.user_id` → `users.id`
- `driver_bookings.driver_id` → `drivers.id`
- `guide_bookings.guide_id` → `guides.id`

This system ensures that every new tour is automatically distributed to all available drivers and guides, making tour management efficient and automated.

