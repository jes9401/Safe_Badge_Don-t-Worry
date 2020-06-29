from rest_framework import serializers
from blog.models import Post


class TestSerializer(serializers.ModelSerializer):
    class Meta:
        model = Post
        fields = ('public_code', 'title','text','author','created_date' )
