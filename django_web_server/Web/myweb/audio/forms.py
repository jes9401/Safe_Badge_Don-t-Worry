from django import forms

from .models import Song

class AudioForm(forms.ModelForm):
    class Meta:
        model=Song
        fields = ('audio_file',)
