# 🍽️ Food Fiesta Royal — Restaurant Management System

## 👋 What is this project? (Explained Simply)

Imagine you own a **restaurant** called "Food Fiesta Royal." 👑

You need two websites:

1. **A menu website for customers** — so people can see your dishes and order food 🧑‍🍳
2. **A secret control room for YOU (the manager)** — so you can manage everything from one place 🕹️

**This project is BOTH of those things!** It's a big computer program that acts like the brain of your restaurant.

---

## 🧸 Let's use a REAL-LIFE ANALOGY

Think of this project like a **restaurant with a notebook and a waiter**:

| Real Restaurant | This Computer Project |
|:---------------|:---------------------|
| 📓 Notebook with customer info | **Database** (stores all data) |
| 🧑‍💼 Waiter who takes orders | **Controllers** (handles requests) |
| 👨‍🍳 Chef who cooks food | **Services** (processes the logic) |
| 📋 Menu card | **HTML Pages** (what you see) |
| 🖌️ Restaurant decoration | **CSS** (colors, fonts, styles) |
| 🚪 Lock on the door | **Spring Security** (keeps坏人 out) |

---

## 🏗️ What Technologies are Used? (Like Building Blocks)

Each technology is like a different LEGO block that does a specific job:

### 🧱 BLOCK 1: Java 21 (The Main Language)
**What it is:** A programming language — like English but for computers.

**What it does:** Everything in this project is written in Java. It's the brain that tells the computer:
- "When someone clicks 'Order Now', save the order"
- "When admin logs in, show the dashboard"
- "Calculate the total price: price × quantity"

💡 **Think of it like:** The recipe book that tells the chef what to cook and how.

---

### 🧱 BLOCK 2: Spring Boot 3.4.2 (The Robot Helper)
**What it is:** A big collection of ready-made tools that make Java easier.

**Without Spring Boot:** You'd have to write 1000 lines of code just to connect to a database. 😱
**With Spring Boot:** You just write `@Autowired` and it magically works! ✨

**What it does automatically:**
- ✅ Creates web pages when you visit a URL
- ✅ Connects to the database
- ✅ Handles user logins
- ✅ Makes sure everything runs smoothly

💡 **Think of it like:** A factory robot that does all the boring, repetitive work so you can focus on the fun stuff.

---

### 🧱 BLOCK 3: Thymeleaf (The Page Maker)
**What it is:** A tool that puts LIVE data into HTML pages.

**The Problem:** If you write a plain HTML page, the name "Rahul" is stuck forever.
**The Solution:** Thymeleaf lets you write:
```html
<p>Hello, <span th:text="${customerName}">Guest</span>!</p>
```
If the customer is "Rahul" → page shows "Hello, Rahul!"
If the customer is "Priya" → page shows "Hello, Priya!"

💡 **Think of it like:** A coloring book where the colors change based on who's holding it. 📘

---

### 🧱 BLOCK 4: Spring Data JPA (The Database Connector)
**What it is:** A tool that saves and reads data from the database.

**What it does:** Instead of writing complicated SQL queries like:
```sql
SELECT * FROM users WHERE email = 'admin@foodfiesta.com'
```
You can just write in Java:
```java
User user = userRepository.findByEmail("admin@foodfiesta.com");
```

💡 **Think of it like:** A remote control for your database — just press a button and it fetches what you need. 🎮

---

### 🧱 BLOCK 5: H2 Database (The Notebook)
**What it is:** A tiny database that lives inside a file on your computer.

**What it stores:**
- 📝 Customer names, emails, passwords
- 🍕 Menu items and prices
- 📦 Orders people placed
- 👤 Admin accounts

**The file is located at:** `data/foodfiesta.mv.db`

💡 **Think of it like:** A pocket diary where you write everything down. When you close the app, the diary still has all the info saved.

**Bonus:** You can also use PostgreSQL (a bigger, stronger database) when you're ready to launch.

---

### 🧱 BLOCK 6: Spring Security (The Bodyguard 🛡️)
**What it is:** A security system that protects the website.

**What it prevents:**
- ❌ Random people from seeing the admin dashboard
- ❌ Hackers from stealing data
- ❌ People from placing orders without logging in

**How it works:**
1. You log in with email + password
2. The app gives you a "session cookie" (like a stamp on your hand at a concert 🎟️)
3. Every time you visit a page, the app checks your stamp
4. If no stamp → you get sent back to login page

💡 **Think of it like:** A bouncer at a club who checks your ID before letting you in. 🚪

---

### 🧱 BLOCK 7: CSS (The Decorator 🎨)
**Files:** `main.css`, `Admin_Page.css`, etc.

**What it is:** Code that makes the website look beautiful.

**What it controls:**
- 🎨 Colors (gold, burgundy, cream)
- 📐 Layout (sidebar on left, content on right)
- 🌀 Animations (buttons that glow, cards that slide up)
- 🖼️ Fonts (fancy "Playfair Display" for headings)

💡 **Think of it like:** The interior designer who paints the walls, picks the furniture, and hangs the lights. 🪑

---

### 🧱 BLOCK 8: JavaScript (The Helper 🤹)
**File:** `Products.js`

**What it is:** Code that runs in your browser (not on the server).

**What it does (client-side magic):**
- 🔍 **Live Search** — Type in a search box and the table filters instantly (no page reload!)
- 📄 **CSV Export** — Click a button and download orders as an Excel file
- 🎊 **Confetti Animation** — Celebrate when an order succeeds

💡 **Think of it like:** A helper standing next to you who does things immediately without asking the chef.

---

### 🧱 BLOCK 9: Maven (The Builder 🏗️)
**File:** `pom.xml`, `mvnw.cmd`

**What it is:** A tool that downloads all the parts and builds the project.

**What it does:**
- 📥 Downloads Spring Boot, Thymeleaf, H2, etc. from the internet
- 🔗 Connects all the pieces together
- 📦 Packages everything into a `.jar` file
- 🏃 Runs the project with `.\mvnw.cmd spring-boot:run`

💡 **Think of it like:** A LEGO instruction manual that finds all the right pieces and puts them together. 📖

---

### 🧱 BLOCK 10: Swagger / OpenAPI (The Documentation 📚)
**URL:** http://localhost:8081/swagger-ui/index.html

**What it is:** A page that shows ALL the buttons/URLs your app has.

**What you can do there:**
- 👀 See every URL like `/admin/services`, `/addingProduct`, `/deleteUser/5`
- 🧪 Test the URLs directly from the browser
- 📋 See what data each URL needs

💡 **Think of it like:** A map of the entire restaurant showing every door, every room, and what's inside. 🗺️

---

## 👑 Project Features Explained (Like a Story)

### 🌐 SCENE 1: Home Page (`/home`)
A customer visits your website. They see:
- A beautiful homepage with gold decorations ✨
- Navigation bar: Home, Menu, About, Location
- A button to Login or Register


# Or with Docker Compose (includes PostgreSQL)
docker compose up -d
```

---

## 📦 Build

```bash
# Clean build
.\mvnw.cmd clean package

# Run tests
.\mvnw.cmd test
```

---

## 📁 Project Structure

```
Food-Fiesta-main/
├── src/main/
│   ├── java/com/example/demo/
│   │   ├── config/          # Security, OpenAPI, DataLoader
│   │   ├── controllers/     # Admin, User, Product, Order, Home
│   │   ├── entities/        # Admin, User, Product, Orders
│   │   ├── repositories/    # JPA Repositories
│   │   ├── services/        # Business logic layer
│   │   ├── loginCredentials/# Login DTOs
│   │   └── count/           # Utility (Logic.java)
│   └── resources/
│       ├── static/
│       │   ├── css/          # Main.css, Admin_Page.css, etc.
│       │   ├── JavaScript/   # Client-side scripts
│       │   └── Images/       # UI images & food photos
│       └── templates/        # Thymeleaf HTML templates
├── Dockerfile
├── docker-compose.yml
└── pom.xml
```

---

## 📄 License

Distributed under the MIT License. See [LICENSE](LICENSE) for details.

---

## 👨‍💻 Author

**rockyrakes**

[![GitHub](https://img.shields.io/badge/GitHub-rockyrakes-181717?logo=github)](https://github.com/rockyrakes)

---

> *"Where every meal is a royal feast"* 👑🍛
