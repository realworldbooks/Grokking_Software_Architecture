# ----------------------------------------------------------------------------------
# Listing 9.3: The Architect's Infrastructure Review
# 
# DESIGN NOTE:
# This Terraform file represents the "Blueprint" of our cloud environment. 
# Unlike a script that explains HOW to build a server, this file describes 
# WHAT the environment should look like (Declarative).
# 
# ARCHITECTURAL CRITIQUE:
# As an architect, your job isn't just to write this code, but to peer-review 
# the constraints it creates. Every line in this file represents a cost, 
# a performance ceiling, or a security boundary.

# ARCHITECTURAL THEME: Infrastructure is the "Physical Contract" of Software.
# While the code is declarative, the consequences are very much imperative: 
# costs accrue, latency occurs, and data can be lost.
# ----------------------------------------------------------------------------------

# 1. THE PROVIDER
provider "aws" {
  region = "us-east-1" #A
}

# 2. THE DATABASE
resource "aws_db_instance" "primary_db" {
  identifier        = "user-profile-database"
  engine            = "postgres"
  instance_class    = "db.t3.micro"  #B   
  allocated_storage = 50             #C
  
  # CRITICAL OMISSION: No 'multi_az' defined. 
  # This database lives in a single data center. If that DC floods, we are offline.
}

# 3. THE STORAGE
resource "aws_s3_bucket" "profile_pictures" {
  bucket = "shopzilla-user-profiles-prod"  #D
  
  # CRITICAL OMISSION: No 'versioning' or 'lifecycle_rules'.
  # Accidental deletions are permanent. Costs will grow forever without cleanup.
}

# 4. THE SECURITY
resource "aws_s3_bucket_public_access_block" "secure_pictures" {
  bucket                  = aws_s3_bucket.profile_pictures.id
  block_public_acls       = true
  block_public_policy     = true   #E 
}

# ----------------------------------------------------------------------------------
# ARCHITECT'S SENIOR NOTES:
# ----------------------------------------------------------------------------------
# #A: BLAST RADIUS CONCERN: us-east-1 is the most crowded AWS region. 
#     History shows it is the most likely to have service-wide outages. 
#     Senior architects often choose us-east-2 (Ohio) or us-west-2 (Oregon) 
#     to reduce the "Neighbor Noise" effect.
#
# #B: PERFORMANCE CEILING: The 't3' series is for "burstable" workloads. 
#     This is a trap for databases. Once you run out of "CPU Credits," your 
#     app will time out on every query. We should look at 'm6g' for production.
#
# #C: OPERATIONAL RISK: 50GB is static. 
#     In modern cloud architecture, we should use 'max_allocated_storage'. 
#     If the DB hits 50GB and cannot grow, it goes into 'read-only' mode, 
#     effectively killing the write-path of our application.
#
# #D: COMPLIANCE & RECOVERY: Does this bucket have 'versioning' enabled? 
#     If a user (or a bug) overwrites a file, without versioning, the data 
#     is unrecoverable. IaC should explicitly define our Recovery Point Objective (RPO).
#
# #E: DEFENSE IN DEPTH: Blocking public access is the bare minimum. 
#     The architect should also ask: Are we using server-side encryption (SSE)? 
#     Are we using IAM Roles (Least Privilege) to ensure only the app can see it?
# ----------------------------------------------------------------------------------