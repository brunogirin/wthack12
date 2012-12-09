'''
Created on 8 Dec 2012

@author: bruno
'''
from django.conf.urls import patterns, url

from maternity import views

urlpatterns = patterns('',
    url(r'^$', views.index, name='index'),
    url(r'^simple/$', views.simple, name='simple')
)
