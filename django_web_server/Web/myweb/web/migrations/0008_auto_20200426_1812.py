# Generated by Django 2.1.3 on 2020-04-26 09:12

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('web', '0007_auto_20200417_1108'),
    ]

    operations = [
        migrations.AlterField(
            model_name='info',
            name='id',
            field=models.AutoField(primary_key=True, serialize=False),
        ),
    ]
