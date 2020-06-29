from django.urls import path
from . import views


urlpatterns = [
    path('<personal_id>/', views.rfid),
]

