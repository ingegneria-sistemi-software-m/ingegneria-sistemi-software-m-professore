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
with Diagram('conwayactorsqakArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxcells', graph_attr=nodeattr):
          player=Custom('player','./qakicons/symActorDynamicWithobj.png')
          gamebuilder=Custom('gamebuilder','./qakicons/symActorWithobjSmall.png')
          gamecontroller=Custom('gamecontroller','./qakicons/symActorWithobjSmall.png')
          gamemaster=Custom('gamemaster','./qakicons/symActorWithobjSmall.png')
          cell=Custom('cell','./qakicons/symActorDynamicWithobj.png')
     gamecontroller >> Edge( label='clearCell', **eventedgeattr, decorate='true', fontcolor='red') >> sys
     gamemaster >> Edge( label='startthegame', **eventedgeattr, decorate='true', fontcolor='red') >> sys
     gamemaster >> Edge( label='stopthecell', **eventedgeattr, decorate='true', fontcolor='red') >> sys
     gamemaster >> Edge( label='synch', **eventedgeattr, decorate='true', fontcolor='red') >> sys
     sys >> Edge( label='clearCell', **evattr, decorate='true', fontcolor='darkgreen') >> cell
     sys >> Edge( label='startthegame', **evattr, decorate='true', fontcolor='darkgreen') >> cell
     sys >> Edge( label='curstate', **evattr, decorate='true', fontcolor='darkgreen') >> cell
     sys >> Edge( label='synch', **evattr, decorate='true', fontcolor='darkgreen') >> cell
     sys >> Edge( label='stopthecell', **evattr, decorate='true', fontcolor='darkgreen') >> cell
     gamecontroller >> Edge(color='blue', style='solid',  decorate='true', label='<start &nbsp; stop &nbsp; >',  fontcolor='blue') >> gamemaster
     gamemaster >> Edge(color='blue', style='solid',  decorate='true', label='<gridEmpty &nbsp; >',  fontcolor='blue') >> gamecontroller
     gamebuilder >> Edge(color='blue', style='solid',  decorate='true', label='<activateMaster &nbsp; >',  fontcolor='blue') >> gamemaster
diag
