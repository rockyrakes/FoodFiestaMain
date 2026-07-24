package com.example.demo.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.count.*;
import com.example.demo.entities.*;
import com.example.demo.loginCredentials.*;
import com.example.demo.services.*;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@Tag(name = "Admin Controller", description = "Back-office and authentication endpoints")
public class AdminController {
	@Autowired
	private UserServices services;
	@Autowired
	private AdminServices adminServices;
	@Autowired
	private ProductServices productServices;	
	@Autowired
	private OrderServices orderServices;

// Removed instance variables email and user to prevent data leakage in singleton controller

	@PostMapping("/adminLogin")
	@Operation(summary = "Admin login authentication", description = "Validates admin credentials and redirects to the services dashboard")
	public String getAllData(@ModelAttribute("adminLogin") AdminLogin login, Model model, jakarta.servlet.http.HttpSession session) {
		String email = login.getEmail();
		String password = login.getPassword();
		if (adminServices.validateAdminCredentials(email, password)) {
			session.setAttribute("loggedInAdmin", email);
			return "redirect:/admin/services";
		} else {
			model.addAttribute("error", "Invalid email or password");
			return "Login";
		}
	}

	@PostMapping("/userLogin")
	@Operation(summary = "User login authentication", description = "Validates user credentials and redirects to the dashboard")
	public String userLogin(@ModelAttribute("userLogin") UserLogin login, Model model, jakarta.servlet.http.HttpSession session) {
		String email = login.getUserEmail();
		String password = login.getUserPassword();
		if (services.validateLoginCredentials(email, password)) {
			User loggedInUser = this.services.getUserByEmail(email);
			session.setAttribute("loggedInUser", loggedInUser);
			return "redirect:/dashboard";
		} else {
			model.addAttribute("error2", "Invalid email or password");
			return "Login";
		}
	}

	@GetMapping("/dashboard")
	@Operation(summary = "User Dashboard", description = "Displays the product ordering page for logged-in users")
	public String dashboard(Model model, jakarta.servlet.http.HttpSession session) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			return "redirect:/userLogin";
		}
		List<Orders> orders = this.orderServices.getOrdersForUser(loggedInUser);
		double totalSpent = 0;
		for (Orders o : orders) {
			totalSpent += o.getTotalAmmout();
		}
		List<Product> allProducts = this.productServices.getAllProducts();
		model.addAttribute("orders", orders);
		model.addAttribute("totalSpent", totalSpent);
		model.addAttribute("productsCount", allProducts.size());
		model.addAttribute("name", loggedInUser.getUname());
		return "BuyProduct";
	}

	@GetMapping("/logout")
	@Operation(summary = "Logout", description = "Invalidates the session and redirects to home")
	public String logout(jakarta.servlet.http.HttpSession session) {
		session.invalidate();
		return "redirect:/home";
	}
	@PostMapping("/product/search")
	@Operation(summary = "Search for a product", description = "Finds a specific food item by name and returns its details")
	public String seachHandler(@RequestParam("productName") String name, Model model, jakarta.servlet.http.HttpSession session) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			return "redirect:/userLogin";
		}

		Product product = this.productServices.getProductByName(name);
		List<Orders> orders = this.orderServices.getOrdersForUser(loggedInUser);
		double totalSpent = 0;
		for (Orders o : orders) {
			totalSpent += o.getTotalAmmout();
		}
		List<Product> allProducts = this.productServices.getAllProducts();
		model.addAttribute("orders", orders);
		model.addAttribute("totalSpent", totalSpent);
		model.addAttribute("productsCount", allProducts.size());
		model.addAttribute("name", loggedInUser.getUname());

		if (product == null) {
			model.addAttribute("message", "SORRY...! Product '" + name + "' Unavailable");
			model.addAttribute("product", null);
		} else {
			model.addAttribute("product", product);
		}
		return "BuyProduct";
	}
	@GetMapping("/admin/services")
	@Operation(summary = "Admin Services Dashboard", description = "Displays the overview of all users, admins, products, and orders")
	public String returnBack(Model model, jakarta.servlet.http.HttpSession session) {
		if (session.getAttribute("loggedInAdmin") == null) {
			return "redirect:/login";
		}
		List<User> users = this.services.getAllUser();
		List<Admin> admins = this.adminServices.getAll();
		List<Product> products = this.productServices.getAllProducts();
		List<Orders> orders = this.orderServices.getOrders();
		List<Object[]> orderCounts = this.orderServices.getOrderCountPerUser();
		List<Object[]> productSummary = this.orderServices.getProductSalesSummary();
		List<Object[]> userOrdersSummary = this.orderServices.getUserOrdersSummary();

		double totalRevenue = 0.0;
		int totalItemsSold = 0;
		for (Orders o : orders) {
			totalRevenue += o.getTotalAmmout();
			totalItemsSold += o.getoQuantity();
		}

		double avgOrderValue = orders.isEmpty() ? 0.0 : totalRevenue / orders.size();

		model.addAttribute("users", users);
		model.addAttribute("admins", admins);
		model.addAttribute("products", products);
		model.addAttribute("orders", orders);
		model.addAttribute("orderCounts", orderCounts);
		model.addAttribute("productSummary", productSummary);
		model.addAttribute("userOrdersSummary", userOrdersSummary);
		model.addAttribute("totalRevenue", totalRevenue);
		model.addAttribute("totalItemsSold", totalItemsSold);
		model.addAttribute("avgOrderValue", avgOrderValue);

		return "Admin_Page";
	}
	@GetMapping("/addAdmin")
	@Operation(summary = "View Add Admin page", description = "Serves the HTML form to create a new administrator account")
	public String addAdminPage()
	{
		return "Add_Admin";
	}
	@PostMapping("addingAdmin")
	@Operation(summary = "Create a new Admin", description = "Processes the form submission to save a new administrator to the database")
	public String addAdmin( @ModelAttribute Admin admin)
	{

		this.adminServices.addAdmin(admin);
		return "redirect:/admin/services";

	}
	@GetMapping("/updateAdmin/{adminId}")
	@Operation(summary = "View Update Admin page", description = "Loads the specified admin's details into the update form")
	public String update(@PathVariable("adminId") int id,Model model)
	{
		Admin admin = this.adminServices.getAdmin(id);
		model.addAttribute("admin", admin);
		return "Update_Admin";
	}
	@PostMapping("/updatingAdmin/{id}")
	@Operation(summary = "Process Admin update", description = "Updates an existing administrator's information in the database")
	public String updateAdmin(@ModelAttribute Admin admin,@PathVariable("id") int id)
	{
		this.adminServices.update(admin, id);
		return "redirect:/admin/services";
	}
	@GetMapping("/deleteAdmin/{id}")
	@Operation(summary = "Delete an Admin", description = "Removes an administrator account by ID")
	public String deleteAdmin(@PathVariable("id") int id)
	{
		this.adminServices.delete(id);
		return "redirect:/admin/services";
	}
	@GetMapping("/addProduct")
	@Operation(summary = "View Add Product page", description = "Serves the HTML form to register a new food item")
	public String addProduct()
	{
		return "Add_Product";
	}
	
	@GetMapping("/updateProduct/{productId}")
	@Operation(summary = "View Update Product page", description = "Loads the specified food item's details into the edit form")
	public String updateProduct(@PathVariable("productId") int id,Model model)
	{
		Product product=this.productServices.getProduct(id);
		System.out.println(product);
		model.addAttribute("product", product);
		return "Update_Product";
	}

	@GetMapping("/addUser")
	@Operation(summary = "View Add User page", description = "Serves the HTML form to register a new customer manually")
	public String addUser()
	{
		return "Add_User";
	}

	@GetMapping("/updateUser/{userId}")
	@Operation(summary = "View Update User page", description = "Loads the specified customer's details into the edit form")
	public String updateUserPage(@PathVariable("userId") int id,Model model)
	{
		User user = this.services.getUser(id);
		model.addAttribute("user", user);
		return "Update_User";
	}

	@PostMapping("/product/order")
	@Operation(summary = "Process food order", description = "Calculates total amount and saves the order in the system")
	public String orderHandler(@ModelAttribute() Orders order, Model model, jakarta.servlet.http.HttpSession session) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			return "redirect:/userLogin";
		}

		double totalAmount = Logic.countTotal(order.getoPrice(), order.getoQuantity());
		order.setTotalAmmout(totalAmount);
		order.setUser(loggedInUser);
		order.setOrderDate(new Date());
		this.orderServices.saveOrder(order);
		model.addAttribute("amount", totalAmount);
		return "Order_success";
	}

	@PostMapping("/checkout")
	@Operation(summary = "Process checkout for selected orders", description = "Calculates total for selected orders and shows success page")
	public String checkoutHandler(@RequestParam(value = "selectedOrders", required = false) List<Integer> orderIds, Model model, jakarta.servlet.http.HttpSession session) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			return "redirect:/userLogin";
		}
		if (orderIds == null || orderIds.isEmpty()) {
			return "redirect:/dashboard";
		}
		double grandTotal = 0;
		int itemCount = 0;
		for (Integer oid : orderIds) {
			Optional<Orders> opt = this.orderServices.getOrderById(oid);
			if (opt.isPresent() && opt.get().getUser().getU_id() == loggedInUser.getU_id()) {
				grandTotal += opt.get().getTotalAmmout();
				itemCount++;
			}
		}
		model.addAttribute("amount", grandTotal);
		model.addAttribute("itemCount", itemCount);
		return "Order_success";
	}

	@GetMapping("/product/back")
	@Operation(summary = "Return to shop", description = "Navigates back to the product catalog after viewing orders")
	public String back(jakarta.servlet.http.HttpSession session) {
		return "redirect:/dashboard";
	}

	@GetMapping("/order/delete/{orderId}")
	@Operation(summary = "Remove an order", description = "Deletes a specific order by ID for the logged-in user")
	public String deleteOrder(@PathVariable("orderId") int id, jakarta.servlet.http.HttpSession session) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			return "redirect:/userLogin";
		}
		this.orderServices.deleteOrder(id);
		return "redirect:/dashboard";
	}

	// ========== ADMIN ORDER MANAGEMENT ==========

	@GetMapping("/addOrder")
	@Operation(summary = "View Add Order page", description = "Serves the HTML form for admin to create a new order for any customer")
	public String addOrderPage(Model model, jakarta.servlet.http.HttpSession session) {
		if (session.getAttribute("loggedInAdmin") == null) {
			return "redirect:/login";
		}
		List<User> users = this.services.getAllUser();
		List<Product> products = this.productServices.getAllProducts();
		model.addAttribute("users", users);
		model.addAttribute("products", products);
		model.addAttribute("order", new Orders());
		return "Add_Order";
	}

	@PostMapping("/addingOrder")
	@Operation(summary = "Create a new order (admin)", description = "Admin creates an order for a selected customer")
	public String addOrder(@ModelAttribute Orders order, @RequestParam("userId") int userId, jakarta.servlet.http.HttpSession session) {
		if (session.getAttribute("loggedInAdmin") == null) {
			return "redirect:/login";
		}
		User user = this.services.getUser(userId);
		double totalAmount = Logic.countTotal(order.getoPrice(), order.getoQuantity());
		order.setTotalAmmout(totalAmount);
		order.setUser(user);
		order.setOrderDate(new Date());
		this.orderServices.saveOrder(order);
		return "redirect:/admin/services";
	}

	@GetMapping("/updateOrder/{orderId}")
	@Operation(summary = "View Update Order page", description = "Loads the specified order details into the edit form for admin")
	public String updateOrderPage(@PathVariable("orderId") int id, Model model, jakarta.servlet.http.HttpSession session) {
		if (session.getAttribute("loggedInAdmin") == null) {
			return "redirect:/login";
		}
		Optional<Orders> optional = this.orderServices.getOrderById(id);
		if (optional.isPresent()) {
			Orders order = optional.get();
			List<User> users = this.services.getAllUser();
			List<Product> products = this.productServices.getAllProducts();
			model.addAttribute("order", order);
			model.addAttribute("users", users);
			model.addAttribute("products", products);
			return "Update_Order";
		}
		return "redirect:/admin/services";
	}

	@PostMapping("/updatingOrder/{id}")
	@Operation(summary = "Update an order (admin)", description = "Admin updates an existing order details")
	public String updateOrder(@ModelAttribute Orders order, @PathVariable("id") int id, @RequestParam("userId") int userId, jakarta.servlet.http.HttpSession session) {
		if (session.getAttribute("loggedInAdmin") == null) {
			return "redirect:/login";
		}
		User user = this.services.getUser(userId);
		double totalAmount = Logic.countTotal(order.getoPrice(), order.getoQuantity());
		order.setTotalAmmout(totalAmount);
		order.setUser(user);
		order.setoId(id);
		this.orderServices.saveOrder(order);
		return "redirect:/admin/services";
	}

	@GetMapping("/deleteOrderAdmin/{orderId}")
	@Operation(summary = "Delete an order (admin)", description = "Admin removes an order from the system by ID")
	public String deleteOrderAdmin(@PathVariable("orderId") int id, jakarta.servlet.http.HttpSession session) {
		if (session.getAttribute("loggedInAdmin") == null) {
			return "redirect:/login";
		}
		this.orderServices.deleteOrder(id);
		return "redirect:/admin/services";
	}

}
