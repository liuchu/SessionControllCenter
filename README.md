LTE-B Session Control Center
===========================

Environment
---
Java 1.8<br>
Maven 3.5<br>

Deploy
---
cd {source code root}<br>
mvn package<br>
java -jar ./target/SessionControllCenter-0.0.1-SNAPSHOT.jar<br>


Statement
---
Control LTE-B sessions starting and stopping.<br>
Start with home page: http://localhost:8083/a/start once app is started up.<br>
<br>
server default port = 8083<br>

Core ideas
---
1. Have simple web Front-end pages to "Start/Stop"<br>
2. Back-end handle 3 kinds of requests from Front-end:<br>
    start n sessions.<br>
    stop single sessions.<br>
    stop all active sessions.<br>
   Then send related "Start/Stop" requests to "LTE-B virtual server"<br>
3. One thread handle one session, for example, we can "Start" 7 session at same time, then 7 threads will be created to start 7 sessions<br>
4. All "Start/Stop" history would be logged.<br>
5. Validate XML format before send POST requests to  "LTE-B virtual server"
6. Loaded 3 collections Map<Integer,LTESession> startedItems, List<LTESession> startingItems, List<LTESession> failedItems in servletContext to save sessions status<br>

UML graph
---
See LTE_session_control_center_uml.png at {source code root}<br>

v1.0
---
1. Start multiple sessions.<br>
2. Stop single sessions.<br>
3. Stop all sessions<br>
