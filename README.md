# Automatic Gatling

Automate the website load testing with configurable Website main URL, users, context URLs and response time less than 5secs.

Running the Recorder: Simply launch the Recorder class in your IDE Or using Command Prompt
**“mvn gatling:recorder”**

Simulations will be generated in the src/test/scala directory.
Running Gatling: Simply launch the Engine class in your IDE Or using Command Prompt  

**“mvn gatling:execute”**

**“mvn gatling:test -Dgatling.simulationClass=packageName.scalaClassName.”** 
(if you want to run particular Scala file use command,)

Results will be generated under target\gatling folder 

Just do : 
>**“mvn gatling:test -Dgatling.simulationClass=demogatling.BasicSimulation”**
