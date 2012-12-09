# Create your views here.
from django.shortcuts import render
from service import MaternityService


def index(request):
    service = MaternityService()
    entity = service.get('ALL')
    context = {'entity': entity}
    return render(request, 'maternity/index.html', context)