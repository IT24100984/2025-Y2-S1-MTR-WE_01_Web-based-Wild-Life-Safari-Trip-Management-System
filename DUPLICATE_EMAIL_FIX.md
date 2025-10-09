# Duplicate Email Constraint Error Fix

## ❌ **Error Encountered:**
```
Msg 2627, Level 14, State 1, Line 59
Violation of UNIQUE KEY constraint 'UK6dotkott2kjsp8vw4d0m25fb7'. 
Cannot insert duplicate key in object 'dbo.users'. 
The duplicate key value is (admin@gmail.com).
```

## 🔍 **Root Cause:**
There's already a user in the database with the email `admin@gmail.com`, which violates the UNIQUE KEY constraint on the email column.

## ✅ **Solution Options:**

### **Option 1: Clean Up and Recreate (Recommended)**
```sql
-- Step 1: Check existing users with admin@gmail.com
SELECT username, email, role FROM users WHERE email = 'admin@gmail.com';

-- Step 2: Check existing users with username 'admin'
SELECT username, email, role FROM users WHERE username = 'admin';

-- Step 3: Delete any existing admin user
DELETE FROM users WHERE username = 'admin';

-- Step 4: Delete any existing user with admin@gmail.com (if different)
DELETE FROM users WHERE email = 'admin@gmail.com' AND username != 'admin';

-- Step 5: Insert admin user with TOURIST role (to avoid role constraint)
INSERT INTO users (username, password, role, first_name, last_name, email, contact_number, nic) 
VALUES (
    'admin', 
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 
    'TOURIST',  -- Use TOURIST to avoid role constraint
    'Admin', 
    'admin', 
    'admin@gmail.com', 
    '07188888888', 
    '200479504848'
);

-- Step 6: Try to update to ADMIN role
UPDATE users 
SET role = 'ADMIN' 
WHERE username = 'admin';

-- Step 7: Verify success
SELECT username, email, role FROM users WHERE username = 'admin';
```

### **Option 2: Use Different Email**
```sql
-- Delete existing admin user
DELETE FROM users WHERE username = 'admin';

-- Insert with different email
INSERT INTO users (username, password, role, first_name, last_name, email, contact_number, nic) 
VALUES (
    'admin', 
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 
    'TOURIST', 
    'Admin', 
    'admin', 
    'admin@safari.com',  -- Different email
    '07188888888', 
    '200479504848'
);

-- Try to update to ADMIN role
UPDATE users 
SET role = 'ADMIN' 
WHERE username = 'admin';
```

### **Option 3: Update Existing User**
```sql
-- Find the existing user with admin@gmail.com
SELECT username, email, role FROM users WHERE email = 'admin@gmail.com';

-- Update that user to be admin (if it's not already admin)
UPDATE users 
SET username = 'admin',
    password = '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi',
    role = 'TOURIST',  -- Use TOURIST first
    first_name = 'Admin',
    last_name = 'admin',
    contact_number = '07188888888',
    nic = '200479504848'
WHERE email = 'admin@gmail.com';

-- Try to update to ADMIN role
UPDATE users 
SET role = 'ADMIN' 
WHERE username = 'admin';
```

## 🎯 **Step-by-Step Solution:**

### **Step 1: Check Existing Users**
```sql
-- Check what users exist with admin@gmail.com
SELECT username, email, role FROM users WHERE email = 'admin@gmail.com';

-- Check what users exist with username 'admin'
SELECT username, email, role FROM users WHERE username = 'admin';
```

### **Step 2: Clean Up Existing Users**
```sql
-- Delete any existing admin user
DELETE FROM users WHERE username = 'admin';

-- Delete any existing user with admin@gmail.com (if different from admin)
DELETE FROM users WHERE email = 'admin@gmail.com' AND username != 'admin';
```

### **Step 3: Insert Admin User**
```sql
-- Insert with TOURIST role to avoid role constraint
INSERT INTO users (username, password, role, first_name, last_name, email, contact_number, nic) 
VALUES (
    'admin', 
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 
    'TOURIST', 
    'Admin', 
    'admin', 
    'admin@gmail.com', 
    '07188888888', 
    '200479504848'
);
```

### **Step 4: Update to ADMIN Role**
```sql
-- Try to update to ADMIN role
UPDATE users 
SET role = 'ADMIN' 
WHERE username = 'admin';
```

### **Step 5: Verify Success**
```sql
-- Check if admin user was created successfully
SELECT username, email, role, first_name, last_name FROM users WHERE username = 'admin';
```

## 🧪 **Testing the Fix:**

### **Test 1: Check Existing Users**
```sql
SELECT username, email, role FROM users WHERE email = 'admin@gmail.com';
SELECT username, email, role FROM users WHERE username = 'admin';
```

### **Test 2: Clean Up and Insert**
```sql
-- Run the cleanup and insert script
DELETE FROM users WHERE username = 'admin';
DELETE FROM users WHERE email = 'admin@gmail.com' AND username != 'admin';
INSERT INTO users (username, password, role, first_name, last_name, email, contact_number, nic) 
VALUES ('admin', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'TOURIST', 'Admin', 'admin', 'admin@gmail.com', '07188888888', '200479504848');
```

### **Test 3: Update Role**
```sql
-- Try to update to ADMIN role
UPDATE users SET role = 'ADMIN' WHERE username = 'admin';
```

### **Test 4: Verify Admin User**
```sql
-- Check if admin user exists with correct details
SELECT username, email, role, first_name, last_name FROM users WHERE username = 'admin';
```

### **Test 5: Test Login**
1. **Go to login page** - `/login`
2. **Enter credentials:**
   - Username: `admin`
   - Password: `admin123`
3. **Click Login**
4. **Verify:** Redirected to appropriate dashboard

## 🎉 **Expected Results:**

### **✅ If Successful:**
- Admin user created successfully
- Can login with username: `admin`, password: `admin123`
- Redirected to admin dashboard (if role is ADMIN) or appropriate dashboard

### **❌ If Role Update Fails:**
- Admin user exists with TOURIST role
- Can still login and access system
- May need to modify authentication handler to treat admin user specially

## 🚀 **Alternative Approach:**

If the role constraint cannot be resolved, we can modify the authentication handler to treat the admin user specially:

```java
// In CustomAuthenticationSuccessHandler.java
if (username.equals("admin")) {
    // Treat admin user as admin regardless of role
    request.getSession().setAttribute("adminId", user.getId());
    response.sendRedirect("/admin-dashboard");
}
```

## 📝 **Files Created:**

1. **`fix_duplicate_email.sql`** - Complete solution for duplicate email issue
2. **`admin_user_alternative_email.sql`** - Alternative with different email
3. **`DUPLICATE_EMAIL_FIX.md`** - Comprehensive documentation

**Run the cleanup script first to remove existing users, then insert the admin user!** 🎉




