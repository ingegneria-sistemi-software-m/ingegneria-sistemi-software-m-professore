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
with Diagram('conwaycellsqakArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxcells', graph_attr=nodeattr):
          cell=Custom('cell','./qakicons/symActorWithobjSmall.png')
          orchestrator=Custom('orchestrator','./qakicons/symActorWithobjSmall.png')
     sys >> Edge( label='allcellready', **evattr, decorate='true', fontcolor='darkgreen') >> cell
     sys >> Edge( label='startthegame', **evattr, decorate='true', fontcolor='darkgreen') >> cell
     sys >> Edge( label='curstate', **evattr, decorate='true', fontcolor='darkgreen') >> cell
     sys >> Edge( label='synch', **evattr, decorate='true', fontcolor='darkgreen') >> cell
     orchestrator >> Edge( label='allcellready', **eventedgeattr, decorate='true', fontcolor='red') >> sys
     orchestrator >> Edge( label='startthegame', **eventedgeattr, decorate='true', fontcolor='red') >> sys
     orchestrator >> Edge( label='synch', **eventedgeattr, decorate='true', fontcolor='red') >> sys
     cell >> Edge(color='magenta', style='solid', decorate='true', label='<addtogame<font color="darkgreen"> addedtogame</font> &nbsp; >',  fontcolor='magenta') >> orchestrator
     cell >> Edge(color='blue', style='solid',  decorate='true', label='<cellcreated &nbsp; cellready &nbsp; >',  fontcolor='blue') >> orchestrator
diag
