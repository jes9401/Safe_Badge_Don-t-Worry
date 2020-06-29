from django.db import models

# Create your models here.
class Test1(models.Model):
    name=models.CharField(max_length=100)

