### conda install diagrams
from diagrams import Cluster, Diagram, Edge
from diagrams.custom import Custom
import os
os.environ['PATH'] += os.pathsep + 'C:/Program Files/Graphviz/bin/'

graphattr = {     #https://www.graphviz.org/doc/info/attrs.html
    'fontsize': '22',
}

nodeattr = {   
    'fontsize': '22',
    'bgcolor': 'lightyellow'
}

eventedgeattr = {
    'color': 'red',
    'style': 'dotted'
}
evattr = {
    'color': 'darkgreen',
    'style': 'dotted'
}
with Diagram('eurekaexampleArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxeurekademousage', graph_attr=nodeattr):
          aserviceusage=Custom('aserviceusage','./qakicons/symActorWithobjSmall.png')
     with Cluster('ctxeureka', graph_attr=nodeattr):
          aservice=Custom('aservice(ext)','./qakicons/externalQActor.png')
     aserviceusage >> Edge( label='alarm', **eventedgeattr, decorate='true', fontcolor='red') >> sys
     aserviceusage >> Edge(color='magenta', style='solid', decorate='true', label='<r1<font color="darkgreen"> r1reply</font> &nbsp; >',  fontcolor='magenta') >> aservice
diag
