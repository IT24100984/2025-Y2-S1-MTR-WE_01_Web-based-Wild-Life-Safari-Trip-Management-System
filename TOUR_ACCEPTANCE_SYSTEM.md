# Tour Acceptance and Removal System

## Overview

The system ensures that when a driver or guide accepts a tour, it is automatically removed from all other drivers' and guides' dashboards and database tables. This prevents multiple drivers/guides from accepting the same tour.

## How It Works

### 1. Tour Creation Process

When a new tour is created:

1. **Main Tour Created**: A tour is created in the main `tours` table
2. **Driver Tours Created**: Individual `DriverTour` entries are created for ALL drivers
3. **Guide Tours Created**: Individual `GuideTour` entries are created for ALL guides
4. **Status Set to AVAILABLE**: All entries start with status "AVAILABLE"

### 2. Tour Acceptance Process

#### **Driver Accepts Tour:**

1. **Validation**: System checks if tour is still available for this driver
2. **Accept Tour**: Driver's tour entry status changes to "ACCEPTED"
3. **Remove from Others**: All other drivers' entries for this tour are DELETED
4. **Update Main Tour**: Main tour table gets assigned driver ID
5. **Real-time Update**: All driver dashboards refresh to show updated data

#### **Guide Accepts Tour:**

1. **Validation**: System checks if tour is still available for this guide
2. **Accept Tour**: Guide's tour entry status changes to "ACCEPTED"
3. **Remove from Others**: All other guides' entries for this tour are DELETED
4. **Update Main Tour**: Main tour table gets assigned guide ID
5. **Real-time Update**: All guide dashboards refresh to show updated data

## Database Schema

### Main Tour Table (`tours`)
```sql
CREATE TABLE tours (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    tour_name NVARCHAR(255) NOT NULL,
    tour_date DATE NOT NULL,
    number_of_people INT NOT NULL,
    special_instructions NVARCHAR(MAX),
    user_id BIGINT NOT NULL,
    assigned_driver_id BIGINT NULL,
    assigned_guide_id BIGINT NULL,
    status NVARCHAR(50) DEFAULT 'PENDING'
);
```

### Driver Tours Table (`driver_tours`)
```sql
CREATE TABLE driver_tours (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    driver_id BIGINT NOT NULL,
    tour_id BIGINT NOT NULL,
    tour_name NVARCHAR(255) NOT NULL,
    tour_date DATE NOT NULL,
    number_of_people INT NOT NULL,
    special_instructions NVARCHAR(MAX),
    tourist_id BIGINT NOT NULL,
    tourist_name NVARCHAR(255) NOT NULL,
    tourist_contact NVARCHAR(255) NOT NULL,
    status NVARCHAR(50) DEFAULT 'AVAILABLE',
    accepted_date DATE NULL
);
```

### Guide Tours Table (`guide_tours`)
```sql
CREATE TABLE guide_tours (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    guide_id BIGINT NOT NULL,
    tour_id BIGINT NOT NULL,
    tour_name NVARCHAR(255) NOT NULL,
    tour_date DATE NOT NULL,
    number_of_people INT NOT NULL,
    special_instructions NVARCHAR(MAX),
    tourist_id BIGINT NOT NULL,
    tourist_name NVARCHAR(255) NOT NULL,
    tourist_contact NVARCHAR(255) NOT NULL,
    status NVARCHAR(50) DEFAULT 'AVAILABLE',
    accepted_date DATE NULL
);
```

## Implementation Details

### 1. Tour Creation Service

```java
@Transactional
public Tour createTour(String tourName, LocalDate tourDate, Integer numberOfPeople,
                       String specialInstructions, Long userId) {
    // Create main tour
    Tour tour = new Tour(tourName, tourDate, numberOfPeople, specialInstructions, userId);
    Tour savedTour = tourRepository.save(tour);
    
    // Create driver tours for ALL drivers
    driverTourService.createToursForAllDrivers(
        savedTour.getId(), tourName, tourDate, numberOfPeople,
        specialInstructions, userId, touristName, touristContact
    );
    
    // Create guide tours for ALL guides
    guideTourService.createToursForAllGuides(
        savedTour.getId(), tourName, tourDate, numberOfPeople,
        specialInstructions, userId, touristName, touristContact
    );
    
    return savedTour;
}
```

### 2. Driver Tour Acceptance

```java
@Transactional
public boolean acceptTour(Long driverId, Long tourId) {
    // Check if tour is available for this driver
    Optional<DriverTour> driverTourOptional = driverTourRepository.findByDriverIdAndTourId(driverId, tourId);
    if (!driverTourOptional.isPresent() || !"AVAILABLE".equals(driverTourOptional.get().getStatus())) {
        return false; // Tour not available
    }
    
    // Check if tour is already accepted by another driver
    Optional<DriverTour> alreadyAccepted = driverTourRepository.findByTourIdAndStatus(tourId, "ACCEPTED");
    if (alreadyAccepted.isPresent()) {
        return false; // Tour already accepted by another driver
    }
    
    // Accept the tour
    driverTourRepository.updateDriverTourStatus(driverId, tourId, "ACCEPTED", LocalDate.now());
    
    // Remove tour from all other drivers
    driverTourRepository.deleteAvailableToursByTourId(tourId);
    
    // Update main tour with assigned driver
    tourRepository.updateAssignedDriver(tourId, driverId);
    
    return true;
}
```

### 3. Guide Tour Acceptance

```java
@Transactional
public boolean acceptTour(Long guideId, Long tourId) {
    // Check if tour is available for this guide
    Optional<GuideTour> guideTourOptional = guideTourRepository.findByGuideIdAndTourId(guideId, tourId);
    if (!guideTourOptional.isPresent() || !"AVAILABLE".equals(guideTourOptional.get().getStatus())) {
        return false; // Tour not available
    }
    
    // Check if tour is already accepted by another guide
    Optional<GuideTour> alreadyAccepted = guideTourRepository.findByTourIdAndStatus(tourId, "ACCEPTED");
    if (alreadyAccepted.isPresent()) {
        return false; // Tour already accepted by another guide
    }
    
    // Accept the tour
    guideTourRepository.updateGuideTourStatus(guideId, tourId, "ACCEPTED", LocalDate.now());
    
    // Remove tour from all other guides
    guideTourRepository.deleteAvailableToursByTourId(tourId);
    
    // Update main tour with assigned guide
    tourRepository.updateAssignedGuide(tourId, guideId);
    
    return true;
}
```

## Repository Methods

### Driver Tour Repository

```java
@Repository
public interface DriverTourRepository extends JpaRepository<DriverTour, Long> {
    
    // Find tours by driver ID
    List<DriverTour> findByDriverId(Long driverId);
    
    // Find available tours by driver ID
    List<DriverTour> findByDriverIdAndStatus(Long driverId, String status);
    
    // Find specific driver's tour
    Optional<DriverTour> findByDriverIdAndTourId(Long driverId, Long tourId);
    
    // Check if tour is already accepted
    Optional<DriverTour> findByTourIdAndStatus(Long tourId, String status);
    
    // Update tour status
    @Modifying
    @Query("UPDATE DriverTour dt SET dt.status = :status, dt.acceptedDate = :acceptedDate WHERE dt.driverId = :driverId AND dt.tourId = :tourId")
    void updateDriverTourStatus(@Param("driverId") Long driverId, @Param("tourId") Long tourId, 
                               @Param("status") String status, @Param("acceptedDate") LocalDate acceptedDate);
    
    // Delete all available tours for a specific tour ID
    @Modifying
    @Query("DELETE FROM DriverTour dt WHERE dt.tourId = :tourId AND dt.status = 'AVAILABLE'")
    void deleteAvailableToursByTourId(@Param("tourId") Long tourId);
}
```

### Guide Tour Repository

```java
@Repository
public interface GuideTourRepository extends JpaRepository<GuideTour, Long> {
    
    // Find tours by guide ID
    List<GuideTour> findByGuideId(Long guideId);
    
    // Find available tours by guide ID
    List<GuideTour> findByGuideIdAndStatus(Long guideId, String status);
    
    // Find specific guide's tour
    Optional<GuideTour> findByGuideIdAndTourId(Long guideId, Long tourId);
    
    // Check if tour is already accepted
    Optional<GuideTour> findByTourIdAndStatus(Long tourId, String status);
    
    // Update tour status
    @Modifying
    @Query("UPDATE GuideTour gt SET gt.status = :status, gt.acceptedDate = :acceptedDate WHERE gt.guideId = :guideId AND gt.tourId = :tourId")
    void updateGuideTourStatus(@Param("guideId") Long guideId, @Param("tourId") Long tourId, 
                              @Param("status") String status, @Param("acceptedDate") LocalDate acceptedDate);
    
    // Delete all available tours for a specific tour ID
    @Modifying
    @Query("DELETE FROM GuideTour gt WHERE gt.tourId = :tourId AND gt.status = 'AVAILABLE'")
    void deleteAvailableToursByTourId(@Param("tourId") Long tourId);
}
```

## API Endpoints

### Tour Management
- **POST** `/api/tours/create` - Create new tour (automatically creates driver/guide entries)
- **GET** `/api/tours/all` - Get all tours
- **GET** `/api/tours/user/{userId}` - Get tours by user ID

### Driver Tours
- **GET** `/api/driver-tours/driver/{driverId}` - Get all tours for driver
- **GET** `/api/driver-tours/driver/{driverId}/available` - Get available tours for driver
- **GET** `/api/driver-tours/driver/{driverId}/accepted` - Get accepted tours for driver
- **POST** `/api/driver-tours/accept` - Accept tour as driver

### Guide Tours
- **GET** `/api/guide-tours/guide/{guideId}` - Get all tours for guide
- **GET** `/api/guide-tours/guide/{guideId}/available` - Get available tours for guide
- **GET** `/api/guide-tours/guide/{guideId}/accepted` - Get accepted tours for guide
- **POST** `/api/guide-tours/accept` - Accept tour as guide

### Testing Endpoints
- **POST** `/api/test-tours/create-sample-tour` - Create sample tour for testing
- **GET** `/api/test-tours/driver-tours/all` - Get all driver tours
- **GET** `/api/test-tours/guide-tours/all` - Get all guide tours

## Testing the System

### Step 1: Create Sample Tour

```bash
# Create a sample tour
curl -X POST http://localhost:8080/api/test-tours/create-sample-tour
```

### Step 2: Verify Tour Distribution

```bash
# Check all driver tours
curl http://localhost:8080/api/test-tours/driver-tours/all

# Check all guide tours
curl http://localhost:8080/api/test-tours/guide-tours/all
```

### Step 3: Test Tour Acceptance

#### **Driver Accepts Tour:**
1. **Login as Driver**: Go to `/login` and login with driver credentials
2. **View Dashboard**: Should see the new tour in available tours
3. **Accept Tour**: Click "Accept" button
4. **Verify Removal**: Tour should disappear from all other driver dashboards
5. **Check Database**: Only one driver should have this tour (status: ACCEPTED)

#### **Guide Accepts Tour:**
1. **Login as Guide**: Go to `/login` and login with guide credentials
2. **View Dashboard**: Should see the new tour in available tours
3. **Accept Tour**: Click "Accept" button
4. **Verify Removal**: Tour should disappear from all other guide dashboards
5. **Check Database**: Only one guide should have this tour (status: ACCEPTED)

### Step 4: Verify Database State

#### **After Driver Acceptance:**
- **Main Tour Table**: `assigned_driver_id` should be set
- **Driver Tours Table**: Only one entry with status "ACCEPTED"
- **Other Driver Entries**: Should be deleted

#### **After Guide Acceptance:**
- **Main Tour Table**: `assigned_guide_id` should be set
- **Guide Tours Table**: Only one entry with status "ACCEPTED"
- **Other Guide Entries**: Should be deleted

## Key Features

### ✅ **Automatic Tour Distribution**
- New tours automatically appear in ALL driver and guide dashboards
- No manual assignment needed

### ✅ **Exclusive Acceptance**
- Only one driver can accept a tour
- Only one guide can accept a tour
- Prevents double-booking

### ✅ **Automatic Removal**
- When a driver accepts, tour disappears from all other driver dashboards
- When a guide accepts, tour disappears from all other guide dashboards
- Database entries are automatically cleaned up

### ✅ **Real-time Updates**
- Dashboards automatically refresh to show current state
- No manual refresh needed

### ✅ **Transaction Safety**
- All operations are wrapped in `@Transactional`
- Database consistency is maintained
- Rollback on errors

## Benefits

1. **Prevents Double-Booking**: Only one driver and one guide per tour
2. **Automatic Cleanup**: No orphaned database entries
3. **Real-time Updates**: Dashboards always show current state
4. **Scalable**: Works with any number of drivers/guides
5. **Reliable**: Transaction-based operations ensure data consistency

This system ensures that tours are properly distributed and managed, preventing conflicts and ensuring each tour has exactly one assigned driver and one assigned guide.




