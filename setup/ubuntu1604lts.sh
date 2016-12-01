sudo apt install openjdk-9-jdk-headless
sudo apt install maven
git clone https://github.com/lesunb/goalp.git
cd goalp/
mvn install

cd scalability-evaluation

export MAVEN_OPTS="-server -Xcomp"
mvn exec:java
