all: run

Synthesize.class: Synthesize.java
	javac -cp ".:*:lib/*" $^

run: Synthesize.class
	java -cp ".:*:lib/*" Synthesize
