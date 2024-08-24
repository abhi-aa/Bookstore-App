# Bookstore Application

This repository contains the implementation of a simple Bookstore Application as part of the COE528 Winter 2021 course project. The application utilizes JavaFX for its graphical user interface and follows the State Design Pattern to manage customer states based on their accumulated points.

## Project Overview

### Objectives

* Analyze, design, and implement a bookstore application.
* Use UML diagrams for system design.
* Apply the State Design Pattern to manage customer statuses.
* Implement the system using JavaFX for GUI development.

### Problem Description

The Bookstore Application allows users to log in as either an **Owner** or a **Customer**. Depending on the role, users can perform various actions:

* **Owner**:
  * Add or remove books.
  * Register or delete customers.
  * Manage customer data including username, password, and points.

* **Customer**:
  * View available books and their prices.
  * Purchase books and earn points.
  * Redeem points to reduce purchase costs.
  * View their current status (Silver/Gold) based on points.

### Key Features

* **Single-Window Interface**: The application operates within a single window, replacing screens rather than opening new ones.
* **State Management**: Customer status transitions between Silver and Gold based on their accumulated points.
* **Persistent Data**: Book and customer data are saved to files upon exit and reloaded on startup.

## UML Diagrams

The project includes two primary UML diagrams:

1. **Use Case Diagram**: Depicts the interactions between users (Owner, Customer) and the system.
2. **Class Diagram**: Shows the structure of the application, including the classes and their relationships.

## Design Pattern

The **State Design Pattern** is applied to manage the state of customers (Silver or Gold). The pattern allows the customer's behavior to change based on their accumulated points. The concrete states are represented by the `Silver` and `Gold` classes.
