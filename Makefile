.PHONY: all

all:
	javac -d . *.java
	java -Dprism.order=sw lab3.Main
