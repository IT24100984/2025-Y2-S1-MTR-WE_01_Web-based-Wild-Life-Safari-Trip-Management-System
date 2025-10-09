# Database Troubleshooting Guide

## 🚨 **Issue: Database Data Cleared**

### **Problem Description:**
After clearing all data from the database tables, you're encountering errors. This is likely due to:
1. **Foreign key constraint issues** between tables
2. **Missing sample data** for testing
3. **Table structure inconsistencies**
4. **JPA entity mapping problems**

## 🔧 **Solutions**

### **Option 1: Complete Database Reset (Recommended)**

**Use this if you want to start fresh with proper table structure:**

```sql
-- Run the complete reset script
-- File: database_reset_complete.sql
```

**This script will:**
- ✅ Drop all existing tables and constraints
- ✅ Recreate tables with correct structure
- ✅ Add proper foreign key relationships
- ✅ Insert sample data for testing
- ✅ Verify the setup

### **Option 2: Fix Foreign Keys Only**

**Use this if tables exist but relationships are broken:**

```sql
-- Run the foreign key fix script
-- File: fix_foreign_keys_only.sql
```

**This script will:**
- ✅ Drop existing broken constraints
- ✅ Add proper foreign key relationships
- ✅ Verify constraints were created
- ✅ Keep existing data (if any)

## 🎯 **Step-by-Step Fix Process**

### **Step 1: Identify the Error**

**Common errors after clearing data:**

1. **Foreign Key Constraint Errors:**
   ```
   The INSERT statement conflicted with the FOREIGN KEY constraint
   ```

2. **Column Mapping Errors:**
   ```
   Invalid column name 'id'
   ```

3. **Table Not Found Errors:**
   ```
   Invalid object name 'users'
   ```

### **Step 2: Choose Your Fix**

#### **For Complete Reset:**
1. **Run:** `database_reset_complete.sql`
2. **Restart** your Spring Boot application
3. **Test** registration and login

#### **For Foreign Key Fix Only:**
1. **Run:** `fix_foreign_keys_only.sql`
2. **Restart** your Spring Boot application
3. **Test** existing functionality

### **Step 3: Verify the Fix**

**Check if tables exist:**
```sql
SELECT name FROM sys.tables ORDER BY name;
```

**Check foreign key constraints:**
```sql
SELECT 
    fk.name AS constraint_name,
    tp.name AS parent_table,
    tr.name AS referenced_table
FROM sys.foreign_keys fk
INNER JOIN sys.tables tp ON fk.parent_object_id = tp.object_id
INNER JOIN sys.tables tr ON fk.referenced_object_id = tr.object_id
WHERE fk.name LIKE 'fk_%_user_id';
```

**Check sample data:**
```sql
SELECT COUNT(*) AS user_count FROM users;
SELECT COUNT(*) AS driver_count FROM drivers;
SELECT COUNT(*) AS guide_count FROM guides;
```

## 🧪 **Testing After Fix**

### **Test 1: User Registration**
1. **Go to:** `/signup`
2. **Register** a new tourist user
3. **Verify:** User is created in `users` and `tourists` tables
4. **Check:** Foreign key relationships work

### **Test 2: User Login**
1. **Go to:** `/login`
2. **Login** with registered user
3. **Verify:** Redirects to appropriate dashboard
4. **Check:** Session data is loaded correctly

### **Test 3: Tour Creation**
1. **Create** a new tour booking
2. **Verify:** Tour is saved in `tours` table
3. **Check:** Foreign key to `users` table works
4. **Confirm:** Tour appears in driver/guide dashboards

## 🔍 **Common Issues and Solutions**

### **Issue 1: "Invalid column name 'id'"**

**Cause:** JPA entity mapping doesn't match database column names

**Solution:**
```java
// In User.java - ensure correct column mapping
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "user_id")  // ✅ This maps to user_id column
private Long id;
```

### **Issue 2: "Foreign key constraint violation"**

**Cause:** Trying to insert data that references non-existent records

**Solution:**
1. **Check** if referenced records exist
2. **Insert** parent records first
3. **Then** insert child records

### **Issue 3: "Table doesn't exist"**

**Cause:** Tables were dropped but not recreated

**Solution:**
1. **Run** the complete reset script
2. **Restart** the application
3. **Let** Hibernate create tables with `ddl-auto=update`

### **Issue 4: "Constraint already exists"**

**Cause:** Trying to add constraints that already exist

**Solution:**
1. **Drop** existing constraints first
2. **Then** add new ones
3. **Use** the provided scripts

## 📊 **Database Schema Verification**

### **Expected Table Structure:**

```sql
-- Users table
users (
    user_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    contact_number VARCHAR(20) NOT NULL,
    nic VARCHAR(20) NOT NULL UNIQUE,
    role VARCHAR(20) NOT NULL,
    -- ... other fields
)

-- Tours table
tours (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    tour_name VARCHAR(255) NOT NULL,
    tour_date DATE NOT NULL,
    number_of_people INT NOT NULL,
    special_instructions TEXT,
    user_id BIGINT NOT NULL,  -- ✅ Foreign key to users.user_id
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    -- ... other fields
)
```

### **Expected Foreign Key Relationships:**

```sql
-- Tours -> Users
ALTER TABLE tours 
ADD CONSTRAINT fk_tours_user_id 
FOREIGN KEY (user_id) REFERENCES users(user_id);

-- Drivers -> Users
ALTER TABLE drivers 
ADD CONSTRAINT fk_drivers_user_id 
FOREIGN KEY (user_id) REFERENCES users(user_id);

-- Guides -> Users
ALTER TABLE guides 
ADD CONSTRAINT fk_guides_user_id 
FOREIGN KEY (user_id) REFERENCES users(user_id);
```

## 🚀 **Quick Fix Commands**

### **If you just want to fix foreign keys:**
```sql
-- Run this in SQL Server Management Studio
-- File: fix_foreign_keys_only.sql
```

### **If you want to start completely fresh:**
```sql
-- Run this in SQL Server Management Studio
-- File: database_reset_complete.sql
```

### **If you want to check what's wrong:**
```sql
-- Check if tables exist
SELECT name FROM sys.tables ORDER BY name;

-- Check foreign key constraints
SELECT 
    fk.name AS constraint_name,
    tp.name AS parent_table,
    tr.name AS referenced_table
FROM sys.foreign_keys fk
INNER JOIN sys.tables tp ON fk.parent_object_id = tp.object_id
INNER JOIN sys.tables tr ON fk.referenced_object_id = tr.object_id;

-- Check table structure
SELECT 
    COLUMN_NAME,
    DATA_TYPE,
    IS_NULLABLE,
    COLUMN_DEFAULT
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_NAME = 'users'
ORDER BY ORDINAL_POSITION;
```

## ✅ **Success Indicators**

**After running the fix scripts, you should see:**

1. ✅ **No foreign key constraint errors**
2. ✅ **User registration works**
3. ✅ **User login works**
4. ✅ **Tour creation works**
5. ✅ **Dashboard data loads correctly**
6. ✅ **All foreign key relationships are intact**

## 🆘 **If Still Having Issues**

**Check these common problems:**

1. **Database Connection:** Ensure your database is running and accessible
2. **Table Permissions:** Ensure your user has CREATE/ALTER permissions
3. **Data Types:** Ensure column data types match JPA entity types
4. **Null Values:** Ensure required fields are not null
5. **Unique Constraints:** Ensure unique fields don't have duplicates

**Run this diagnostic query:**
```sql
-- Check database status
SELECT 
    name,
    database_id,
    create_date,
    collation_name
FROM sys.databases 
WHERE name = 'Akeshi';

-- Check table status
SELECT 
    t.name AS table_name,
    s.name AS schema_name,
    t.create_date
FROM sys.tables t
INNER JOIN sys.schemas s ON t.schema_id = s.schema_id
ORDER BY t.name;
```

**This should resolve your database issues after clearing the data!** 🎉




