# Generated by Django 2.1.3 on 2020-04-26 09:43

from django.db import migrations, models
import django.utils.timezone


class Migration(migrations.Migration):

    dependencies = [
        ('web', '0009_auto_20200426_1836'),
    ]

    operations = [
        migrations.AddField(
            model_name='info',
            name='created_date',
            field=models.DateTimeField(default=django.utils.timezone.now),
        ),
    ]
