
getAllLogs(LOGS) :-
	findall( V, logitem( V ), LOGS).

showAllLogs :-
	getAllLogs(LOGS),
	%stdout <- println( LOGS ),
	showList(LOGS).   
	
showList([]).
showList([C|R]):-
	stdout <- println( C ),
	showList(R).

	
getAllPrologLogs(LOGS) :-
	findall( logs( S,C,V ), logitem( S,C,V ), LOGS).
showAllPrologLogs :-
	getAllPrologLogs(LOGS),
    %stdout <- println( LOGS ),
	showList(LOGS).   
