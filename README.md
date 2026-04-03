# Grokking Software Architecture: Official Code Repository

Welcome to the official companion code repository for **Grokking Software Architecture**!

This repository contains all the practical examples, anti-patterns, and refactoring exercises discussed throughout the book. Whether you are learning how to decouple chatty interfaces, apply SOLID principles, or transition from a Fat Controller to a Layered Architecture you will find the working code here.

---

## The Language-Agnostic Approach

Software architecture is not about syntax, frameworks, or libraries—it is about structure, communication, and managing complexity. 

To prove that these concepts are truly universal and language-agnostic, **every single example in this repository is provided in four distinct programming environments:**

* 🟣 **.NET (C#)**
* ☕ **Java**
* 🟩 **Node.js (JavaScript)** 
* 🐍 **Python**

Whether you are a backend Java engineer, a full-stack JavaScript developer, or a Python data specialist, you can follow along in the language you are most comfortable with. You can also compare the implementations side-by-side to see how different ecosystems tackle the exact same architectural goals!

---

## Repository Structure

The repository is organized chronologically by chapter. Inside each chapter folder, you will find subdirectories for the different programming languages, and within those, the specific section examples.

```text
📦 grokking-software-architecture
 ┣ 📂 Chapter02
 ┣ 📂 Chapter03
 ┃ ┣ 📂 C#
 ┃ ┣ 📂 Java
 ┃ ┣ 📂 Node
 ┃ ┗ 📂 Python
 ┣ 📂 Chapter04
 ┃ ┣ 📂 C#
 ┃ ┣ 📂 Java
 ┃ ┣ 📂 Node
 ┃ ┗ 📂 Python
 ┗ 📜 README.md (You are here)
 ```
Within the section folders, code is typically split into two states to clearly demonstrate the learning objective:

- **before/:** The anti-pattern, tightly coupled implementation, or missing abstraction.

- **after/:** The refactored, architecturally sound solution.

## How to Run the Code
Because each chapter introduces different concepts (from simple console apps to full interactive web servers), the specific instructions for running the code are located inside each chapter's folder.

### **To get started:**

1. Navigate to the Chapter you are currently reading (e.g., Chapter04/).

2. Open the README.md located in the root of that chapter's folder.

3. Follow the instructions for your preferred programming language.

### **General Prerequisites**
To run the code in this repository, you will need the standard runtime environments for the languages you choose to explore:

- **.NET:** .NET 6.0 SDK or higher

- **Java:** Java 17+ and Maven

- **Node.js:** Node.js v16+ and npm

- **Python:** Python 3.10+ (3.12 recommended)

## Recommended IDE: Visual Studio Code

Because this repository spans four different programming languages, you might not want to juggle four different heavy-duty IDEs just to read the companion code. 

We highly recommend using **[Visual Studio Code](https://code.visualstudio.com/)**. It is lightweight, free, and by installing a few official extensions, you can run, debug, and explore every single example in this book from one unified editor:

* **C# (.NET):** Install the *C# Dev Kit* extension by Microsoft.
* **Java:** Install the *Extension Pack for Java* by Microsoft.
* **Node.js:** JavaScript is supported right out-of-the-box.
* **Python:** Install the *Python* extension by Microsoft.

Simply open the root `grokking-software-architecture` folder in VS Code, and you will be able to seamlessly navigate between all four language implementations!

## 🐛 Issues and Contributions
If you spot a bug in the code, a typo in the documentation, or an architectural implementation that could be clearer, please feel free to open an Issue or submit a Pull Request! We want this repository to be the best possible learning resource for our readers and the software engineering community.

Happy architecting!

**-CodeLiftSleep**