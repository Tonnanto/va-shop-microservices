package de.leuphana.va.onlineshop.customer.component.structure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cartId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<CartItem> cartItems;

    public Cart() {
        this.cartItems = new HashSet<>();
    }

    public int getCartId() {
        return cartId;
    }

    public void addCartItem(int articleId) {
        CartItem cartItem;
        if (getCartItemsMap().containsKey(articleId)) {
            cartItem = getCartItemsMap().get(articleId);
            cartItem.incrementQuantity();
        } else {
            cartItem = new CartItem(articleId);
            cartItems.add(cartItem);
        }
    }

    public void decrementArticleQuantity(int articleId) {
        if (getCartItemsMap().containsKey(articleId)) {
            CartItem cartItem = getCartItemsMap().get(articleId);
            cartItem.decrementQuantity();

            if (cartItem.getQuantity() <= 0)
                cartItems.remove(cartItem);

        }
    }

    public void deleteCartItem(int articleId) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getArticleId() == (articleId)) {
                cartItems.remove(cartItem);
                break;
            }
        }
    }

    public Collection<CartItem> getCartItems() {
        return getCartItemsMap().values();
    }

    public Map<Integer, CartItem> getCartItemsMap() {
        return cartItems.stream().collect(Collectors.toMap(CartItem::getArticleId, cartItem -> cartItem));
    }

    public int getNumberOfArticles() {
        return cartItems.size();
    }
}