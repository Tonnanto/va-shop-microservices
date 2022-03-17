package de.leuphana.va.onlineshop.customer.connector;

import de.leuphana.va.onlineshop.customer.component.behaviour.CustomerComponentService;
import de.leuphana.va.onlineshop.customer.component.structure.Cart;
import de.leuphana.va.onlineshop.customer.component.structure.Customer;
import de.leuphana.va.onlineshop.customer.connector.responses.*;
import de.leuphana.va.onlineshop.customer.connector.requests.*;
import org.springframework.web.bind.annotation.*;

//@Slf4j
@RestController
@RequestMapping("api/v1/customer")
public class CustomerRestConnectorProvider {

    private final CustomerComponentService customerService;

    public CustomerRestConnectorProvider(CustomerComponentService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("create")
    public CustomerCreateResponse createCustomer(@RequestBody CustomerCreateRequest createCustomerRequest) {
        Customer newCustomer = customerService.createCustomerWithCart(createCustomerRequest.name(), createCustomerRequest.address());
        return new CustomerCreateResponse(newCustomer);
    }

    @PostMapping("cart/addArticle")
    public void addArticleToCart(@RequestBody AddRemoveArticleRequest addArticleRequest) {
        customerService.addArticleToCart(addArticleRequest.customerId(), addArticleRequest.articleId());
    }

    @PostMapping("cart/removeArticle")
    public void removeArticleFromCart(@RequestBody AddRemoveArticleRequest removeArticleRequest) {
        customerService.removeArticleFromCart(removeArticleRequest.customerId(), removeArticleRequest.articleId());
    }

    @PostMapping("cart/decreaseArticle")
    public void cartItemDecrement(@RequestBody AddRemoveArticleRequest removeArticleRequest) {
        customerService.decrementArticleQuantityInCart(removeArticleRequest.customerId(), removeArticleRequest.articleId());
    }

    @GetMapping(path = "cart/{customerId}")
    public CartGetResponse getCartForCustomer(@PathVariable("customerId") Integer customerId) {
        Cart cart = customerService.getCartForCustomer(customerId);
        return new CartGetResponse(cart);
    }
}
