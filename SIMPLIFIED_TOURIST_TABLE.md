# Simplified Tourist Table Schema

## Table: `tourists`

The `tourists` table stores basic tourist information for users with TOURIST role.

### Columns:

| Column Name | Data Type | Constraints | Description |
|-------------|-----------|-------------|-------------|
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Unique tourist ID |
| `user_id` | BIGINT | NOT NULL, FOREIGN KEY | Reference to users table |
| `email` | VARCHAR(255) | NOT NULL | Tourist's email address |
| `nationality` | VARCHAR(100) | NOT NULL | Tourist's nationality |
| `first_name` | VARCHAR(100) | NOT NULL | Tourist's first name |
| `last_name` | VARCHAR(100) | NOT NULL | Tourist's last name |
| `contact_number` | VARCHAR(20) | NOT NULL | Tourist's contact number |
| `nic` | VARCHAR(20) | NOT NULL | Tourist's NIC number |
| `username` | VARCHAR(50) | NOT NULL | Tourist's username |

### Sample Data:

```sql
INSERT INTO tourists (user_id, email, nationality, first_name, last_name, contact_number, nic, username) 
VALUES 
(1, 'john.doe@email.com', 'American', 'John', 'Doe', '+1234567890', '123456789V', 'johndoe'),
(6, 'jane.smith@email.com', 'British', 'Jane', 'Smith', '+44123456789', '987654321V', 'janesmith');
```

### Database Schema:

```sql
CREATE TABLE tourists (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    email VARCHAR(255) NOT NULL,
    nationality VARCHAR(100) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    contact_number VARCHAR(20) NOT NULL,
    nic VARCHAR(20) NOT NULL,
    username VARCHAR(50) NOT NULL
);
```

### Registration Flow:

#### **Tourist Registration:**
1. **User fills form** → Selects "Tourist" role, enters nationality
2. **User record created** → In `users` table
3. **Tourist record created** → In `tourists` table with:
   - User ID (from users table)
   - Email (from users table)
   - Nationality (from form)
   - First name (from users table)
   - Last name (from users table)
   - Contact number (from users table)
   - NIC (from users table)
   - Username (from users table)
4. **Redirect** → Goes to login page (tourists use general dashboard)

### API Endpoints:

#### **Tourist Endpoints:**
- **GET** `/api/tourists` - Get all tourists
- **GET** `/api/tourists/user/{userId}` - Get tourist by user ID
- **GET** `/api/tourists/nationality/{nationality}` - Get tourists by nationality

### Tourist Entity:

```java
@Entity
@Table(name = "tourists")
public class Tourist {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "email", nullable = false)
    private String email;
    
    @Column(name = "nationality", nullable = false)
    private String nationality;
    
    @Column(name = "first_name", nullable = false)
    private String firstName;
    
    @Column(name = "last_name", nullable = false)
    private String lastName;
    
    @Column(name = "contact_number", nullable = false)
    private String contactNumber;
    
    @Column(name = "nic", nullable = false)
    private String nic;
    
    @Column(name = "username", nullable = false)
    private String username;
}
```

### TouristService Methods:

```java
// Create tourist
public Tourist createTourist(Long userId, String email, String nationality, 
                           String firstName, String lastName, String contactNumber, 
                           String nic, String username);

// Get all tourists
public List<Tourist> getAllTourists();

// Get tourist by user ID
public Optional<Tourist> getTouristByUserId(Long userId);

// Get tourists by nationality
public List<Tourist> getTouristsByNationality(String nationality);
```

### What This Means:

- ✅ **Simplified Structure**: Only essential tourist information stored
- ✅ **Basic User Data**: All basic user fields duplicated in tourist table
- ✅ **Nationality Tracking**: Tourist's nationality is tracked
- ✅ **Easy Queries**: Simple queries for tourist management
- ✅ **Registration Works**: Tourist registration will create both user and tourist records




