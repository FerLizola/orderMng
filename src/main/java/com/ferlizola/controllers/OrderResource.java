package com.ferlizola.controllers;

import java.net.URI;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.ferlizola.email.EmailDetails;
import com.ferlizola.email.EmailService;
import com.ferlizola.order.Order;
import com.ferlizola.order.OrderItem;
import com.ferlizola.order.OrderNotFoundException;
import com.ferlizola.person.Person;
import com.ferlizola.person.PersonNotFoundException;
import com.ferlizola.product.Product;
import com.ferlizola.product.ProductNotFoundException;
import com.ferlizola.repository.OrderItemRepository;
import com.ferlizola.repository.OrderRepository;
import com.ferlizola.repository.PersonRepository;
import com.ferlizola.repository.ProductRepository;
import com.ferlizola.utils.OrderStatus;

@CrossOrigin
@RestController
public class OrderResource {
	
	Logger logger = LoggerFactory.getLogger(OrderResource.class);

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private ProductRepository prodRepository;
	
	@Autowired
	private EmailService emailService ;

	@GetMapping(path = "/orders/{userid}")
	public List<Order> showOrders(@PathVariable int userid) {
		return orderRepository.findAllByPersonPersonId(userid);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/order/{id}")
	public EntityModel<Order> showOrder(@PathVariable String id) {
		// TODO: add a parameter personId @RequestHeader(name="parameterName".
		// required="true") String iD
		Optional<Order> order = orderRepository.findById(Integer.parseInt(id));
		if (!order.isPresent()) {

			throw new OrderNotFoundException(
					"The order with the ID- " + id + " was not found, please verify the information!");
		}

		EntityModel<Order> model = EntityModel.of(order.get());

		WebMvcLinkBuilder linkToOrders = linkTo(methodOn(this.getClass()).showOrders(order.get().getPerson().getPersonId()));

		model.add(linkToOrders.withRel("all-orders"));

		return model;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/order/{id}/details")
	public CollectionModel<OrderItem> showOrderDetails(@PathVariable String id) {
		// TODO: add a parameter personId @RequestHeader(name="parameterName".
		// required="true") String iD
		Optional<Order> order = orderRepository.findById(Integer.parseInt(id));
		if (!order.isPresent()) {

			throw new OrderNotFoundException(
					"The order with the ID- " + id + " was not found, please verify the information!");
		}

		List<OrderItem> items = order.get().getOrderItems();

		CollectionModel<OrderItem> model = CollectionModel.of(items);

		WebMvcLinkBuilder linkToOrders = linkTo(methodOn(this.getClass()).showOrders(order.get().getPerson().getPersonId()));

		model.add(linkToOrders.withRel("all-orders"));
		
		return model;
	}

	@DeleteMapping(path = "/order/{id}")
	public void deleteOrder(@PathVariable String id) {
		Optional<Order> order = orderRepository.findById(Integer.parseInt(id));

		if (order.isPresent()) {
			order.get().setOrderStatus(OrderStatus.CANCELED.toString());
			orderRepository.save(order.get());
			EmailDetails details = new EmailDetails(order.get().getPerson().getEmail(),
	        		"Order #" + order.get().getOrderId() + " was marked as canceled on " + new Date() + ".",
	        		"Order has been canceled",
	        		"");
	        String result = emailService.sendSimpleMail(details);
			return;

		}
		throw new OrderNotFoundException(
				"The order with the ID- " + id + " was not found, please verify the information!");
	}
	
	@PutMapping(path = "/order/{id}")
	public void modifyOrder(@PathVariable String id) {
		
		Optional<Order> orderExt = orderRepository.findById(Integer.parseInt(id));

		if (orderExt.isPresent()) {
			orderExt.get().setOrderStatus(OrderStatus.COMPLETED.toString());
			orderRepository.save(orderExt.get());
			EmailDetails details = new EmailDetails(orderExt.get().getPerson().getEmail(),
	        		"Order #" + orderExt.get().getOrderId() + " was marked as completed on " + new Date() + ".",
	        		"Order has been completed",
	        		"");
	        String result = emailService.sendSimpleMail(details);
	        //String result = "NOT ENABLED EMAIL SENDER";
	        
	        logger.info("Order completed, " + result);
			return;

		}
		throw new OrderNotFoundException(
				"The order with the ID- " + id + " was not found, please verify the information!");
	}

	@PostMapping("/order")
    //public ResponseEntity<Object> createOrder(@Valid @RequestBody Order order,@RequestBody List<OrderItem> items, @RequestHeader("userId") int userId) {
    public ResponseEntity<Object> createOrder(@RequestBody JsonNode payload) {
        logger.info("Order Object :: " + payload.toPrettyString());
        
        logger.info("PersonID :: " + payload.get("newOrder").get("order").get("personId").asInt());
        
        int userId = payload.get("newOrder").get("order").get("personId").asInt();
        Optional<Person> person = personRepository.findById(userId);
        
        if(!person.isPresent()) {
            throw new PersonNotFoundException(
                    "The person id was not found, please verify the information!");
        }
        float totalAmount = payload.get("newOrder").get("order").get("totalAmount").floatValue();
        Order order = new Order(OrderStatus.IN_PROGRESS.toString(), totalAmount, new Date());
        order.setPerson(person.get());
        
        Order savedOrder = orderRepository.save(order);
        
        Iterator<JsonNode> items = payload.get("newOrder").get("orderItem").elements();
        
        while(items.hasNext()) {
            JsonNode currentItem = items.next();
            logger.info("Current Item :: " + currentItem.toPrettyString());
            Optional<Product> prod = prodRepository.findById(currentItem.get("prodId").asInt());
            if(!prod.isPresent()) {
                throw new ProductNotFoundException(
                        "The product was not found, please verify the information!");
            }
            OrderItem item = new OrderItem(currentItem.get("quantity").asInt());
            item.setProd(prod.get());
            item.setOrder(savedOrder);
            OrderItem itemSaved = orderItemRepository.save(item);
            logger.info("Item :: " + itemSaved);
        }
        EmailDetails details = new EmailDetails(person.get().getEmail(),
        		"Order #" + order.getOrderId() + " was successfully created on " + savedOrder.getOrderDate() + ".",
        		"Order created successfully",
        		"");
        String result = emailService.sendSimpleMail(details);
        //String result = "NOT ENABLED EMAIL SENDER";
        
        logger.info("Order created, " + result);

       URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedOrder.getOrderId()).toUri();



       return ResponseEntity.created(location).build();
        //return null;
    }

}
