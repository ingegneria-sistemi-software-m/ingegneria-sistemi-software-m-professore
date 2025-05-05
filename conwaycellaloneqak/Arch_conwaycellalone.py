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
with Diagram('conwaycellaloneArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxcellalone', graph_attr=nodeattr):
          cell0000=Custom('cell0000','./qakicons/symActorWithobjSmall.png')
          perceiver=Custom('perceiver','./qakicons/symActorWithobjSmall.png')
     with Cluster('ctxmaster', graph_attr=nodeattr):
          gamemaster=Custom('gamemaster(ext)','./qakicons/externalQActor.png')
     sys >> Edge( label='allcellready', **evattr, decorate='true', fontcolor='darkgreen') >> cell0000
     sys >> Edge( label='exitCmd', **evattr, decorate='true', fontcolor='darkgreen') >> cell0000
     sys >> Edge( label='startthegame', **evattr, decorate='true', fontcolor='darkgreen') >> cell0000
     sys >> Edge( label='clearCell', **evattr, decorate='true', fontcolor='darkgreen') >> cell0000
     sys >> Edge( label='stopthecell', **evattr, decorate='true', fontcolor='darkgreen') >> cell0000
     sys >> Edge( label='curstate', **evattr, decorate='true', fontcolor='darkgreen') >> cell0000
     sys >> Edge( label='synch', **evattr, decorate='true', fontcolor='darkgreen') >> cell0000
     sys >> Edge( label='cellLifeName', **evattr, decorate='true', fontcolor='darkgreen') >> perceiver
     sys >> Edge( label='kernel_rawmsg', **evattr, decorate='true', fontcolor='darkgreen') >> perceiver
     cell0000 >> Edge(color='magenta', style='solid', decorate='true', label='<addtogame<font color="darkgreen"> addedtogame</font> &nbsp; >',  fontcolor='magenta') >> gamemaster
     cell0000 >> Edge(color='blue', style='solid',  decorate='true', label='<cellcreated &nbsp; cellready &nbsp; >',  fontcolor='blue') >> gamemaster
diag
