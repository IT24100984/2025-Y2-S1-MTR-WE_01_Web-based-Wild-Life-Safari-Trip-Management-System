# Tourist Users Table Schema

## Table: `tourist_users`

The `tourist_users` table stores only users with role=TOURIST, containing only the essential fields you requested.

### Columns:

| Column Name | Data Type | Constraints | Description |
|-------------|-----------|-------------|-------------|
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Unique tourist user ID |
| `user_id` | BIGINT | NOT NULL, UNIQUE, FOREIGN KEY | Reference to users table |
| `email` | VARCHAR(255) | NOT NULL | Tourist's email address |
| `nationality` | VARCHAR(100) | NOT NULL | Tourist's nationality |
| `nic` | VARCHAR(20) | NOT NULL | Tourist's NIC number |
| `username` | VARCHAR(50) | NOT NULL | Tourist's username |

### Database Schema:

```sql
CREATE TABLE tourist_users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL,
    nationality VARCHAR(100) NOT NULL,
    nic VARCHAR(20) NOT NULL,
    username VARCHAR(50) NOT NULL
);
```

### Sample Data:

```sql
INSERT INTO tourist_users (user_id, email, nationality, nic, username) 
VALUES 
(1, 'john.doe@email.com', 'American', '123456789V', 'johndoe'),
(6, 'jane.smith@email.com', 'British', '987654321V', 'janesmith');
```

### Registration Flow:

#### **Tourist Registration:**
1. **User fills form** → Selects "Tourist" role, enters nationality
2. **User record created** → In `users` table
3. **Tourist record created** → In `tourists` table (full details)
4. **TouristUser record created** → In `tourist_users` table (simplified - only requested fields)
5. **Redirect** → Goes to login page

### API Endpoints:

#### **TouristUser Endpoints:**
- **GET** `/api/tourist-users` - Get all tourist users
- **GET** `/api/tourist-users/user/{userId}` - Get tourist user by user ID
- **GET** `/api/tourist-users/nationality/{nationality}` - Get tourist users by nationality
- **GET** `/api/tourist-users/email/{email}` - Get tourist user by email
- **GET** `/api/tourist-users/username/{username}` - Get tourist user by username
- **GET** `/api/tourist-users/nic/{nic}` - Get tourist user by NIC

### TouristUser Entity:

```java
@Entity
@Table(name = "tourist_users")
public class TouristUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;
    
    @Column(name = "email", nullable = false)
    private String email;
    
    @Column(name = "nationality", nullable = false)
    private String nationality;
    
    @Column(name = "nic", nullable = false)
    private String nic;
    
    @Column(name = "username", nullable = false)
    private String username;
}
```

### TouristUserService Methods:

```java
// Create tourist user
public TouristUser createTouristUser(Long userId, String email, String nationality, String nic, String username);

// Get all tourist users
public List<TouristUser> getAllTouristUsers();

// Get tourist user by user ID
public Optional<TouristUser> getTouristUserByUserId(Long userId);

// Get tourist users by nationality
public List<TouristUser> getTouristUsersByNationality(String nationality);

// Get tourist user by email
public Optional<TouristUser> getTouristUserByEmail(String email);

// Get tourist user by username
public Optional<TouristUser> getTouristUserByUsername(String username);

// Get tourist user by NIC
public Optional<TouristUser> getTouristUserByNic(String nic);
```

### What This Means:

- ✅ **Simplified Tourist Table**: Only contains users with role=TOURIST
- ✅ **Essential Fields Only**: Only the 5 fields you requested (id, user_id, email, nationality, nic, username)
- ✅ **Automatic Creation**: When a tourist registers, both `tourists` and `tourist_users` tables are populated
- ✅ **Easy Queries**: Simple queries for tourist user management
- ✅ **Clean Structure**: No unnecessary fields, focused on tourist users only




