# Generated by Django 2.1.3 on 2020-04-18 04:41

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('blog', '0007_auto_20200418_1340'),
    ]

    operations = [
        migrations.AlterField(
            model_name='post',
            name='public_code',
            field=models.CharField(choices=[('2', '4'), ('1', '3')], max_length=80, null=True),
        ),
    ]
