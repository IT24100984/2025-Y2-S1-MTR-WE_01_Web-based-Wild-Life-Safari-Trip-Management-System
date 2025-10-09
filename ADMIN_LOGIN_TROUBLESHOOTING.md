# Admin Login Troubleshooting Guide

## 🔍 **Problem: Admin User Created but Cannot Login**

The admin user exists in the database but login is not working. Let's troubleshoot this step by step.

## ✅ **Step-by-Step Troubleshooting:**

### **Step 1: Verify Admin User in Database**
```sql
-- Check if admin user exists with correct details
SELECT username, email, role, first_name, last_name FROM users WHERE username = 'admin';

-- Check if password is properly encrypted (should start with $2a$)
SELECT username, password FROM users WHERE username = 'admin';
```

### **Step 2: Check Role Value**
```sql
-- Verify the role value (should be 'ADMIN' or 'TOURIST')
SELECT username, role FROM users WHERE username = 'admin';
```

### **Step 3: Test Login Credentials**
- **Username:** `admin`
- **Password:** `admin123`
- **Expected:** Redirect to admin dashboard

## 🔧 **Common Issues and Solutions:**

### **Issue 1: Role Constraint Problem**
If the role is 'TOURIST' instead of 'ADMIN', the authentication handler might not redirect correctly.

**Solution:**
```sql
-- Update role to ADMIN if possible
UPDATE users SET role = 'ADMIN' WHERE username = 'admin';
```

### **Issue 2: Password Encryption Problem**
If the password is not properly encrypted, login will fail.

**Solution:**
```sql
-- Update password with correct BCrypt hash
UPDATE users 
SET password = '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi' 
WHERE username = 'admin';
```

### **Issue 3: Authentication Handler Not Recognizing Admin**
If the role is 'TOURIST' and cannot be changed to 'ADMIN', we need to modify the authentication handler.

**Solution:** Update the authentication handler to treat admin user specially.

## 🎯 **Quick Fix Solutions:**

### **Solution 1: Update Role to ADMIN**
```sql
-- Try to update role to ADMIN
UPDATE users SET role = 'ADMIN' WHERE username = 'admin';

-- If that fails due to constraint, use Solution 2
```

### **Solution 2: Modify Authentication Handler**
If role cannot be 'ADMIN', update the authentication handler to treat admin user specially.

### **Solution 3: Recreate Admin User**
```sql
-- Delete existing admin user
DELETE FROM users WHERE username = 'admin';

-- Insert with correct role and password
INSERT INTO users (username, password, role, first_name, last_name, email, contact_number, nic) 
VALUES (
    'admin', 
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 
    'ADMIN', 
    'Admin', 
    'admin', 
    'admin@gmail.com', 
    '07188888888', 
    '200479504848'
);
```

## 🧪 **Testing Steps:**

### **Test 1: Database Verification**
```sql
SELECT username, role, password FROM users WHERE username = 'admin';
```

### **Test 2: Login Test**
1. **Go to:** `/login`
2. **Enter:** Username: `admin`, Password: `admin123`
3. **Click:** Login
4. **Check:** What happens (error message, redirect, etc.)

### **Test 3: Authentication Handler Test**
Check if the authentication handler is working correctly by looking at the console logs.

## 🚀 **Expected Results:**

### **✅ If Working:**
- Login successful
- Redirected to `/admin-dashboard`
- Admin dashboard loads with tour data

### **❌ If Not Working:**
- Check error messages
- Verify database values
- Check authentication handler logic

## 📝 **Next Steps:**

1. **Run the database verification queries**
2. **Test the login process**
3. **Check for error messages**
4. **Apply the appropriate solution based on the results**

**Let me know what you find when you run the verification queries, and I'll provide the specific solution!** 🎉




