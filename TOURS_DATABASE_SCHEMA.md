# Tours Database Schema

## Table: `tours`

The `tours` table stores tour booking information submitted by users.

### Columns:

| Column Name | Data Type | Constraints | Description |
|-------------|-----------|-------------|-------------|
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Unique tour booking ID |
| `tour_name` | VARCHAR(255) | NOT NULL | Name of the tour (e.g., "Yala Safari", "Wilpattu Safari") |
| `tour_date` | DATE | NOT NULL | Date selected by user for the tour |
| `number_of_people` | INT | NOT NULL | Number of people for the booking |
| `special_instructions` | TEXT | NULL | Special instructions or requests from user |
| `user_id` | BIGINT | NOT NULL | ID of the user who made the booking |
| `status` | VARCHAR(50) | NOT NULL, DEFAULT 'PENDING' | Booking status (PENDING, CONFIRMED, CANCELLED) |

### Sample Data:

```sql
INSERT INTO tours (tour_name, tour_date, number_of_people, special_instructions, user_id, status) 
VALUES 
('Yala Safari', '2024-02-15', 2, 'Vegetarian meals please', 1, 'PENDING'),
('Wilpattu Safari', '2024-02-20', 4, 'Early morning start preferred', 1, 'PENDING'),
('Udawalawe Safari', '2024-02-25', 1, 'Photography tour', 2, 'PENDING');
```

### API Endpoints:

- **POST** `/api/tours/book` - Create a new tour booking
- **GET** `/api/tours/user/{userId}` - Get all tours for a specific user
- **GET** `/api/tours/all` - Get all tour bookings (admin)

### Frontend Integration:

When a user submits a tour booking form:
1. Form data is sent to `/api/tours/book`
2. Data is saved to the `tours` table
3. Success message "Booking sent for confirmation" is displayed
4. Form is reset for new booking




