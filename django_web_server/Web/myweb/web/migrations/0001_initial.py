# Generated by Django 3.0.4 on 2020-03-19 07:34

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='HomeInfo',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('cctvURL', models.TextField(verbose_name='CCTV URL')),
            ],
            options={
                'verbose_name': '데이터1',
                'verbose_name_plural': '데이터1',
                'db_table': 'Info1',
            },
        ),
    ]
