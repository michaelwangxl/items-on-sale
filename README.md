 
 # Test Assignment Demo 
 
 ### items-on-sale

Thanks for giving me this opportunity to demonstrate my coding with this assignment.

1. This project was written in Java 8, Spring Boot with Maven. Configurations for DB, Scheduling and Security are in *com.michael.test.config* package.

2. Data source is embedded H2 database. DB scritps are in /resources/testdb/

3. For testing purpose, the authentication here is in-memory authentication.
   * Users are user1, user2, admin; passwords are all password.

4. Scheduler has a thread pool size = 10.

5. To implement the business logic, User, Product, UserFavorite, UserOrder tables are created.The Recommendation Object includes 3 parts: myOrders list, Wish List and Hot Deals.
   1. User: userId, userName, password.
   2. Product: productId, productName, onSale. This table indicates if the product is on sale or not.
   3. UserFavorite: favId, userId, productId. This table is to record users' favorite products. 
   4. UserOrder: orderId, userId, productId, rating. This table records users' orders of some certain products and the rating the user ratings for the products. 
   5. The business logic explanation for recommendation list is in RecommendationService.java.

6. **Items on sale list only changes once daily** is implemented by scheduling cron job, which is in ScheduledTasks.java.

7. **Secure your service to only allow calls from the website domain "shopping.rbc.com"** is implemented in the ItemsOnSaleController.java with annotation "@CrossOrigin".

8. **Consumers/Users should have valid credentials when calling the microservice or else they will get 403 forbidden error** is handled in ItemsOnSaleController with an Exception Class NoAccessException. The logic is, the logged user can only call the API with their own userId, otherwise, the API will respond 403 Error.
   * e.g. user1's userId = 1, so user1 can only call /recommendations/1, other url will return 403 error.

9. API documentation is implemented with OpenAPI plugin, which can be visited at "http://localhost:8080/swagger-ui.html".
