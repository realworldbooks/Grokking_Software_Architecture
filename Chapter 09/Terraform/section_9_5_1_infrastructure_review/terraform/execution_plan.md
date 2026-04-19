# Listing 9.4: The Architect's Execution Plan Review

### THE CONCEPT: "Look Before You Leap"
The Execution Plan (generated via `terraform plan`) is the bridge between your **Declarative Code** and the **Physical Reality** of the cloud. It tells you exactly how the engine will resolve the difference between what you asked for and what currently exists.

---

## The Plan Output
```hcl
Terraform will perform the following actions:

  # aws_instance.web_server will be created
  + resource "aws_instance" "web_server" {
      + ami           = "ami-0c55b159cbfafe1f0"
      + instance_type = "t3.micro"
    }

  # aws_security_group.firewall will be updated in-place
  ~ resource "aws_security_group" "firewall" {
      ~ description = "Allow web traffic" -> "Allow web and API traffic"
    }

  # aws_db_instance.primary_db will be destroyed
  - resource "aws_db_instance" "primary_db" {
      - engine = "postgres"
    }

Plan: 1 to add, 1 to change, 1 to destroy.
```

# SENIOR ARCHITECT'S ANALYSIS

1. **The Green Light (+):** *Additive Change*
 
   **Action:** aws_instance.web_server will be created.
   
   - *Architect's Note:* This is generally low risk. We are adding a new "Cattle" member to our herd. However, the architect should verify: Does this AMI match our security baseline? and Are we within our region's instance quota?

2. **The Yellow Light (~):** *In-Place Update* 

   **Action:** aws_security_group.firewall will be updated.
   
   - *Architect's Note:* Notice the ~ symbol. This means Terraform can change the resource without destroying it. This is a non-disruptive change. We are simply updating a text description. High safety, low impact.

3. **THE RED LIGHT (-):** *Destructive Change*
    
    **Action:** aws_db_instance.primary_db will be destroyed.*
   
   - *CRITICAL DANGER:* The - symbol is the most dangerous character in the cloud. Terraform has decided that the current database no longer matches the blueprint and must be deleted.
   
   - *The Architect's Query:* Why is this being destroyed? 
      - Did someone change the DB engine? 
      - Did they change the name? 
      
    If you hit "Apply" now, all production data is gone. This is why we use prevent_destroy lifecycle hooks!
      
## 🏁 THE SUMMARY VERDICT

| Symbol | Action | Risk Level | Architectural Implication |
| :---: | :--- | :--- | :--- |
| **`+`** | **Create** | Low | **Additive:** Adding new resources. Generally safe, but review for cost and security baseline compliance. |
| **`~`** | **Update** | Medium | **In-Place:** Modifying attributes of existing resources. Check if the change causes a brief service "hiccup" or restart. |
| **`-`** | **Destroy** | **CRITICAL** | **Destructive:** Resource will be deleted. **STOP IMMEDIATELY.** Ensure a data migration or backup plan is active. |
| **`-/+`** | **Replace** | **CRITICAL** | **Recreation:** A change (like a DB name) forces a delete and create. This is "Destroy" in disguise; data will be lost. **Total data loss risk.** |

**REALITY CHECK:** A junior developer sees "Plan: 1 to add, 1 to change, 1 to destroy" and thinks, "Cool, it's working." A Clarity Engineer sees that "1 to destroy" and realizes they are 30 seconds away from a massive data loss event.  

When reading a plan, don't just look at the bottom summary line (e.g., Plan: 1 to add, 1 to change, 1 to destroy). Always scroll up and locate the Red Light (-) and Replacement (-/+) symbols. These are the career-killers. If you see them on a stateful resource (like a Database or an S3 bucket), your primary job is to find out why the engine thinks that resource is no longer compatible with your code.

---

### 2. What else should an Architect look for in a Plan?

To further level up this lesson, here are the "Invisible" red flags we look for when reviewing a plan:

* **Forces New Resource:** Sometimes you see `~` but with a note saying `(forces replacement)`. This is a "Destroy-and-Recreate" disguised as an update. If this happens to a database or a storage volume, it's just as lethal as a `-` delete.
* **The "Wall of Text":** In large environments, a plan might change 500 things. Senior architects look for **Unexpected Changes**. If you only meant to change a description but 10 servers are being replaced, there is a logic error in your module dependencies.
* **Sensitive Values:** If the plan shows `password = (sensitive value)`, the architect must verify: *Are we sure this isn't being logged in plaintext somewhere in our CI/CD runner?* (Relates to Chapter 11: Security).
