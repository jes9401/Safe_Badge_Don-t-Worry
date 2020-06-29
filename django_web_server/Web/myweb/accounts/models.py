from django.db import models
from django.contrib.auth.models import User
from web.models import Info

class Profile(models.Model):
    user = models.OneToOneField(User, on_delete=models.CASCADE)
    public_code = models.CharField(max_length = 500,default = 'null')
    personal_code = models.CharField(max_length = 500,default = 'null')
    def __str__(self):
        return f'{self.user.username} Profile'

    def save(self, *args, **kwargs):
        super().save(*args, **kwargs)

