# Generated by Django 2.1.3 on 2020-04-16 05:59

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('web', '0004_auto_20200328_1739'),
    ]

    operations = [
        migrations.AddField(
            model_name='info',
            name='code',
            field=models.TextField(default='null', verbose_name='CODE'),
        ),
    ]
