from django.conf.urls import patterns, include, url
import django.contrib.staticfiles.urls

# Uncomment the next two lines to enable the admin:
# from django.contrib import admin
# admin.autodiscover()

urlpatterns = patterns('',
    # Examples:
    # url(r'^$', 'dataview.views.home', name='home'),
    # url(r'^dataview/', include('dataview.foo.urls')),

    # Uncomment the admin/doc line below to enable admin documentation:
    # url(r'^admin/doc/', include('django.contrib.admindocs.urls')),

    # Uncomment the next line to enable the admin:
    # url(r'^admin/', include(admin.site.urls)),
    url(r'^maternity/', include('maternity.urls')),
)

urlpatterns += django.contrib.staticfiles.urls.staticfiles_urlpatterns()
