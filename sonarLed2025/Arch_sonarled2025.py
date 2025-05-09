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
with Diagram('sonarled2025Arch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxsonarled2025', graph_attr=nodeattr):
          mind=Custom('mind','./qakicons/symActorWithobjSmall.png')
          sonardevice=Custom('sonardevice','./qakicons/symActorWithobjSmall.png')
          sonarsimul=Custom('sonarsimul','./qakicons/symActorWithobjSmall.png')
     sonarsimul >> Edge( label='sonardata', **eventedgeattr, decorate='true', fontcolor='red') >> mind
     mind >> Edge( label='unibologprolog', **eventedgeattr, decorate='true', fontcolor='red') >> sys
diag
