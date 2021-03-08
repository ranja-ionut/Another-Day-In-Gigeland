build:
	javac *.java

run-p1:	Bani.class
	java Bani
	
run-p2:	Gard.class
	java Gard
	
run-p3:	Bomboane.class 
	java Bomboane
	
run-p4:	Sala.class 
	java Sala
	
clean:
	rm -rf *.class