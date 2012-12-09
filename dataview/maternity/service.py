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
    
    def get_authorities(self):
        query = self.riak.add(settings.RIAK_MATERNITY_BUCKET)
        query.map('''
            function(v) {
                var data = JSON.parse(v.values[0].data);
                if(data.type == 'AUTHORITY') {
                    return [[v.key, data]];
                }
                return [];
            }
        ''')
        return query.run()
        #data = []
        #for result in query.run():
        #    d = result[1]
            #dj = json.loads(d)
        #    data.append((result[0], d))
        #return data
        #return [
        #        (result[0], json.loads(result[1]))
        #        for result in query.run()
        #        ]