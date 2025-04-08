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
with Diagram('conwaycellsnqakaiveArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxcells', graph_attr=nodeattr):
          cellbuilder=Custom('cellbuilder','./qakicons/symActorWithobjSmall.png')
          cell=Custom('cell','./qakicons/symActorDynamicWithobj.png')
     sys >> Edge( label='clearCell', **evattr, decorate='true', fontcolor='darkgreen') >> cell
     sys >> Edge( label='startthegame', **evattr, decorate='true', fontcolor='darkgreen') >> cell
     sys >> Edge( label='curstate', **evattr, decorate='true', fontcolor='darkgreen') >> cell
     sys >> Edge( label='synch', **evattr, decorate='true', fontcolor='darkgreen') >> cell
     sys >> Edge( label='stopthecell', **evattr, decorate='true', fontcolor='darkgreen') >> cell
diag
