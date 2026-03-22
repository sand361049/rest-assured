# OPPS in Automation Testing

In automation testing, Object-Oriented Programming (OOP) is the backbone that transforms a collection of scripts into a professional, maintainable framework. It allows you to model the application as objects rather than just lines of code.

### Here are the key areas where you can apply the four pillars of OOP:

**1. Abstraction (Hiding Implementation Details)**
In automation, you want to interact with elements without worrying about the underlying locator logic in every test case.

Common Use Case: Creating a BasePage or BaseTest class. You define high-level methods like clickElement() or waitForElement().

The Benefit: Your actual test scripts look like human-readable steps (e.g., loginPage.login(user, pass)) while the complex Selenium or Playwright logic is hidden away.

**2. Encapsulation (Data Hiding and Bundling)**
This is most famously used in the Page Object Model (POM) design pattern.

Common Use Case: Each web page is a class. The locators (buttons, text fields) are private variables, and the actions (clicking, typing) are public methods.

The Benefit: If a developer changes the ID of a "Submit" button, you only update it in one variable within that specific page class. Your test scripts remain untouched because they only call the public method.

**3. Inheritance (Reusability of Common Logic)**
Inheritance prevents you from writing the same setup and teardown code repeatedly.

Common Use Case: A BaseTest class contains methods for @BeforeMethod (initializing the driver) and @AfterMethod (closing the browser). Every specific test class (e.g., LoginTest, CheckoutTest) "extends" or "inherits" from this BaseTest.

The Benefit: You don't have to initialize the browser or load config files in every single test file.

**4. Polymorphism (Flexibility in Execution)**
Polymorphism allows one interface to represent different underlying forms, which is vital for cross-browser and multi-platform testing.

Method Overloading: You might have a search() method that takes a String (keyword), and another search() method that takes a String and an Int (keyword and category ID).

Method Overriding: You have a generic click() method in your BasePage, but for a specific "Custom Dropdown" element that requires a special JavaScript click, you "override" that method in the specific page class.

The Benefit: You can write a single script that runs on Chrome, Firefox, or Safari by passing different driver objects to the same interface.

## Where have you used the OPPS concepts in your Project.
OPPS is the backbone that transforms collection of scripts into a professional, maintainable framework and it is used heavily used in my project. Here are
few examples from my project. 
1.**Base test Class (Inheritance)** : In my project there is base class which contains methods for *`@BeforeMethod` (initializing the driver)* and *`@AfterMethod` (closing the browser)*. Every specific test class  "inherits" from this BaseTest.
2.**POM (Encapsulation)**: We are using Page Object Model which serves purpose of `encapsulation`. Each Page is class. The locators like `buttons`, `text fields` etc are private variables, and the actions like *`clicking`*, *`typing`* are public methods 
3.**Interface (Polymorphism)**: We are using interface to define contract/ or `dependency injection`. For example single script can run on different browsers like Crome,Firefox etc just by passing different driver objects to same interface
