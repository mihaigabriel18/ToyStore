run1:
	java -jar target/ToyStore-1.0-SNAPSHOT-jar-with-dependencies.jar < tests/res/test0.in

run2:
	java -jar target/ToyStore-1.0-SNAPSHOT-jar-with-dependencies.jar < tests/res/test1.in

run3:
	java -jar target/ToyStore-1.0-SNAPSHOT-jar-with-dependencies.jar < tests/res/test2.in

run4:
	java -jar target/ToyStore-1.0-SNAPSHOT-jar-with-dependencies.jar < tests/res/test3.in

run5:
	java -jar target/ToyStore-1.0-SNAPSHOT-jar-with-dependencies.jar < tests/res/test4.in
clean:
	rm commandErrors.err commandResults.out
