#include <stdlib.h>
#include <stdio.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <unistd.h>

void pr_exit(int status);

int main(){
	pid_t pid;
	int status;

	if((pid = fork())<0)
		perror("fork error");
	else if(pid == 0){
		exit(10);
	}
	if(wait(&status) != pid)
		perror("wait error");
	pr_exit(status);

	if((pid = fork())<0)
		perror("fork error");
	else if(pid == 0){
		abort();
	}
	if(wait(&status) != pid)
		perror("wait error");
	pr_exit(status);

	if((pid = fork())<0)
		perror("fork error");
	else if(pid == 0){
		status /= 0;
	}
	if(wait(&status) != pid)
		perror("wait error");
	pr_exit(status);



	exit(0);
}

void pr_exit(int status){
	if(WIFEXITED(status))
		printf("Normalis befejezodes, visszaadott ertek = %d\n", WEXITSTATUS(status));
	else
		if(WIFSIGNALED(status))
			printf("Abnormalis befejezes, szignal sorszam = %d\n", WTERMSIG(status));
}