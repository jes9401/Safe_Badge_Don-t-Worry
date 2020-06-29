from rest_framework import serializers
from web.models import Info


class TestSerializer(serializers.ModelSerializer):
    class Meta:
        model = Info
        fields = ('cctvURL', 'gps','public_code','personal_code' )
