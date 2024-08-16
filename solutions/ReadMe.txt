Note : Suppose we have configured solutionsPath for any Problem (say ProblemID PS_0001) as solutions/PS_0001. Then we must need to follow below folder hierarchy to place solutions for this problem.

1. Create folders for supported platforms for given problem
	i-     LINUX    => Place here solutions that need to be tested on LINUX environment
	ii-    WINDOWS  => Place here solutions that need to be tested on WINDOWS environment
	iii-   COMMON   => Place here solutions that can be tested on any environment
2. Create folders for supported languages (JAVA, PYTHON, C, CPP etc)
3. Create folders with EmployeeID/UserID inside Language folders (Format : <FirstName>_<ID>)
4. Place solutions inside above EmployeeID/UserID folders. Executable name must be started with configured executableNamePrefix in apps-config.json

Eg. 

1. Suppose any Employee (Employee Code : 10001, First Name : John) shared his solution (built on LINUX machine) for ProblemID PS_0001 in PYTHON language , then it can be placed in below folder
		=> AppAnalyzer/solutions/PS_0001/LINUX/PYTHON/John_10001/mainAppSolution
2. Suppose any Employee (Employee Code : 10002, First Name : Jack) shared his solution (built on WINDOWS machine) for ProblemID PS_0001 in JAVA language (platform independent) , then it can be placed in below folder
		=> AppAnalyzer/solutions/PS_0001/COMMON/PYTHON/Jack_10002/mainAppJack.jar