#include <stdlib.h>
#include <stdio.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <string.h>

int main(){
	char command[20];
	int status;
	char out;

	printf("Adja meg a parancsot: ");
	scanf("%s", command);
	do{
		status=system(command);
		if(WIFEXITED(status)) printf("Normalis befejezodes, visszaadott ertek: %d\n", WEXITSTATUS(status));
		printf("Adja meg a parancsot (\\ a kilepeshez): ");
		scanf("%s", command);
	} while(command[0] != '\\');
	exit(0);
}