# moonphases
A project to build a visualization of the different phases of the moon.


# Package contents:

*  /data
    -  Repository of data (currently has nothing in it)
*  /db_comps
    -  Initial dashboard components (not currently deployable)
    -  Requires a Pentaho server with a data source called "moon"
    -  exportManifest.xml document is what allows it to be uploaded into the server environment
    -  /Moon/test_dashboards/load_dataintotable_csv.ktr is designed to take data from a base format and put it into the format reruired for the dashboard.  This may the basis of our integration between the visual and the data
