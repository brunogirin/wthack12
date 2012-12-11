Rewired State Welcome Trust Open Science Hack 2012
==================================================

The Brief
---------

For this hack I decided to use data from [HESonline](http://www.hesonline.nhs.uk/)
and in particular maternity data for 2011-12. AS the data came as an Excel
spreadsheet, the first task was to extract it from the spreadsheet and store
it into a more accessible format. I chose to store it as JSON objects in
Riak. I then built a small Django application that creates charts based on
the data. The file structure reflects this:
* The `dataload` directory contains a command line Java application that
  uses [Apache POI](http://poi.apache.org/) to read the Excel file and then
  loads the data into a [Riak](http://basho.com/products/riak-overview/)
  database.
* The `dataview` directory contains a [django](https://www.djangoproject.com/)
  application that uses the Riak data to product graphs.

![Application screen shot](http://brunogirin.github.com/wthack12/screenshots/index.png)

License
-------

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

