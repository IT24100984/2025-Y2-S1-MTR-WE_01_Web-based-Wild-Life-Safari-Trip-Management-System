# Safari Backend Setup Guide

## Overview
This backend system provides a complete booking management system for a wildlife safari tour company. It includes driver management, guide management, tour management, and booking functionality.

## Database Setup

### 1. SQL Server Setup
1. Install SQL Server (Express, Developer, or Enterprise edition)
2. Enable SQL Server Authentication
3. Run the `database_setup.sql` script to create the database and user

### 2. Database Configuration
- **Database Name**: safari_db
- **Username**: safari_user
- **Password**: 234
- **Port**: 1433 (default)

## Project Structure

```
src/main/java/com/safari/safari_2/drivermanagement/
├── config/
│   └── DatabaseConfig.java          # Database configuration
├── controller/
│   ├── BookingController.java       # Booking API endpoints
│   ├── DriverController.java        # Driver API endpoints
│   ├── GuideController.java         # Guide API endpoints
│   └── TourController.java          # Tour API endpoints
├── dto/
│   ├── BookingAcceptanceDTO.java    # Booking acceptance DTO
│   ├── BookingRequestDTO.java       # Booking request DTO
│   └── BookingResponseDTO.java      # Booking response DTO
├── model/
│   ├── Booking.java                 # Booking entity
│   ├── BookingStatus.java           # Booking status enum
│   ├── Driver.java                  # Driver entity
│   ├── Guide.java                   # Guide entity
│   └── Tour.java                    # Tour entity
├── repository/
│   ├── BookingRepository.java       # Booking data access
│   ├── DriverRepository.java         # Driver data access
│   ├── GuideRepository.java          # Guide data access
│   └── TourRepository.java           # Tour data access
└── service/
    ├── BookingService.java           # Booking business logic
    ├── DataInitializationService.java # Data initialization
    ├── DriverService.java            # Driver business logic
    ├── GuideService.java             # Guide business logic
    └── TourService.java              # Tour business logic
```

## API Endpoints

### Booking Endpoints
- `POST /api/bookings/create` - Create a new booking
- `GET /api/bookings` - Get all bookings
- `GET /api/bookings/{id}` - Get booking by ID
- `GET /api/bookings/driver/{driverId}` - Get bookings by driver
- `GET /api/bookings/guide/{guideId}` - Get bookings by guide
- `POST /api/bookings/{id}/accept` - Accept booking (driver/guide)
- `POST /api/bookings/{id}/reject` - Reject booking (driver/guide)

### Tour Endpoints
- `GET /api/tours` - Get all tours
- `GET /api/tours/active` - Get active tours
- `GET /api/tours/{id}` - Get tour by ID
- `POST /api/tours` - Create tour
- `PUT /api/tours/{id}` - Update tour
- `DELETE /api/tours/{id}` - Delete tour

### Driver Endpoints
- `GET /api/drivers` - Get all drivers
- `GET /api/drivers/{id}` - Get driver by ID
- `POST /api/drivers` - Create driver
- `PUT /api/drivers/{id}` - Update driver
- `DELETE /api/drivers/{id}` - Delete driver

### Guide Endpoints
- `GET /api/guides` - Get all guides
- `GET /api/guides/available` - Get available guides
- `GET /api/guides/{id}` - Get guide by ID
- `POST /api/guides` - Create guide
- `PUT /api/guides/{id}` - Update guide
- `PUT /api/guides/{id}/description` - Update guide description

## Running the Application

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- SQL Server running on localhost:1433

### Steps
1. **Setup Database**:
   ```sql
   -- Run the database_setup.sql script
   ```

2. **Build and Run**:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

3. **Access Application**:
   - Backend API: http://localhost:8080
   - Frontend: http://localhost:8080/static/home.html

## Frontend Integration

The backend is designed to work with the existing frontend HTML files:

### Home Page Flow
1. User visits `home.html`
2. Clicks "Explore Tour" → goes to `exploretour.html`
3. Selects a tour (e.g., Yala Safari) → goes to `yala_tour.html`
4. Fills booking form and clicks "Book Tour"
5. Booking is created and assigned to driver/guide
6. Driver/Guide can view bookings in their respective dashboards

### Booking Process
1. **Tour Selection**: User selects from available tours
2. **Booking Creation**: Form data is sent to `/api/bookings/create`
3. **Driver/Guide Assignment**: System automatically assigns available driver and guide
4. **Status Tracking**: Booking status is tracked through the system
5. **Dashboard Updates**: Driver and guide dashboards show assigned bookings

## Sample Data

The application automatically initializes with sample data:
- **3 Drivers**: John Doe, Mike Johnson, David Wilson
- **3 Guides**: Jane Smith, Sarah Brown, Tom Davis
- **6 Tours**: Yala, Wilpattu, Udawalawe, Minneriya, Kumana, Sinharaja

## Database Tables

The following tables are automatically created by Hibernate:
- `bookings` - Booking information
- `drivers` - Driver information
- `guides` - Guide information
- `tours` - Tour information

## Troubleshooting

### Common Issues
1. **Database Connection**: Ensure SQL Server is running and accessible
2. **Port Conflicts**: Check if port 8080 is available
3. **CORS Issues**: The application includes CORS configuration for frontend integration

### Logs
- Application logs are available in the console
- SQL queries are logged when `spring.jpa.show-sql=true`

## Security Notes
- Database credentials are in `application.properties`
- CORS is configured for development (should be restricted in production)
- No authentication is implemented (add Spring Security for production)

## Next Steps
1. Add authentication and authorization
2. Implement email notifications
3. Add payment processing
4. Implement real-time updates
5. Add comprehensive logging and monitoring

