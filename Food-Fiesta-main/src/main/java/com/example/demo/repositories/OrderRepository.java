package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entities.Orders;
import com.example.demo.entities.User;

public interface OrderRepository extends JpaRepository<Orders, Integer>
{
	List<Orders> findOrdersByUser(User user);

	@Query("SELECT o.user.u_id, o.user.uname, o.user.uemail, COUNT(o) FROM Orders o GROUP BY o.user.u_id, o.user.uname, o.user.uemail")
	List<Object[]> countOrdersGroupByUser();

	@Query("SELECT o.oName, COUNT(o), SUM(o.oQuantity), SUM(o.totalAmmout), AVG(o.oPrice) FROM Orders o GROUP BY o.oName ORDER BY SUM(o.totalAmmout) DESC")
	List<Object[]> getProductSalesSummary();

	@Query("SELECT o.user.u_id, o.user.uname, o.user.uemail, COUNT(o), SUM(o.oQuantity), SUM(o.totalAmmout) FROM Orders o GROUP BY o.user.u_id, o.user.uname, o.user.uemail ORDER BY SUM(o.totalAmmout) DESC")
	List<Object[]> getUserOrdersSummary();
}
