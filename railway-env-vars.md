# Railway Environment Variables Configuration

## For Spring Boot Backend Deployment

Add these environment variables in your Railway service:

```
PORT=8080
SPRING_PROFILES_ACTIVE=prod
DDL_AUTO=update
SHOW_SQL=false
LOG_LEVEL=INFO
CORS_ORIGINS=*

# Database Connection Variables
DATABASE_URL=mysql://root:hCeVXbAFRayTLwphywfgAbjUWeXHytCP@ballast.proxy.rlwy.net:26089/railway?useSSL=true&allowPublicKeyRetrieval=true&serverTimezone=UTC
DB_USERNAME=root
DB_PASSWORD=hCeVXbAFRayTLwphywfgAbjUWeXHytCP

# MySQL Variables (Railway Auto-Generated)
MYSQL_DATABASE=railway
MYSQL_PUBLIC_URL=mysql://root:hCeVXbAFRayTLwphywfgAbjUWeXHytCP@ballast.proxy.rlwy.net:26089/railway
MYSQL_ROOT_PASSWORD=hCeVXbAFRayTLwphywfgAbjUWeXHytCP
MYSQL_URL=mysql://root:hCeVXbAFRayTLwphywfgAbjUWeXHytCP@mysql.railway.internal:3306/railway
MYSQLDATABASE=railway
MYSQLHOST=mysql.railway.internal
MYSQLPASSWORD=hCeVXbAFRayTLwphywfgAbjUWeXHytCP
MYSQLPORT=3306
MYSQLUSER=root
```

## Deployment Steps

1. Deploy Spring Boot app to Railway from GitHub repository
2. Add all environment variables above in Railway Variables tab
3. Railway will build and deploy automatically
4. Get your app URL from Railway Settings tab
5. Update frontend API URLs to use Railway backend URL
