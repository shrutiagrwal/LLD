import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class foodDeliveryApp {
    public static void main(String[] args) {
        List<User> userslist = new ArrayList<>();
        List<Restaurant> restaurantList = new ArrayList<>();
        List<DeliveryPartner> deliveryPartnerList = new ArrayList<>();
        FoodDeliverySystem system = new FoodDeliverySystem(restaurantList, deliveryPartnerList, userslist);
        system.createUser();
        system.createDeliveryPartner();
        system.createRestaurants();
    }
}

class FoodDeliverySystem {
    RestaurantManager restaurantManager;
    OrderController orderController;
    UserController userController;
    DeliveryManager deliveryManager;

    FoodDeliverySystem(List<Restaurant> restaurantList, List<DeliveryPartner> deliveryPartners, List<User> users) {
        this.restaurantManager = new RestaurantManager(restaurantList);
        this.orderController = new OrderController();
        this.deliveryManager = new DeliveryManager(deliveryPartners);
        this.userController = new UserController(users);
    }

    public void createRestaurants() {
        Restaurant restaurant = new Restaurant();
        restaurant.id = 1;
        restaurant.menu = this.createMenu();
        restaurantManager.addRestaurant(restaurant);
    }

    public void createDeliveryPartner() {
        DeliveryPartner dp = new DeliveryPartner();
        dp.id = 1;
        dp.city = "blr";
        dp.name = "abc";
        deliveryManager.addPartner(dp);
    }

    public Menu createMenu() {
        FoodCategory pizzaCategory = new FoodCategory(1, "PIZZA");
        FoodItem foodItem = new FoodItem(101, "veg pizza", 120);
        FoodItem foodItem2 = new FoodItem(102, "chicken pizza", 140);
        pizzaCategory.addFoodItem(foodItem);
        pizzaCategory.addFoodItem(foodItem2);
        FoodCategory burger = new FoodCategory(2, "BURGER");
        foodItem = new FoodItem(201, "veg burger", 120);
        foodItem2 = new FoodItem(202, "chicken burger", 140);
        burger.addFoodItem(foodItem);
        burger.addFoodItem(foodItem2);
        Menu menu = new Menu();
        menu.addCategory(pizzaCategory);
        menu.addCategory(burger);
        return menu;
    }

    public void createUser() {
        userController.addUser(new User(1, "shruti", new Address()));
    }

    public List<Restaurant> getRestaurants() {
        return restaurantManager.getRestaurants();
    }
}

class FoodItem {
    int id;
    String name;
    int price;

    FoodItem(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}

class FoodCategory {
    int categoryId;
    String categoryName;
    List<FoodItem> items;

    FoodCategory(int id, String name) {
        this.categoryId = id;
        this.categoryName = name;
    }

    public void addFoodItem(FoodItem food) {
        items.add(food);
    }
}

class Menu {
    List<FoodCategory> foodCategories;

    Menu() {
        this.foodCategories = new ArrayList<>();
    }

    public void addCategory(FoodCategory category) {
        foodCategories.add(category);
    }

    public void removeCategory(FoodCategory category) {
        foodCategories.remove(category);
    }

    public void DisplayMenu() {
        for (FoodCategory category : foodCategories) {
            System.out.println("category--" + category.categoryName);
            for (FoodItem food : category.items) {
                System.out.println("food item " + food.name);
            }
        }
    }
}

class Restaurant {
    int id;
    String name;
    Menu menu;
    Address address;
}

class Address {
    String city;
    String zipcode;
}

class RestaurantManager {
    List<Restaurant> restaurantList;

    RestaurantManager(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }

    public void addRestaurant(Restaurant restaurant) {
        restaurantList.add(restaurant);
    }

    public void deleteRestaurant(Restaurant restaurant) {
        restaurantList.remove(restaurant);
    }

    public Restaurant getRestaurantById(int id) {
        for (Restaurant restaurant : restaurantList) {
            if (restaurant.id == id) {
                return restaurant;
            }
        }
        return null;
    }

    public List<Restaurant> getRestaurants() {
        return restaurantList;
    }
}

class DeliveryPartner {
    int id;
    String city;
    String name;
}

class DeliveryManager {
    List<DeliveryPartner> deliveryPartnerList;
    AssignDeliveryPartnerStrategy strategy;

    DeliveryManager(List<DeliveryPartner> deliveryPartnerList) {
        this.deliveryPartnerList = deliveryPartnerList;
    }

    public void addPartner(DeliveryPartner partner) {
        deliveryPartnerList.add(partner);
    }

    public void removePartner(DeliveryPartner partner) {
        deliveryPartnerList.remove(partner);
    }

    public DeliveryPartner selectDeliveryPartner(AssignDeliveryPartnerStrategy strategy) {
        this.strategy = strategy;
        return strategy.getDeliveryPartner(deliveryPartnerList);
    }

}

interface AssignDeliveryPartnerStrategy {
    public DeliveryPartner getDeliveryPartner(List<DeliveryPartner> deliveryPartners);
}

class AssignNearestPartner implements AssignDeliveryPartnerStrategy {
    @Override
    public DeliveryPartner getDeliveryPartner(List<DeliveryPartner> deliveryPartners) {
        return deliveryPartners.get(0);
    }
}

class User {
    int id;
    String name;
    Address address;

    User(int id, String name, Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }
}

class UserController {
    List<User> users;

    UserController(List<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void deleteUser(User user) {
        users.remove(user);
    }
}

class Order {
    int id;
    Map<FoodItem, Integer> foodItemsCount;
    User user;
    Restaurant restaurant;
    Payment payment;

    Order(int id, User user, Restaurant restaurant, Map<FoodItem, Integer> foodItemsCount) {
        this.id = id;
        this.user = user;
        this.restaurant = restaurant;
        this.foodItemsCount = foodItemsCount;
    }
}

class Payment {
    int id;
    int amount;
    String paymentMode;
}

class OrderController {
    List<Order> orderList;
    Map<User, List<Order>> userVsOrderList;

    OrderController() {
        this.orderList = new ArrayList<>();
        this.userVsOrderList = new HashMap<>();
    }

    public Order getOrderById(int id) {
        for (Order order : orderList) {
            if (order.id == id)
                return order;
        }
        return null;
    }

    public Order createNewOrder(User user, Restaurant restaurant, Map<FoodItem, Integer> foodItemsCount) {
        Order order = new Order(orderList.size(), user, restaurant, foodItemsCount);
        orderList.add(order);
        if (userVsOrderList.containsKey(user)) {
            userVsOrderList.get(user).add(order);
        } else {
            List<Order> orders = new ArrayList<>();
            orders.add(order);
            userVsOrderList.put(user, orders);
        }
        return order;
    }
}
