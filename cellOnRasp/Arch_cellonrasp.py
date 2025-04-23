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
with Diagram('cellonraspArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxcellonrasp', graph_attr=nodeattr):
          cell_1_1=Custom('cell_1_1','./qakicons/symActorWithobjSmall.png')
          perceiver=Custom('perceiver','./qakicons/symActorWithobjSmall.png')
     sys >> Edge( label='clearCell', **evattr, decorate='true', fontcolor='darkgreen') >> cell_1_1
     sys >> Edge( label='startthegame', **evattr, decorate='true', fontcolor='darkgreen') >> cell_1_1
     sys >> Edge( label='stopthecell', **evattr, decorate='true', fontcolor='darkgreen') >> cell_1_1
     sys >> Edge( label='curstate', **evattr, decorate='true', fontcolor='darkgreen') >> cell_1_1
     sys >> Edge( label='alarm', **evattr, decorate='true', fontcolor='darkgreen') >> perceiver
     sys >> Edge( label='kernel_rawmsg', **evattr, decorate='true', fontcolor='darkgreen') >> perceiver
diag
