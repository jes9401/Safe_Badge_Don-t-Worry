from django.urls import path
from . import views


urlpatterns = [
    path('upload/', views.upload, name='upload'),
    path('audios/', views.audio_list,name='audio_list'),
    path('audios/upload/',views.upload_audio,name='upload_audio'),
    path('getFile/', views.getFile),
    path('Android/',views.Android),
    path('recordings/',views.RecordingList.as_view()),
]