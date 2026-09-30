terraform {
  required_version = ">= 1.15.0"

  required_providers {
    aws = {
      source = "hashicorp/aws"
    }
  }
}

provider "aws" {
  region = "ap-south-1"

  default_tags {
    tags = {
      Project     = "cloudnative-aws-devops-platform"
      Environment = "portfolio"
      ManagedBy   = "terraform"
    }
  }
}
module "ecr" {
  source = "./ecr"
}
module "vpc" {
  source = "./vpc"
}

module "iam" {
  source = "./iam"
}
