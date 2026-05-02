# Section 9.5.3: The Architect's Execution Plan Review (Terraform Plan)

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

# A CLARITY ENGINEER'S ANALYSIS

### 1. The Green Light (+): Additive Change
**Action:** `aws_instance.web_server` will be created.

* **Architect's Note:** This is generally safe. Adding new resources rarely breaks existing traffic unless you hit an account limit (like a maximum number of allowed VPCs).

### 2. The Yellow Light (~): In-Place Update
**Action:** `aws_security_group.firewall` will be updated in-place.

* **Architect's Note:** The engine has determined it can modify this resource *without* turning it off. This is a seamless update. However, the architect still needs to verify that the logic change (e.g., altering a firewall rule) doesn't accidentally expose a private subnet to the public internet.

### 3. The Red Light (-): Destructive Change (⚠️ DANGER)
**Action:** `aws_db_instance.primary_db` will be destroyed.

* **Architect's Note:** This is the nightmare scenario. A junior developer might see the summary "Plan: 1 to add, 1 to change, 1 to destroy" and think, "Cool, it's working." A Clarity Engineer sees that "1 to destroy" and realizes they are 30 seconds away from a massive data loss event.  

When reading a plan, don't just look at the bottom summary line. Always scroll up and locate the Red Light (`-`) and Replacement (`-/+`) symbols. These are the career-killers. If you see them on a stateful resource (like a Database or an S3 bucket), your primary job is to find out *why* the engine thinks that resource is no longer compatible with your code.

---

## What else should an Architect look for in a Plan?

To further level up this lesson, here are the "Invisible" red flags we look for when reviewing a plan:

* **Forces New Resource:** Sometimes you see `~` but with a note saying `(forces replacement)`. This is a "Destroy-and-Recreate" action disguised as a simple update. If this happens to a database or a storage volume, it's just as lethal as a `-` delete.
* **The "Wall of Text":** In large environments, a plan might change 500 things. Senior architects look for **Unexpected Changes**. If you only meant to change a description but 10 servers are suddenly being replaced, there is a logic error in your module dependencies.
* **Sensitive Values:** If the plan shows `password = (sensitive value)`, the architect must verify: *Are we sure this isn't being logged in plaintext somewhere in our CI/CD runner logs?*