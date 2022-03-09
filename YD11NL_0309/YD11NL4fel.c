#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/types.h>

int main(){
	pid_t pid; 

	pid = fork();

	if (pid < 0) {
		perror("fork error");
	}
	else if (pid == 0) {
		if (execlp("date", "date", (char*)NULL)<0) {
			perror("execlp error");
		}
	} else{
		if(waitpid(pid, NULL, 0) < 0)
            printf("wait error");
		printf("A szulo processz megvarta a gyermeket.\n");
	}
	
	exit(0);
}
