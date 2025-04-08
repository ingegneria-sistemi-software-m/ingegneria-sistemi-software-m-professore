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
with Diagram('gamelifeactorsArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxconwayactors', graph_attr=nodeattr):
          griddisplay=Custom('griddisplay','./qakicons/symActorWithobjSmall.png')
          gridcreator=Custom('gridcreator','./qakicons/symActorWithobjSmall.png')
          gamelifehelper=Custom('gamelifehelper','./qakicons/symActorWithobjSmall.png')
          gamelife=Custom('gamelife','./qakicons/symActorWithobjSmall.png')
          cell=Custom('cell','./qakicons/symActorDynamicWithobj.png')
     gamelifehelper >> Edge( label='startthegame', **eventedgeattr, decorate='true', fontcolor='red') >> sys
     gamelife >> Edge( label='synch', **eventedgeattr, decorate='true', fontcolor='red') >> sys
     sys >> Edge( label='startthegame', **evattr, decorate='true', fontcolor='darkgreen') >> cell
     sys >> Edge( label='curstate', **evattr, decorate='true', fontcolor='darkgreen') >> cell
     sys >> Edge( label='synch', **evattr, decorate='true', fontcolor='darkgreen') >> cell
     sys >> Edge( label='clearCell', **evattr, decorate='true', fontcolor='darkgreen') >> cell
     gamelife >> Edge(color='blue', style='solid',  decorate='true', label='<fromdisplay &nbsp; cellends &nbsp; >',  fontcolor='blue') >> gamelifehelper
     cell >> Edge(color='blue', style='solid',  decorate='true', label='<todisplay &nbsp; >',  fontcolor='blue') >> griddisplay
     gamelife >> Edge(color='blue', style='solid',  decorate='true', label='<coapinfo &nbsp; >',  fontcolor='blue') >> griddisplay
     cell >> Edge(color='blue', style='solid',  decorate='true', label='<cellready &nbsp; cellends &nbsp; >',  fontcolor='blue') >> gamelife
     gamelife >> Edge(color='blue', style='solid',  decorate='true', label='<allcellready &nbsp; gamesuspend &nbsp; >',  fontcolor='blue') >> gamelife
     gridcreator >> Edge(color='blue', style='solid',  decorate='true', label='<gameready &nbsp; >',  fontcolor='blue') >> griddisplay
     cell >> Edge(color='blue', style='solid',  decorate='true', label='<cellcreated &nbsp; >',  fontcolor='blue') >> gridcreator
     gamelifehelper >> Edge(color='blue', style='solid',  decorate='true', label='<gamestopped &nbsp; gameended &nbsp; >',  fontcolor='blue') >> gamelife
     gridcreator >> Edge(color='blue', style='solid',  decorate='true', label='<gameready &nbsp; gameended &nbsp; >',  fontcolor='blue') >> gamelife
diag
