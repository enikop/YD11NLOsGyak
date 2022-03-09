#include <stdlib.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/wait.h>

int main(){
	int status, status1;

	//letezo:
	status=system("ls");
	if(status < 0) perror("system() error");
	if(WIFEXITED(status)) printf("Normalis befejezodes, visszaadott ertek: %d\n", WEXITSTATUS(status));
	else printf("Nem normalis befejezodes\n");
	
	//nem letezo
	status=system("kutya");
	if(status < 0) perror("system() error");
	if(WIFEXITED(status)) printf("Normalis befejezodes, visszaadott ertek: %d\n ", WEXITSTATUS(status));
	else printf("Nem normalis befejezodes\n");
	exit(0);

}