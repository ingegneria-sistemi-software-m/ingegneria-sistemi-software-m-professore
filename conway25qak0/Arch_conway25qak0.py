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
with Diagram('conway25qak0Arch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxconway0', graph_attr=nodeattr):
          conway0=Custom('conway0','./qakicons/symActorWithobjSmall.png')
          guicmdpereceiver=Custom('guicmdpereceiver','./qakicons/symActorWithobjSmall.png')
     sys >> Edge( label='kernel_rawmsg', **evattr, decorate='true', fontcolor='darkgreen') >> conway0
     sys >> Edge( label='kernel_rawmsg', **evattr, decorate='true', fontcolor='darkgreen') >> guicmdpereceiver
diag
