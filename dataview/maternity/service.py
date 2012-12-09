'''
Created on 8 Dec 2012

@author: bruno
'''
from riak import RiakClient
from django.conf import settings
import json

class MaternityService:
    def __init__(self):
        self.maternity_bucket = settings.RIAK_MATERNITY_BUCKET
        self.riak = RiakClient(host = settings.RIAK_DATABASE['HOST'],
                               port = settings.RIAK_DATABASE['PORT'])
    
    def get(self, pk):
        entity = self.riak.bucket(settings.RIAK_MATERNITY_BUCKET).get(str(pk))
        if entity.exists():
            data = json.loads(entity.get_data())
            data['statistics'] = sorted(data['statistics'].iteritems())
            return data
        else:
            return None
        