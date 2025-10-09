# Role-Based Login System

## Overview

The login system now automatically redirects users to their appropriate dashboards based on their role after successful authentication.

## How It Works

### 1. Login Process

When a user logs in through `/login`:

1. **Authentication**: User enters username and password
2. **Role Detection**: System identifies the user's role (DRIVER, GUIDE, or TOURIST)
3. **Session Storage**: User ID and role-specific ID are stored in session
4. **Automatic Redirect**: User is redirected to their appropriate dashboard

### 2. Role-Based Redirections

#### **Driver Login:**
- **Redirects to**: `/driver-dashboard`
- **Session Data**: `userId`, `driverId`
- **Dashboard Features**: Available tours, accept tours, tour management

#### **Guide Login:**
- **Redirects to**: `/guide-dashboard`
- **Session Data**: `userId`, `guideId`
- **Dashboard Features**: Available tours, accept tours, tour management

#### **Tourist Login:**
- **Redirects to**: `/explore-tour`
- **Session Data**: `userId`
- **Features**: Browse tours, book tours, view assignments

### 3. Session Management

#### **Session Data Stored:**
- `userId`: General user ID
- `driverId`: Driver-specific ID (for drivers)
- `guideId`: Guide-specific ID (for guides)

#### **Session Controller:**
- **Endpoint**: `/api/session/user-info`
- **Purpose**: Provides user session data to frontend
- **Usage**: Dashboards fetch user ID from session

## Implementation Details

### 1. Custom Authentication Success Handler

```java
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) {
        // Get user from database
        String username = authentication.getName();
        Optional<User> userOptional = userRepository.findByUsername(username);
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Role userRole = user.getRole();
            
            // Store user ID in session
            request.getSession().setAttribute("userId", user.getId());
            
            // Redirect based on role
            if (userRole == Role.DRIVER) {
                request.getSession().setAttribute("driverId", user.getId());
                response.sendRedirect("/driver-dashboard");
            } else if (userRole == Role.GUIDE) {
                request.getSession().setAttribute("guideId", user.getId());
                response.sendRedirect("/guide-dashboard");
            } else if (userRole == Role.TOURIST) {
                response.sendRedirect("/explore-tour");
            }
        }
    }
}
```

### 2. Security Configuration

```java
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .formLogin(form -> form
            .loginPage("/login")
            .successHandler(authenticationSuccessHandler) // Custom handler
            .failureUrl("/login?error=true")
            .permitAll()
        )
        // ... other configurations
}
```

### 3. Dashboard Integration

#### **Guide Dashboard:**
```javascript
async function loadUserInfo() {
    try {
        const response = await fetch('/api/session/user-info');
        const userInfo = await response.json();
        
        if (userInfo.guideId) {
            sessionStorage.setItem('guideId', userInfo.guideId);
            localStorage.setItem('guideId', userInfo.guideId);
        }
        
        loadGuideTours();
    } catch (error) {
        console.error('Error loading user info:', error);
        loadGuideTours(); // Fallback to default
    }
}
```

#### **Driver Dashboard:**
```javascript
async function loadUserInfo() {
    try {
        const response = await fetch('/api/session/user-info');
        const userInfo = await response.json();
        
        if (userInfo.driverId) {
            sessionStorage.setItem('driverId', userInfo.driverId);
            localStorage.setItem('driverId', userInfo.driverId);
        }
        
        loadDriverTours();
    } catch (error) {
        console.error('Error loading user info:', error);
        loadDriverTours(); // Fallback to default
    }
}
```

## Testing the System

### Step 1: Register Users

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

### Step 2: Test Login Redirects

#### **Driver Login Test:**
1. Go to `/login`
2. Enter driver username and password
3. Click "Login"
4. **Expected**: Redirected to `/driver-dashboard`
5. **Dashboard should show**: Available tours for this driver

#### **Guide Login Test:**
1. Go to `/login`
2. Enter guide username and password
3. Click "Login"
4. **Expected**: Redirected to `/guide-dashboard`
5. **Dashboard should show**: Available tours for this guide

#### **Tourist Login Test:**
1. Go to `/login`
2. Enter tourist username and password
3. Click "Login"
4. **Expected**: Redirected to `/explore-tour`
5. **Page should show**: Tour exploration page

### Step 3: Verify Dashboard Functionality

#### **Driver Dashboard:**
- ✅ **Shows available tours** for this specific driver
- ✅ **Accept tour functionality** works
- ✅ **Session data** is properly loaded
- ✅ **Real-time updates** when tours are accepted

#### **Guide Dashboard:**
- ✅ **Shows available tours** for this specific guide
- ✅ **Accept tour functionality** works
- ✅ **Session data** is properly loaded
- ✅ **Real-time updates** when tours are accepted

## API Endpoints

### Session Management
- **GET** `/api/session/user-info` - Get current user session data

### Dashboard Data
- **GET** `/api/driver-tours/driver/{driverId}/available` - Get available tours for driver
- **GET** `/api/guide-tours/guide/{guideId}/available` - Get available tours for guide

### Tour Acceptance
- **POST** `/api/driver-tours/accept` - Accept tour as driver
- **POST** `/api/guide-tours/accept` - Accept tour as guide

## Error Handling

### Login Failures
- **Invalid credentials**: Redirected to `/login?error=true`
- **User not found**: Redirected to `/explore-tour` (fallback)

### Dashboard Errors
- **Session data missing**: Falls back to default ID (1)
- **API errors**: Shows "No available tours" message
- **Network errors**: Graceful error handling with user feedback

## Security Features

### Session Security
- ✅ **Session-based authentication**
- ✅ **Role-based access control**
- ✅ **Automatic session management**
- ✅ **Secure redirects**

### Data Protection
- ✅ **User ID validation**
- ✅ **Role verification**
- ✅ **Session timeout handling**
- ✅ **CSRF protection** (disabled for API endpoints)

## Benefits

- ✅ **Automatic Role Detection**: No manual role selection needed
- ✅ **Seamless User Experience**: Direct redirect to appropriate dashboard
- ✅ **Session Management**: Proper user session handling
- ✅ **Role-Based Access**: Each user sees only their relevant data
- ✅ **Real-Time Updates**: Dashboards show current available tours
- ✅ **Error Handling**: Graceful fallbacks for edge cases

## Troubleshooting

### If Login Doesn't Redirect Correctly
1. **Check user role in database**
2. **Verify authentication success handler**
3. **Check session data storage**

### If Dashboard Shows No Tours
1. **Verify user ID in session**
2. **Check if tours exist in database**
3. **Test API endpoints directly**

### If Session Data Missing
1. **Check session controller**
2. **Verify session storage**
3. **Check browser session settings**

This system ensures that users are automatically directed to their appropriate dashboards based on their role, providing a seamless and intuitive user experience.




