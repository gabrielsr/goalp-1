
## Requirements

### Git, Java8+ and Maven

Ubunt 16.04:
``` bash
sudo apt install openjdk-9-jdk-headless
sudo apt install maven
```

### Get the Project 
``` bash
git clone https://github.com/lesunb/goalp.git
cd goalp/
```

### Build
``` bash
mvn install
```

## Execute
``` bash
cd scalability-evaluation
export MAVEN_OPTS="-server -Xcomp"
mvn exec:java
```
 
## *Execute with parameters
"-server -Xcomp" makes JVM compile all code and init all needed structures at the beginning. If not done so, the first planning executions will be more slow, afecting the experiments results.   