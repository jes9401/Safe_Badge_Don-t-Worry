from django.urls import path
from . import views
from rest_framework import routers


#router = routers.DefaultRouter()
#router.register(r'getPost2', views.TestViewSet)

urlpatterns = [
    path('', views.post_list, name='post_list'),
    path('getPost/', views.getPost),
    path('<public_id>/',views.public),
    path('getPost/<personal_id>/',views.personal),
]