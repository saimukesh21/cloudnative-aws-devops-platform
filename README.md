# CloudNative Application Delivery Platform on AWS

A production-style DevOps portfolio project demonstrating automated application delivery from source control to Kubernetes on AWS.

## Architecture

```text
Developer
   |
   v
GitHub
   |
   v
Jenkins
   |
   +--> Maven Build
   |
   +--> Unit Tests
   |
   +--> SonarQube
   |
   +--> Docker Build
   |
   v
Amazon ECR
   |
   v
Amazon EKS
   |
   +--> Helm
   |
   +--> AWS Load Balancer Controller
   |
   v
Application Load Balancer
   |
   v
Spring Boot Product API
   |
   v
Amazon RDS MySQL

Monitoring:
Prometheus --> Grafana
```

## Project Highlights

* Spring Boot REST API using Java 17
* Maven build and unit testing
* Jenkins CI pipeline
* SonarQube code-quality analysis and quality gate
* Docker containerization
* Amazon ECR image storage
* Amazon EKS Kubernetes deployment
* Helm-based application deployment
* AWS Load Balancer Controller
* Application Load Balancer
* Amazon RDS MySQL
* Infrastructure as Code with Terraform
* Prometheus and Grafana monitoring
* Kubernetes health probes
* Helm rollback testing
* Failure testing and deployment verification
* Security scanning with Gitleaks and Trivy
* Documented project evidence

## Repository Structure

```text
.
├── application/          # Spring Boot application
├── docker/               # Dockerfile
├── helm/                 # Helm chart
├── kubernetes/           # Kubernetes manifests
├── terraform/            # AWS infrastructure
├── evidence/             # Project implementation evidence
├── Jenkinsfile           # Jenkins CI pipeline
└── README.md
```

## CI/CD Flow

```text
GitHub
  |
  v
Jenkins
  |
  +--> Checkout
  +--> Maven Build
  +--> Unit Tests
  +--> SonarQube Analysis
  +--> Quality Gate
  +--> Docker Build
  +--> ECR
  +--> Helm Deployment
  +--> Kubernetes Verification
```

## Application

The project contains a Spring Boot Product API.

Local application port:

```text
8090
```

The application exposes health and Prometheus metrics through Spring Boot Actuator.

## Local Development

### Prerequisites

* Java 17
* Maven
* Docker Desktop
* kubectl
* Helm
* kind or another local Kubernetes cluster

### Build and Test

```bash
cd application
mvn clean test
```

### Docker Build

```bash
docker build -f docker/Dockerfile -t product-api:local .
```

### Kubernetes Deployment

The application can be deployed locally using the Helm chart under:

```text
helm/product-api/
```

## AWS Infrastructure

Terraform was used to provision the AWS infrastructure required for the cloud deployment.

The project used:

* Amazon VPC
* Public and private subnets
* Amazon EKS
* EKS IAM roles
* Amazon ECR
* AWS Load Balancer Controller
* Application Load Balancer
* Amazon RDS MySQL

AWS infrastructure was created only for project testing and was destroyed after the implementation and evidence collection were completed.

## Kubernetes

The application was packaged as a Helm chart.

The deployment includes:

* Rolling updates
* Resource requests and limits
* Startup probe
* Readiness probe
* Liveness probe
* Kubernetes Service
* Configurable container image
* Configurable replica count

## Monitoring

Monitoring was implemented with:

* Prometheus
* Grafana
* kube-state-metrics
* node-exporter
* Alertmanager

Prometheus targets and Grafana Kubernetes dashboards were verified during the project.

## Security

Security practices used in the project include:

* No AWS access keys committed to Git
* Kubernetes secrets used for sensitive application configuration
* Jenkins credentials for CI/CD authentication
* Gitleaks secret scanning
* Trivy container image scanning
* Terraform state excluded from Git
* Sensitive `.env` and credential files excluded from Git

## Testing

The project included:

* Maven unit tests
* SonarQube quality gate
* Docker container testing
* Kubernetes deployment verification
* Application health checks
* Helm rollback testing
* Prometheus target verification
* Grafana dashboard verification
* Failure testing
* Final AWS cleanup verification

## Evidence

Project evidence is available under:

```text
evidence/
```

Evidence is organized by project stage:

```text
01-setup
02-local-build
03-docker
04-jenkins-sonarqube
05-terraform
06-eks
07-helm-rollback
08-alb-rds
09-monitoring
10-failure-testing
11-cleanup
```

## Cleanup

All AWS resources created for the project were destroyed after testing.

Final cleanup included verification of:

* EKS
* RDS
* ECR
* VPC
* Application Load Balancer
* Terraform state

## Disclaimer

This repository is a portfolio/learning project. Infrastructure configurations are intentionally kept small to control AWS usage and are not intended as a universal production reference architecture.
