.PHONY: all

all:
	javac -d . *.java
	java -Xmx256m -Xss256m lab3.Main
