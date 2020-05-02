*************READ ME*****************************************

STEPS TO RUN CODE :

- import project as Maven.
- Before run, please do Maven update.
- Open Simulator.java file and run as Java Application.

*************************************************************

NOTE :

- To add new sensor events -> add json including its data
- To add driving modes -> add List<String> modeList = Arrays.asList("NORMAL", "SPORT", "SAFE");

**************************************************************

SAMPLE TEST

Scenario: Success 

>autonomousDriving NORMAL
Welcome. Driving mode is NORMAL
20
Enter Event: 50
50
Enter Event: 1
40
Enter Event: 2
50
Enter Event:



Scenario: Ignore

>autonomousDriving SPORT
Welcome. Driving mode is SPORT
20
>Enter Event: 7
50
>Enter Event: 7
50
>Enter Event:

Scenario: Invalid

> autonomousDriving SAFE
Welcome. Driving mode is SAFE
20
>Enter Event: 60
55
>Enter Event: 200
Invalid
>Enter Event:
