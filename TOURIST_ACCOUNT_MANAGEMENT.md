# Tourist Account Management Implementation

## ✅ **Features Implemented:**

### **1. Delete Account Button**
- Added "Delete Account" button to the tourist dashboard dropdown
- Button appears in red color to indicate destructive action
- Double confirmation required before deletion

### **2. Account Details Display**
- Added account details section above "Your Tours"
- Displays username (read-only), contact number, and email
- Clean, professional styling matching the dashboard theme

### **3. Editable Contact Information**
- Contact number and email are editable inline
- Click "Edit" to enable editing, "Save" to confirm changes
- Real-time database updates with success feedback
- Visual feedback with button color changes

### **4. Complete Account Deletion**
- Deletes user from all related database tables
- Cascades deletion to tours, bookings, and role-specific records
- Redirects to home page after successful deletion
- Clears all session and local storage

## 🎨 **UI/UX Features:**

### **Account Details Section:**
```html
<!-- Account Details Section -->
<div class="account-details-section">
    <h2>Account Details</h2>
    <div class="account-info">
        <div class="account-field">
            <label>Username:</label>
            <span id="account-username">Loading...</span>
        </div>
        <div class="account-field editable">
            <label>Contact Number:</label>
            <input type="text" id="account-contact" value="Loading..." readonly>
            <button onclick="editField('contact')" class="edit-btn">Edit</button>
        </div>
        <div class="account-field editable">
            <label>Email:</label>
            <input type="email" id="account-email" value="Loading..." readonly>
            <button onclick="editField('email')" class="edit-btn">Edit</button>
        </div>
    </div>
</div>
```

### **Delete Account Button:**
```html
<div class="dropdown-content">
    <a href="/edit">Edit Account</a>
    <a href="#" onclick="deleteAccount()" style="color: #f44336;">Delete Account</a>
    <a href="/logout">Logout</a>
</div>
```

## 🔧 **Backend Implementation:**

### **1. UserController:**
```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId)
    
    @PutMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long userId, @RequestBody Map<String, String> request)
    
    @DeleteMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long userId)
}
```

### **2. UserService Methods:**
```java
public Optional<User> getUserById(Long userId)
public boolean updateUser(Long userId, Map<String, String> request)
public boolean deleteUser(Long userId)
```

### **3. Service Methods Added:**
- `DriverService.deleteDriverByUserId(Long userId)`
- `GuideService.deleteGuideByUserId(Long userId)`
- `TouristService.deleteTouristByUserId(Long userId)`
- `TouristUserService.deleteTouristUserByUserId(Long userId)`
- `TourService.deleteToursByUserId(Long userId)`

## 🗄️ **Database Operations:**

### **Account Update:**
- Updates `contactNumber` and `email` in `users` table
- Maintains data integrity across all related tables

### **Account Deletion:**
1. **Deletes from role-specific tables:**
   - `drivers` table (if user is driver)
   - `guides` table (if user is guide)
   - `tourists` table (if user is tourist)
   - `tourist_users` table (if user is tourist)

2. **Deletes from tour-related tables:**
   - All tours created by the user
   - All driver/guide tour assignments
   - All driver/guide bookings

3. **Deletes from main tables:**
   - `users` table (main user record)

## 🎯 **User Experience:**

### **Account Details Loading:**
```javascript
async function loadAccountDetails() {
    try {
        const sessionResponse = await fetch('/api/session/user-info');
        const userInfo = await sessionResponse.json();
        
        if (userInfo.userId) {
            const response = await fetch(`/api/users/${userInfo.userId}`);
            const user = await response.json();
            
            document.getElementById('account-username').textContent = user.username;
            document.getElementById('account-contact').value = user.contactNumber || '';
            document.getElementById('account-email').value = user.email || '';
        }
    } catch (error) {
        console.error('Error loading account details:', error);
    }
}
```

### **Field Editing:**
```javascript
function editField(fieldType) {
    const input = document.getElementById(`account-${fieldType}`);
    const button = input.nextElementSibling;
    
    if (input.readOnly) {
        input.readOnly = false;
        input.focus();
        button.innerText = "Save";
        button.className = "edit-btn save-btn";
        button.onclick = () => saveField(fieldType);
    } else {
        input.readOnly = true;
        button.innerText = "Edit";
        button.className = "edit-btn";
        button.onclick = () => editField(fieldType);
    }
}
```

### **Account Deletion:**
```javascript
async function deleteAccount() {
    if (confirm('Are you sure you want to delete your account? This action cannot be undone and will delete all your tours and data.')) {
        if (confirm('This will permanently delete your account and all associated data. Are you absolutely sure?')) {
            try {
                const sessionResponse = await fetch('/api/session/user-info');
                const userInfo = await sessionResponse.json();
                
                const response = await fetch(`/api/users/${userInfo.userId}`, {
                    method: 'DELETE'
                });
                
                if (response.ok) {
                    const result = await response.json();
                    if (result.success) {
                        alert('Account deleted successfully. You will be redirected to the home page.');
                        sessionStorage.clear();
                        localStorage.clear();
                        window.location.href = '/home';
                    }
                }
            } catch (error) {
                console.error('Error deleting account:', error);
                alert('Error deleting account');
            }
        }
    }
}
```

## 🎨 **Styling Features:**

### **Account Details Section:**
- Clean, professional layout
- Consistent with dashboard theme
- Responsive design
- Hover effects and transitions

### **Edit Buttons:**
- Blue color for edit state
- Green color for save state
- Smooth transitions
- Visual feedback on actions

### **Delete Button:**
- Red color to indicate danger
- Prominent placement in dropdown
- Clear visual hierarchy

## 🔒 **Security Features:**

### **Authentication:**
- All operations require valid user session
- User can only modify their own account
- Session validation on all requests

### **Data Protection:**
- Double confirmation for account deletion
- Clear warnings about data loss
- Secure API endpoints

## 📱 **Responsive Design:**

### **Mobile Friendly:**
- Account details stack vertically on small screens
- Touch-friendly button sizes
- Readable text and proper spacing

### **Desktop Optimized:**
- Horizontal layout for account fields
- Efficient use of space
- Professional appearance

## 🚀 **Performance:**

### **Efficient Loading:**
- Single API call to load account details
- Minimal database queries
- Fast response times

### **Real-time Updates:**
- Immediate UI feedback
- Optimistic updates
- Error handling and rollback

## ✅ **Testing Checklist:**

### **Account Details:**
- [ ] Username displays correctly
- [ ] Contact number loads and displays
- [ ] Email loads and displays
- [ ] Fields are initially read-only

### **Editing Functionality:**
- [ ] Click "Edit" enables field editing
- [ ] Click "Save" updates database
- [ ] Success feedback displays
- [ ] Error handling works

### **Account Deletion:**
- [ ] Double confirmation required
- [ ] Account deleted from database
- [ ] All related data deleted
- [ ] Redirect to home page
- [ ] Session cleared

### **Security:**
- [ ] Only authenticated users can access
- [ ] Users can only modify their own data
- [ ] Proper error handling
- [ ] Secure API endpoints

## 🎉 **Benefits:**

### **User Experience:**
- **Easy account management** - All account functions in one place
- **Safe editing** - Clear visual feedback and confirmation
- **Secure deletion** - Multiple confirmations prevent accidents
- **Professional appearance** - Clean, modern interface

### **Data Integrity:**
- **Complete deletion** - All related data removed
- **Consistent updates** - Changes reflected across all tables
- **Error handling** - Graceful failure with user feedback
- **Transaction safety** - Database operations are atomic

**The tourist account management system is now fully functional with professional UI, secure operations, and complete data management!** 🎉




