from django.urls import path, include
from rest_framework import routers
from . import views

app_name = 'web'
urlpatterns = [
    path('', include('rest_framework.urls', namespace='rest_framework_category')),
    path('raspberry/<personal_id>/', views.raspberry),
    path('jes/',views.jes),
    path('getUser/',views.getUser),
    path('getInfo/<personal_id>/',views.getInfo),
]