package com.example.demo.config;

import com.example.demo.entities.Admin;
import com.example.demo.repositories.AdminRepository;
import com.example.demo.entities.Product;
import com.example.demo.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository, AdminRepository adminRepository) {
        return args -> {
            if (productRepository.count() < 12) {
                productRepository.deleteAll();
                Product p1 = new Product();
                p1.setPname("Hyderabadi Biryani");
                p1.setPprice(410.0);
                p1.setPdescription("Authentic slow-cooked basmati rice with tender spice-marinated chicken.");

                Product p2 = new Product();
                p2.setPname("Paneer Butter Masala");
                p2.setPprice(290.0);
                p2.setPdescription("Rich and creamy tomato base with fresh cubes of cottage cheese.");

                Product p3 = new Product();
                p3.setPname("Royal Butter Chicken");
                p3.setPprice(380.0);
                p3.setPdescription("Our signature dish. Silky smooth gravy with tandoori chicken chunks.");

                Product p4 = new Product();
                p4.setPname("Honey Chilli Potato");
                p4.setPprice(150.0);
                p4.setPdescription("Crispy potato fries glazed with honey and fiery Schezwan sauce.");

                Product p5 = new Product();
                p5.setPname("Classic Chola Bhatura");
                p5.setPprice(180.0);
                p5.setPdescription("Fluffy puffed bread served with spicy chickpea curry and pickles.");

                Product p6 = new Product();
                p6.setPname("Gulab Jamun (2pcs)");
                p6.setPprice(50.0);
                p6.setPdescription("Warm, syrup-soaked berry-sized balls made with milk solids.");

                Product p7 = new Product();
                p7.setPname("Saffron Lucknowi Biryani");
                p7.setPprice(450.0);
                p7.setPdescription("Royal Awadhi style biryani with aromatic Lucknowi spices.");

                Product p8 = new Product();
                p8.setPname("Dragon Hakka Noodles");
                p8.setPprice(180.0);
                p8.setPdescription("Wok-fired noodles tossed with crisp garden veggies and sesame oil.");

                Product p9 = new Product();
                p9.setPname("Spicy Kadai Paneer");
                p9.setPprice(310.0);
                p9.setPdescription("Fresh cottage cheese chunks cooked with bell peppers and ground spices.");

                Product p10 = new Product();
                p10.setPname("Traditional Rice Khir");
                p10.setPprice(120.0);
                p10.setPdescription("Creamy Indian rice pudding slow-cooked with saffron and cardamom.");

                Product p11 = new Product();
                p11.setPname("Steamed Veggie Momos");
                p11.setPprice(140.0);
                p11.setPdescription("Delicate handcrafted dumplings served with fiery red chutney.");

                Product p12 = new Product();
                p12.setPname("Laccha Paratha");
                p12.setPprice(60.0);
                p12.setPdescription("Multilayered crispy whole wheat bread cooked in pure ghee.");

                productRepository.saveAll(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12));
                System.out.println("Sample products data seeded into database.");
            }

            Admin existingAdmin = adminRepository.findByAdminEmail("admin@foodfiesta.com");
            if (existingAdmin == null) {
                Admin defaultAdmin = new Admin();
                defaultAdmin.setAdminName("Super Admin");
                defaultAdmin.setAdminEmail("admin@foodfiesta.com");
                defaultAdmin.setAdminPassword("admin123");
                defaultAdmin.setAdminNumber("9876543210");
                adminRepository.save(defaultAdmin);
                System.out.println("✅ Default Admin created: admin@foodfiesta.com / admin123");
            } else {
                System.out.println("✅ Default Admin already exists: admin@foodfiesta.com");
            }
        };
    }
}
