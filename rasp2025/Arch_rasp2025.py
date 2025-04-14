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
with Diagram('rasp2025Arch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxrasp', graph_attr=nodeattr):
          raspactor=Custom('raspactor','./qakicons/symActorWithobjSmall.png')
          ledobserver=Custom('ledobserver','./qakicons/symActorWithobjSmall.png')
          ledperceiver=Custom('ledperceiver','./qakicons/symActorWithobjSmall.png')
          ledreactor=Custom('ledreactor','./qakicons/symActorWithobjSmall.png')
     raspactor >> Edge( label='ledchangedevent', **eventedgeattr, decorate='true', fontcolor='red') >> sys
     sys >> Edge( label='ledchangedevent', **evattr, decorate='true', fontcolor='darkgreen') >> ledobserver
     sys >> Edge( label='ledstatelocalevent', **evattr, decorate='true', fontcolor='darkgreen') >> ledobserver
     sys >> Edge( label='ledvaluestream', **evattr, decorate='true', fontcolor='darkgreen') >> ledobserver
     sys >> Edge( label='ledinfoevent', **evattr, decorate='true', fontcolor='darkgreen') >> ledobserver
     sys >> Edge( label='ledchangedevent', **evattr, decorate='true', fontcolor='darkgreen') >> ledperceiver
     sys >> Edge( label='ledstatelocalevent', **evattr, decorate='true', fontcolor='darkgreen') >> ledperceiver
     sys >> Edge( label='ledvaluestream', **evattr, decorate='true', fontcolor='darkgreen') >> ledperceiver
     sys >> Edge( label='ledinfoevent', **evattr, decorate='true', fontcolor='darkgreen') >> ledperceiver
     raspactor >> Edge( label='ledvaluestream', **eventedgeattr, decorate='true', fontcolor='red') >> ledreactor
     sys >> Edge( label='ledchangedevent', **evattr, decorate='true', fontcolor='darkgreen') >> ledreactor
     sys >> Edge( label='ledstatelocalevent', **evattr, decorate='true', fontcolor='darkgreen') >> ledreactor
     sys >> Edge( label='ledinfoevent', **evattr, decorate='true', fontcolor='darkgreen') >> ledreactor
diag
