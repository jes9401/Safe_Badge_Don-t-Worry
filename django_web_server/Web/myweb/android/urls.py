from django.urls import path
from . import views


urlpatterns = [
    path('raspOnOff/<personal_id>/', views.raspOnOff),
]