# Generated by Django 2.1.3 on 2020-04-27 04:51

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('blog', '0015_auto_20200426_2221'),
    ]

    operations = [
        migrations.AlterField(
            model_name='post',
            name='public_code',
            field=models.CharField(choices=[('성결유치원', '성결유치원'), ('의석유치원', '의석유치원')], max_length=80, null=True),
        ),
    ]
