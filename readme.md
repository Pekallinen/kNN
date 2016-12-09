#Install
1. Install maven
2. Install JDK 1.8
3. Add the jdk and maven/bin to PATH if on Windows


#How to run:
1. Run "mvn clean package" in the root
2. Run "java -jar kNN-0.0.1-SNAPSHOT.jar iris.data iris_unclassified.data 3" or "java -jar kNN-0.0.1-SNAPSHOT.jar car.data car_unclassified.data 3" inside target created by running "mvn clean package"
