resource "aws_ecr_repository" "product_api" {
  name                 = "product-api"
  image_tag_mutability = "MUTABLE"

  image_scanning_configuration {
    scan_on_push = true
  }

  tags = {
    Name = "product-api"
  }
}

output "ecr_repository_url" {
  value = aws_ecr_repository.product_api.repository_url
}
