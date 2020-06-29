"""myweb URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/3.0/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path, include, re_path
from django.conf import settings
from django.conf.urls.static import static
from rest_framework import routers
from web import views as web_views
from blog import views as blog_views
router = routers.DefaultRouter()
router.register(r'data', web_views.TestViewSet)
router.register(r'getPost', blog_views.TestViewSet)

urlpatterns = [
    path('admin/', admin.site.urls),
    path('web/', include('web.urls')),
    path('accounts/', include('accounts.urls')),
    path('', include(router.urls)),
    path('blog/',include('blog.urls')),
    path('rfid/',include('rfid.urls')),
    path('audio/',include('audio.urls')),
    path('android/',include('android.urls')),

] + static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)
