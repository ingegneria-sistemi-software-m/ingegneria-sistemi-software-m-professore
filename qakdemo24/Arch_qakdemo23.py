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
with Diagram('qakdemo23Arch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxdemo0', graph_attr=nodeattr):
          demo0=Custom('demo0','./qakicons/symActorSmall.png')
          perceiver=Custom('perceiver','./qakicons/symActorSmall.png')
          sender=Custom('sender','./qakicons/symActorSmall.png')
     sys >> Edge( label='alarm', **evattr, decorate='true', fontcolor='darkgreen') >> perceiver
     sender >> Edge( label='alarm', **eventedgeattr, decorate='true', fontcolor='red') >> sys
     sender >> Edge(color='blue', style='solid',  decorate='true', label='<msg1 &nbsp; msg2 &nbsp; >',  fontcolor='blue') >> demo0
diag
