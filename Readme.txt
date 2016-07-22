			       _       __  __                _       ______           
			      | |     / _|/ _|              ( )     |___  /           
			      | | ___| |_| |_ _ __ ___ _   _|/ ___     / / ___   ___  
			  _   | |/ _ \  _|  _| '__/ _ \ | | | / __|   / / / _ \ / _ \ 
			 | |__| |  __/ | | | | | |  __/ |_| | \__ \  / /_| (_) | (_) |
			  \____/ \___|_| |_| |_|  \___|\__, | |___/ /_____\___/ \___/ 
			                                __/ |                         
			                               |___/                          
			                               
			* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *								                               
			  _____                _                 _        _   
			 |  __ \              | |               | |      | |  
			 | |__) |___  __ _  __| |_ __ ___   ___ | |___  _| |_ 
			 |  _  // _ \/ _` |/ _` | '_ ` _ \ / _ \| __\ \/ / __|
			 | | \ \  __/ (_| | (_| | | | | | |  __/| |_ >  <| |_ 
			 |_|  \_\___|\__,_|\__,_|_| |_| |_|\___(_)__/_/\_\\__|
                  
                                               
 To start the simulation, enter "java Zoo (configuration file directory)" in the command prompt or terminal, or to start the simulation with saved state, enter "java Zoo" in the command prompt or terminal

Configuration:

1. In the configuration file, an animal can be created with no attribute, 1 attribute, 2 attributes or 3 attributes, the default value for gender is 'm', the default value for age is 0 and the default value for health is 5. Any errorneous value will be replaced by the default value of that field. For example: "lion:hahaha,-2" will create a male lion that's 0 month old and has 5 health.

2.  - A saving feature is impemented which allows user to save the current state of the simulation.
	
	- The current state is going to be saved automatically after each month.
		
	- Users can enable or disable the saving feature in the configuration file. 
		  To enable the feature, type "save:true" before "zoo:".
		  To disable the feature, type "save:false" before "zoo:"
		  
	- The default value is true when "save:" is not detected.
		
	- The state will be saved in a text file named "context.txt". 
		  The format of the file will be the same as the configuration file.
	
3.  - Users can also alter the time between each month.
	
	- To alter the time value, type "time:(time in ms)" before "zoo:".
		
	- The default value is 2000 and the input value should not be smaller than 500.
	
3. The simulation will not start if an error is found in the configuration file and an exception will be thrown. Users can use the information provided by the error message to debug.
	
4. Be aware that in the configuration file, only one zoo can be created. If more than one zoo is detected, an error will be thrown.