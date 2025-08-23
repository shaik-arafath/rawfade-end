# Cara E-commerce Backend

A complete Spring Boot backend for the Cara e-commerce website with user authentication, shopping cart functionality, and order management.

## Features

- **User Authentication**: JWT-based authentication with login/register endpoints
- **Product Management**: CRUD operations for products with categories and search
- **Shopping Cart**: Add, remove, and update items in cart
- **Order Management**: Create orders from cart with payment status tracking
- **Security**: Spring Security with JWT tokens
- **Database**: H2 in-memory database for development

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

## Project Structure

```
src/main/java/com/cara/
├── EcommerceApplication.java          # Main Spring Boot application
├── config/
│   └── DataInitializer.java          # Database initialization
├── controller/
│   ├── AuthController.java            # Authentication endpoints
│   ├── ProductController.java         # Product management
│   ├── CartController.java            # Shopping cart operations
│   └── OrderController.java           # Order management
├── dto/
│   ├── LoginRequest.java              # Login request DTO
│   ├── RegisterRequest.java           # Registration request DTO
│   ├── AuthResponse.java              # Authentication response DTO
│   └── CartItemDto.java               # Cart item DTO
├── entity/
│   ├── User.java                      # User entity
│   ├── Product.java                   # Product entity
│   ├── Cart.java                      # Shopping cart entity
│   ├── CartItem.java                  # Cart item entity
│   ├── Order.java                     # Order entity
│   └── OrderItem.java                 # Order item entity
├── repository/
│   ├── UserRepository.java            # User data access
│   ├── ProductRepository.java         # Product data access
│   ├── CartRepository.java            # Cart data access
│   └── OrderRepository.java           # Order data access
├── security/
│   ├── JwtUtils.java                  # JWT utility class
│   ├── UserDetailsImpl.java           # User details implementation
│   ├── UserDetailsServiceImpl.java    # User details service
│   ├── AuthTokenFilter.java           # JWT authentication filter
│   └── WebSecurityConfig.java         # Security configuration
└── service/
    ├── UserService.java               # User business logic
    ├── ProductService.java            # Product business logic
    ├── CartService.java               # Cart business logic
    └── OrderService.java              # Order business logic
```

## Running the Application

### 1. Clone and Setup

```bash
# Navigate to the project directory
cd ecommerce-backend

# Clean and install dependencies
mvn clean install
```

### 2. Run the Application

```bash
# Run the Spring Boot application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### 3. Access H2 Database Console

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: `password`

## API Endpoints

### Authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login

### Products
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `GET /api/products/featured` - Get featured products
- `GET /api/products/category/{category}` - Get products by category
- `GET /api/products/search?name={name}` - Search products
- `GET /api/products/brand/{brand}` - Get products by brand

### Shopping Cart
- `GET /api/cart/{userId}` - Get user's cart
- `POST /api/cart/{userId}/add?productId={id}&quantity={qty}` - Add item to cart
- `PUT /api/cart/{userId}/update?productId={id}&quantity={qty}` - Update cart item quantity
- `DELETE /api/cart/{userId}/remove?productId={id}` - Remove item from cart
- `DELETE /api/cart/{userId}/clear` - Clear cart

### Orders
- `POST /api/orders/{userId}/create` - Create order from cart
- `GET /api/orders/{userId}` - Get user's orders
- `GET /api/orders/order/{orderId}` - Get order by ID
- `GET /api/orders/number/{orderNumber}` - Get order by order number

## Sample Data

The application automatically creates sample data on startup:

### Users
- **Admin**: username: `admin`, password: `admin123`
- **User**: username: `user`, password: `user123`

### Products
- Calico T-shirt ($78.00)
- Summer Dress ($120.00)
- Casual Jeans ($95.00)
- Formal Shirt ($85.00)
- Winter Jacket ($150.00)

## Frontend Integration

### 1. Update Your JavaScript

Replace your current fetch calls with the new backend endpoints:

```javascript
// Login
fetch('http://localhost:8080/api/auth/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ username, password })
})
.then(res => res.json())
.then(data => {
    if (data.token) {
        localStorage.setItem('jwtToken', data.token);
        localStorage.setItem('userId', data.id);
        localStorage.setItem('loggedIn', 'true');
        // Redirect to home page
    }
});

// Add to Cart
fetch(`http://localhost:8080/api/cart/${userId}/add?productId=${productId}&quantity=1`, {
    method: 'POST',
    headers: { 
        'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`,
        'Content-Type': 'application/json'
    }
});
```

### 2. Create Login/Register Pages

Create `login.html` and `register.html` pages with forms that call the backend endpoints.

### 3. Update Cart Page

Modify your cart page to fetch cart data from the backend and handle checkout.

## Security Features

- JWT token-based authentication
- Password encryption with BCrypt
- Role-based access control
- CORS configuration for frontend integration
- Stateless session management

## Database Schema

The application uses JPA/Hibernate with automatic schema generation. Tables are created automatically on startup.

## Testing the API

You can test the API endpoints using:

1. **Postman** or **Insomnia**
2. **cURL** commands
3. **H2 Console** for database inspection

### Example cURL Commands

```bash
# Register a new user
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","email":"test@example.com","password":"password123","firstName":"Test","lastName":"User"}'

# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"password123"}'

# Get all products
curl http://localhost:8080/api/products

# Add item to cart (replace {token} and {userId})
curl -X POST "http://localhost:8080/api/cart/{userId}/add?productId=1&quantity=1" \
  -H "Authorization: Bearer {token}"
```

## Troubleshooting

### Common Issues

1. **Port already in use**: Change the port in `application.properties`
2. **Database connection issues**: Check H2 console configuration
3. **CORS errors**: Verify CORS configuration in `WebSecurityConfig.java`

### Logs

Check the console output for detailed logs. The application runs with DEBUG logging enabled.

## Production Deployment

For production deployment:

1. Change database from H2 to MySQL/PostgreSQL
2. Update JWT secret in `application.properties`
3. Configure proper CORS origins
4. Enable HTTPS
5. Set up proper logging configuration

## Support

If you encounter any issues:

1. Check the console logs for error messages
2. Verify all dependencies are properly installed
3. Ensure Java 17+ is being used
4. Check that port 8080 is available

## License

This project is for educational purposes. Feel free to modify and use as needed.














