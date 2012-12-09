# Create your views here.
from django.shortcuts import render
from service import MaternityService
from charts import BarChart

def index(request):
    service = MaternityService()
    entity = service.get('ALL')
    data = [
            (k, [
                 ('ALL', v['total'])
                 ]) for k, v in entity['statistics']
            ]
    context = {
               'entity': entity,
               'chart': BarChart(600, 400, data)
               }
    return render(request, 'maternity/index.html', context)

def simple(request):
    if 'field' in request.GET:
        field = request.GET['field']
    elif 'field' in request.POST:
        field = request.POST['field']
    else:
        field = 'total'
    service = MaternityService()
    results = service.get_authorities()
    keys = []
    data = []
    for r in results:
        key = r[1]['code']
        name = r[1]['name']
        series = []
        for k, v in sorted(r[1]['statistics'].iteritems()):
            series.append((k, get_field(v, field)))
        data.append((key, series))
        keys.append({
                     'key': key,
                     'name': name
                     })
    #data = [
    #        (r[1]['name'], [
    #                     (k, get_field(v, field)) for k, v in r[1]['statistics']
    #                     ]) for r in results]
    context = {
               'chart': BarChart(600, 400, sorted(data)),
               'keys': sorted(keys),
               'selected': field,
               'fields': [
                          {'value': 'total', 'text': 'Total'},
                          {'value': 'person.doctor', 'text': 'Delivered by the doctor'},
                          {'value': 'person.midwife', 'text': 'Delivered by the midwife'},
                          {'value': 'delivery.caesarean.elective', 'text': 'Elective C-section'}
                          ]
               }
    return render(request, 'maternity/simple.html', context)
    
def get_field(data, field):
    r = data
    for f in field.split('.'):
        r = r[f]
    return r