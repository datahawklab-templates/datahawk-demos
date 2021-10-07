#!/usr/bin/python3
from java.io import FileInputStream
import time
import getopt
import sys
import re


#=======================================================================================
# load properties domain creation template
#=======================================================================================
properties = ''
try:
   opts, args = getopt.getopt(sys.argv[1:], "p:h::", ["properies="])
except getopt.GetoptError:
   print 'create_domain.py -p <path-to-properties-file>'
   sys.exit(2)
for opt, arg in opts:
   if opt == '-h':
      print 'create_domain.py -p <path-to-properties-file>'
      sys.exit()
   elif opt in ("-p", "--properties"):
      properties = arg
print 'properties=', properties


propInputStream = FileInputStream(properties)
configProps = Properties()
configProps.load(propInputStream)
wlsPath = configProps.get("path.wls")
domainConfigPath = configProps.get("path.domain.config")
appConfigPath = configProps.get("path.app.config")
domainName = configProps.get("domain.name")
username = configProps.get("domain.username")
password = configProps.get("domain.password")
adminPort = configProps.get("domain.admin.port")
adminAddress = configProps.get("domain.admin.address")
adminPortSSL = configProps.get("domain.admin.port.ssl")

#=======================================================================================
# load domain creation template
#=======================================================================================
selectTemplate('Basic WebLogic Server Domain')
loadTemplates()

#=======================================================================================
# Create admin server
#=======================================================================================
cd('/Security/base_domain/User/' + username)
cmo.setPassword(password)
cd('/Server/AdminServer')
cmo.setName('AdminServer')
cmo.setListenPort(int(adminPort))
cmo.setListenAddress(adminAddress)
create('AdminServer', 'SSL')
cd('SSL/AdminServer')
set('Enabled', 'True')
set('ListenPort', int(adminPortSSL))


#=======================================================================================
# Create a JMS Server.
#=======================================================================================
cd('/')
create('myJMSServer', 'JMSServer')

#=======================================================================================
# Create a JMS System resource.
#=======================================================================================
cd('/')
create('myJmsSystemResource', 'JMSSystemResource')
cd('JMSSystemResource/myJmsSystemResource/JmsResource/NO_NAME_0')

#=======================================================================================
# Create a JMS Queue and its subdeployment.
#=======================================================================================
myq = create('myQueue', 'Queue')
myq.setJNDIName('jms/myqueue')
myq.setSubDeploymentName('myQueueSubDeployment')

cd('/')
cd('JMSSystemResource/myJmsSystemResource')
create('myQueueSubDeployment', 'SubDeployment')

#=======================================================================================
# assign all servers
#=======================================================================================
#
cd('/')
assign('JMSServer', 'myJMSServer', 'Target', 'AdminServer')
assign('JMSSystemResource.SubDeployment', 'myJmsSystemResource.myQueueSubDeployment', 'Target', 'myJMSServer')
#assign('JDBCSystemResource', 'myDataSource', 'Target', 'AdminServer')

#=======================================================================================
# Create domain
#=======================================================================================
#
setOption('AppDir', appConfigPath + '/' + domainName)
writeDomain(domainConfigPath + '/' + domainName)
closeTemplate()
exit()
