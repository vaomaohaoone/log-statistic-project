## Microservice application for calculating statistics on log files  
#### Architecture:  
![final-project](static/final-project.jpg)
#### Example of input log format:  
{"timestamp":"2020-11-20 00:05:00","host":"localhost1","process":"root1","level":1}  
{"timestamp":"2020-11-20 00:07:00","host":"localhost1","process":"root1","level":1}  
{"timestamp":"2020-11-20 00:16:00","host":"localhost2","process":"root2","level":3}  
{"timestamp":"2020-11-20 00:23:00","host":"localhost2","process":"root3","level":3}  
{"timestamp":"2020-11-20 00:26:00","host":"localhost3","process":"root3","level":3}  
{"timestamp":"2020-11-20 00:33:00","host":"localhost4","process":"root3","level":4}  
{"timestamp":"2020-11-20 00:36:00","host":"localhost5","process":"root3","level":5}  
{"timestamp":"2020-11-20 00:45:00","host":"localhost5","process":"root4","level":6}  
{"timestamp":"2020-11-20 00:59:00","host":"localhost6","process":"root4","level":7}  
