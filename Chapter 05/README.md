# Chapter 5: Hexagonal Architecture (Ports and Adapters)

Welcome to the code samples for **Chapter 5**.

In this chapter, we explore how to decouple our Core Business Logic from external infrastructure (like databases, APIs, and the console) using the **Hexagonal Architecture** pattern (also known as Ports and Adapters).

## Project Structure

This chapter is divided into two distinct examples:

* **[5.4 Server Monitor](./5.4 ServerMonitor/)**
    * **Concept:** The Core Teaching Example.
    * **Scenario:** A server monitoring system that needs to alert admins via SMS (Twilio) or Console.
    * **Demonstrates:** Creating a Port (`IAlertPort`) to swap between a "Real" adapter and a "Dev" adapter.

* **[5.6 Crypto Tracker](./5.6 CryptoTracker/)**
    * **Concept:** The "In Action" Refactoring Exercise.
    * **Scenario:** A portfolio calculator that fetches Bitcoin prices.
    * **Demonstrates:** Refactoring tightly coupled `HttpClient` code into a testable Hexagonal structure using a "Fake" adapter.

## Prerequisites

* **.NET 8.0 SDK** (or newer) installed.
* A terminal or command prompt.

## Getting Started

Navigate to one of the folders above and check the `README.md` inside for specific running instructions.