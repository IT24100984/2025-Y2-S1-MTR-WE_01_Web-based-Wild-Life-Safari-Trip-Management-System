# Dashboard Integration Guide

## Overview

The guide and driver dashboards now display all pending tours that are available for acceptance. When a guide or driver accepts a tour, it disappears from all other guides' and drivers' dashboards.

## How It Works

### 1. Tour Creation
When a tourist creates a new tour:
- ✅ **Tour appears in ALL guide dashboards**
- ✅ **Tour appears in ALL driver dashboards**
- ✅ **Each guide/driver gets their own entry** in their respective table
- ✅ **All entries start with status `AVAILABLE`**

### 2. Tour Acceptance
When a guide or driver accepts a tour:
- ✅ **Tour status changes to `ACCEPTED`** for the accepting guide/driver
- ✅ **Tour disappears from ALL other guides' dashboards**
- ✅ **Tour disappears from ALL other drivers' dashboards**
- ✅ **Only 1 guide and 1 driver** can be assigned to each tour

## Testing the System

### Step 1: Create Sample Data

First, ensure you have some drivers and guides in your database. You can register them through the signup page:

1. **Register a Driver:**
   - Go to `/signup`
   - Fill in driver details
   - Select "Driver" role
   - Complete registration

2. **Register a Guide:**
   - Go to `/signup`
   - Fill in guide details
   - Select "Guide" role
   - Complete registration

3. **Register a Tourist:**
   - Go to `/signup`
   - Fill in tourist details
   - Select "Tourist" role
   - Complete registration

### Step 2: Create a Sample Tour

#### **Option A: Use the Test Endpoint**
```bash
POST http://localhost:8080/api/test/create-sample-tour
```

This will create a sample tour that will automatically appear in all guide and driver dashboards.

#### **Option B: Create Tour via Tourist**
1. Login as a tourist
2. Go to any tour page (e.g., `/yala-tour`)
3. Fill in tour details and submit
4. Tour will be created and distributed to all guides and drivers

### Step 3: View Guide Dashboard

1. **Navigate to Guide Dashboard:**
   ```
   http://localhost:8080/guide-dashboard
   ```

2. **What You'll See:**
   - **Available Tours Table**: Shows all tours available for acceptance
   - **Tour Details**: Tour name, date, number of people, special instructions
   - **Accept Button**: Allows guide to accept a tour
   - **Status**: Shows "AVAILABLE" for pending tours

3. **Accept a Tour:**
   - Click "Accept Tour" button
   - Tour status changes to "Tour Accepted!"
   - Tour disappears from other guides' dashboards

### Step 4: View Driver Dashboard

1. **Navigate to Driver Dashboard:**
   ```
   http://localhost:8080/driver-dashboard
   ```

2. **What You'll See:**
   - **Available Tours Table**: Shows all tours available for acceptance
   - **Tour Details**: Tour name, date, number of people, special instructions
   - **Accept Button**: Allows driver to accept a tour
   - **Status**: Shows "AVAILABLE" for pending tours

3. **Accept a Tour:**
   - Click "Accept Tour" button
   - Tour status changes to "Tour Accepted!"
   - Tour disappears from other drivers' dashboards

## API Endpoints for Testing

### Get Available Tours for Guide
```bash
GET http://localhost:8080/api/guide-tours/guide/1/available
```

### Get Available Tours for Driver
```bash
GET http://localhost:8080/api/driver-tours/driver/1/available
```

### Accept Tour (Guide)
```bash
POST http://localhost:8080/api/guide-tours/accept
Content-Type: application/json

{
  "guideId": 1,
  "tourId": 123
}
```

### Accept Tour (Driver)
```bash
POST http://localhost:8080/api/driver-tours/accept
Content-Type: application/json

{
  "driverId": 1,
  "tourId": 123
}
```

### Get Tour Assignments
```bash
GET http://localhost:8080/api/tour-assignments/tour/123
```

## Expected Behavior

### 1. Initial State
- **All guides see the same tours** in their dashboards
- **All drivers see the same tours** in their dashboards
- **Tours have status "AVAILABLE"**

### 2. After Guide Accepts Tour
- **Accepting guide sees "Tour Accepted!"**
- **All other guides see the tour disappear**
- **All drivers still see the tour** (until a driver accepts)

### 3. After Driver Accepts Tour
- **Accepting driver sees "Tour Accepted!"**
- **All other drivers see the tour disappear**
- **Tour is now fully assigned** (1 guide + 1 driver)

### 4. Tourist View
- **Tourist can see assigned guide and driver details**
- **Tourist gets confirmation of assignments**

## Troubleshooting

### If No Tours Appear
1. **Check if drivers/guides exist:**
   ```bash
   GET http://localhost:8080/api/driver-tours/all
   GET http://localhost:8080/api/guide-tours/all
   ```

2. **Create a sample tour:**
   ```bash
   POST http://localhost:8080/api/test/create-sample-tour
   ```

3. **Check database tables:**
   - `driver_tours` table should have entries
   - `guide_tours` table should have entries

### If Tours Don't Disappear After Acceptance
1. **Check tour status:**
   ```bash
   GET http://localhost:8080/api/guide-tours/guide/1/available
   GET http://localhost:8080/api/driver-tours/driver/1/available
   ```

2. **Verify acceptance worked:**
   ```bash
   GET http://localhost:8080/api/guide-tours/tour/123/accepted
   GET http://localhost:8080/api/driver-tours/tour/123/accepted
   ```

## Database Tables to Check

### Driver Tours Table
```sql
SELECT * FROM driver_tours WHERE driver_id = 1;
```

### Guide Tours Table
```sql
SELECT * FROM guide_tours WHERE guide_id = 1;
```

### Main Tours Table
```sql
SELECT * FROM tours;
```

## Success Indicators

✅ **Guide Dashboard shows available tours**
✅ **Driver Dashboard shows available tours**
✅ **Tours disappear from other dashboards when accepted**
✅ **Only one guide and one driver per tour**
✅ **Tourist can see assigned guide and driver details**
✅ **Real-time updates when tours are accepted**

This system ensures that every new tour is distributed to all guides and drivers, but only one of each can be assigned to each tour, providing a fair and efficient tour assignment system.




