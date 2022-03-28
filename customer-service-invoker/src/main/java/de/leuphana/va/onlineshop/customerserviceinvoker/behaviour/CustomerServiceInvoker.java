package de.leuphana.va.onlineshop.customerserviceinvoker.behaviour;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import de.leuphana.va.onlineshop.article.component.structure.Article;
import de.leuphana.va.onlineshop.article.component.structure.Book;
import de.leuphana.va.onlineshop.article.component.structure.CD;
import de.leuphana.va.onlineshop.customer.component.structure.Customer;
import de.leuphana.va.onlineshop.customerserviceinvoker.connector.ShopRestConnectorRequester;
import de.leuphana.va.onlineshop.customerserviceinvoker.view.SelectionView;
import de.leuphana.va.onlineshop.customerserviceinvoker.view.StringInputView;
import de.leuphana.va.onlineshop.customerserviceinvoker.view.View;
import de.leuphana.va.onlineshop.order.component.structure.OrderPosition;
import de.leuphana.va.onlineshop.order.component.structure.Orderr;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerServiceInvoker {

    ShopRestConnectorRequester shopRestConnector;
    Customer customer;
    private boolean isAppRunning = true;

    public static void main(String[] args) {

        Set<String> loggers = new HashSet<>(Arrays.asList("org.apache.http", "groovyx.net.http"));

        for (String log : loggers) {
            Logger logger = (Logger) LoggerFactory.getLogger(log);
            logger.setLevel(Level.INFO);
            logger.setAdditive(false);
        }

        new CustomerServiceInvoker().start();
    }

    public void start() {

        shopRestConnector = new ShopRestConnectorRequester();

        while (isAppRunning) {
            CustomerShopAction action = selectAction();
            action.execute(this);
        }

    }

    public CustomerShopAction selectAction() {

        List<CustomerShopAction> options = new ArrayList<>();

        options.add(CustomerShopAction.SHOW_CATALOG);

        if (customer == null) {
            options.add(CustomerShopAction.CREATE_CUSTOMER);
            options.add(CustomerShopAction.LOGIN);
        } else {
            options.add(CustomerShopAction.SHOW_CART);
            options.add(CustomerShopAction.SHOW_ORDERS);
            options.add(CustomerShopAction.LOGOUT);
        }

        options.add(CustomerShopAction.STOP);

        SelectionView view = new SelectionView() {
            @Override
            protected String getMessage() {
                return "Enter a number to select an action.";
            }

            @Override
            protected List<String> getOptions() {
                return options.stream().map(CustomerShopAction::text).toList();
            }
        };

        int selection = view.displaySelection();
        return options.get(selection);
    }

    private void showOrders() {
        // assert user != null else display error message
        if (customer == null) {
            displayMessage("ERROR: Customer not logged in!");
            return;
        }

        try {
            // invoke getOrdersByCustomer
            List<Orderr> orders = shopRestConnector.getOrdersForCustomer(customer.getCustomerId()).stream().toList();

            // Display all orders (selectable)
            List<String> options = new ArrayList<>();
            for (Orderr order : orders) {
                options.add("Order " + order.getOrderId() + " (" + order.getNumberOfArticles() + " articles)");
            }

            SelectionView view = new SelectionView() {

                @Override
                protected String getMessage() {
                    return "Your Orders: \n";
                }

                @Override
                protected List<String> getOptions() {
                    options.add(null);
                    options.add("Back");
                    return options;
                }
            };

            int selection = view.displaySelection();

            if (selection < orders.size()) {
                // Order has been selected
                Orderr selectedOrder = orders.get(selection);
                showOrder(selectedOrder);
                showOrders();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //================================================================================
    // The following methods are being called by the corresponding actions.
    //================================================================================

    private void showOrder(Orderr order) {
        // assert user != null else display error message
        if (customer == null) {
            displayMessage("ERROR: Customer not logged in!");
            return;
        }

        try {
            // invoke getUserByID + set user
            List<OrderPosition> orderPositions = order.getOrderPositions().stream().toList();
            List<Article> articles = new ArrayList<>();
            final Float[] totalPrice = {0.0f};

            // Display all articles in cart (selectable)
            List<String> options = new ArrayList<>();
            orderPositions.forEach(orderPos -> {
                try {
                    Article article = shopRestConnector.getArticle(orderPos.getArticleId());
                    articles.add(article);
                    options.add(String.format("%-30s %8.2f €      Amount: %s", article.getName(), article.getPrice(), orderPos.getArticleQuantity()));
                    totalPrice[0] += article.getPrice() * orderPos.getArticleQuantity();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            SelectionView view = new SelectionView() {

                @Override
                protected String getMessage() {
                    return "Your Order (" + order.getOrderId() + "):\n" + articles.size() + " article" + ((articles.size() != 1) ? "s" : "") + "\n";
                }

                @Override
                protected List<String> getOptions() {
                    options.add(null);
                    options.add("Back");
                    return options;
                }
            };

            int selection = view.displaySelection();

            if (selection < articles.size()) {
                // Article has been selected
                Article selectedArticle = articles.get(selection);
                showArticle(selectedArticle);
                showOrder(order);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showCart() {
        // assert user != null else display error message
        if (customer == null) {
            displayMessage("ERROR: Customer not logged in!");
            return;
        }

        try {
            // invoke getUserByID + set user
            customer = shopRestConnector.getCustomerWithId(customer.getCustomerId());
            List<Article> articles = new ArrayList<>();
            final Float[] totalPrice = {0.0f};

            // Display all articles in cart (selectable)
            List<String> options = new ArrayList<>();
            customer.getCart().getCartItems().forEach(cartItem -> {
                try {
                    Article article = shopRestConnector.getArticle(cartItem.getArticleId());
                    articles.add(article);
                    options.add(String.format("%-30s %8.2f €      Amount: %s", article.getName(), article.getPrice(), cartItem.getQuantity()));
                    totalPrice[0] += article.getPrice() * cartItem.getQuantity();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            SelectionView view = new SelectionView() {

                @Override
                protected String getMessage() {
                    return "Your Cart:\n" + articles.size() + " article" + ((articles.size() != 1) ? "s" : "") + "\n";
                }

                @Override
                protected List<String> getOptions() {
                    options.add(null);
                    if (articles.size() > 0) options.add(String.format("Check out (%.2f €)", totalPrice[0]));
                    options.add("Back");
                    return options;
                }
            };

            int selection = view.displaySelection();

            if (selection < articles.size()) {
                // Article has been selected
                Article selectedArticle = articles.get(selection);
                showArticle(selectedArticle);
                showCart();
            } else if (articles.size() > 0 && selection == articles.size()) {
                // Check out cart
                checkOutCart();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkOutCart() {
        // assert user != null else display error message
        if (customer == null) {
            displayMessage("ERROR: Customer not logged in!");
            return;
        }

        try {
            boolean success = shopRestConnector.checkOutCart(customer.getCustomerId());
            if (success) {
                displayMessage("Order placed!");
            } else {
                displayMessage("Failed to check out cart!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void login() {
        // Display login view (Enter user id)
        String customerIdString = new StringInputView() {

            @Override
            protected String getMessage() {
                return "Customer ID:";
            }
        }.displayStringInput();

        try {
            int customerId = Integer.parseInt(customerIdString);

            // invoke getUserByID + set user
            customer = shopRestConnector.getCustomerWithId(customerId);

            // display success
            displayMessage("Logged in as " + customer.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createCustomer() {
        // Display customer creation views (name & address)
        String name = new StringInputView() {

            @Override
            protected String getMessage() {
                return "Username:";
            }
        }.displayStringInput();

        String address = new StringInputView() {

            @Override
            protected String getMessage() {
                return "Address:";
            }
        }.displayStringInput();


        try {
            // Invoke createCustomer + set new customer
            customer = shopRestConnector.createCustomer(name, address);

            // display success view
            displayMessage("Customer created!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showCatalog() {
        try {
            // Invoke get all articles
            List<Article> articles = shopRestConnector.getAllArticles().stream().toList();

            // Display all articles in catalog (Selectable)
            List<String> options = new ArrayList<>();
            articles.forEach(article -> options.add(String.format("%-30s %.2f €", article.getName(), article.getPrice())));

            SelectionView view = new SelectionView() {

                @Override
                protected String getMessage() {
                    return "All Articles:";
                }

                @Override
                protected List<String> getOptions() {
                    options.add(null);
                    options.add("Back");
                    return options;
                }
            };

            int selection = view.displaySelection();

            if (selection < articles.size()) {
                // Article has been selected
                Article selectedArticle = articles.get(selection);
                showArticle(selectedArticle);
                showCatalog();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showArticle(Article article) {
        // Display article information
        // Options: Back, Add to Cart (if customer authenticated)

        SelectionView view = new SelectionView() {

            @Override
            protected String getMessage() {
                StringBuilder sb = new StringBuilder(String.format("Article Info (ID: %s)", article.getArticleId()));

                sb.append(String.format("%n%-20s %s", "Name:", article.getName()));
                sb.append(String.format("%n%-20s %.2f €", "Price:", article.getPrice()));
                if (article instanceof Book book) {
                    sb.append(String.format("%n%-20s %s", "Article Type:", "Book"));
                    sb.append(String.format("%n%-20s %s", "Author:", book.getAuthor()));
                    sb.append(String.format("%n%-20s %s", "Category:", book.getBookCategory().name().replace("_", " ")));
                } else if (article instanceof CD cd) {
                    sb.append(String.format("%n%-20s %s", "Article Type:", "CD"));
                    sb.append(String.format("%n%-20s %s", "Artist:", cd.getArtist()));
                }
                sb.append(String.format("%n%-20s %s", "Manufacturer:", article.getManufacturer()));
                sb.append("\n");
                return sb.toString();
            }

            @Override
            protected List<String> getOptions() {
                List<String> options = new ArrayList<>();
                options.add("Back");
                if (customer != null) options.add("Add to Cart");
                if (customer != null && customer.getCart().getCartItemsMap().containsKey(article.getArticleId()))
                    options.add("Remove from Cart");
                return options;
            }
        };

        int selection = view.displaySelection();

        if (selection == 1) {
            // Add to customers cart
            addArticleToCart(article);
        } else if (selection == 2) {
            // Remove from cart
            removeArticleFromCart(article);
        }
    }

    private void addArticleToCart(Article article) {
        int customerId = customer.getCustomerId();

        try {
            boolean success = shopRestConnector.addArticleToCart(customerId, article.getArticleId());
            if (success) {
                displayMessage(article.getName() + " added to Cart!");
            } else {
                displayMessage("Failed to add article to Cart!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void removeArticleFromCart(Article article) {
        int customerId = customer.getCustomerId();

        try {
            boolean success = shopRestConnector.removeArticleFromCart(customerId, article.getArticleId());

            if (success) {
                displayMessage(article.getName() + " removed from Cart!");
            } else {
                displayMessage("Failed to remove article from Cart!");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayMessage(String message) {
        new View() {
            @Override
            protected String getMessage() {
                return message;
            }
        }.display();
    }

    enum CustomerShopAction {
        SHOW_CATALOG, CREATE_CUSTOMER, LOGIN, SHOW_CART, SHOW_ORDERS, LOGOUT, STOP;

        public String text() {
            return switch (this) {
                case SHOW_CATALOG -> "Show Catalog";
                case CREATE_CUSTOMER -> "Create new customer";
                case LOGIN -> "Login";
                case SHOW_CART -> "Show my cart";
                case SHOW_ORDERS -> "Show my orders";
                case LOGOUT -> "Logout";
                case STOP -> "Stop App";
            };
        }

        public void execute(CustomerServiceInvoker controller) {
            switch (this) {
                case SHOW_CATALOG -> controller.showCatalog();
                case CREATE_CUSTOMER -> controller.createCustomer();
                case LOGIN -> controller.login();
                case SHOW_CART -> controller.showCart();
                case SHOW_ORDERS -> controller.showOrders();
                case LOGOUT -> controller.customer = null;
                case STOP -> controller.isAppRunning = false;
            }
        }
    }
}
