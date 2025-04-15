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
with Diagram('sonarArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxsonar', graph_attr=nodeattr):
          sonar=Custom('sonar','./qakicons/symActorWithobjSmall.png')
          sonarsimulator=Custom('sonarsimulator(coded)','./qakicons/codedQActor.png')
          datalogger=Custom('datalogger(coded)','./qakicons/codedQActor.png')
          datacleaner=Custom('datacleaner(coded)','./qakicons/codedQActor.png')
          distancefilter=Custom('distancefilter(coded)','./qakicons/codedQActor.png')
     with Cluster('ctxradargui', graph_attr=nodeattr):
          radargui=Custom('radargui(ext)','./qakicons/externalQActor.png')
     sys >> Edge( label='obstacle', **evattr, decorate='true', fontcolor='darkgreen') >> sonar
     sys >> Edge( label='sonarRobot', **evattr, decorate='true', fontcolor='darkgreen') >> sonar
     sonar >> Edge( label='sonardata', **eventedgeattr, decorate='true', fontcolor='red') >> sys
     sonar >> Edge(color='blue', style='solid',  decorate='true', label='<simulatorstart &nbsp; >',  fontcolor='blue') >> sonarsimulator
diag
