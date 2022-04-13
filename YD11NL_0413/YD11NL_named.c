#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/file.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <string.h>

int main()
{
	int fd, ret;
	char buf[32];
	char buf2;

	//buf[0]=0;


    ret=mkfifo("YD11NL", 00666);
	if (ret == -1) {
	    perror("Mkfifo error");
	    exit(-1);
	}

	ret=fork();
	if(ret==-1){
		perror("Fork error");
		exit(-1);
	}

	if(ret==0){
		printf("%d: a gyerek PID-je\n",getpid());
		fd=open("YD11NL",O_WRONLY);
		if (fd == -1) {
	    	perror("Open error");
	    	exit(-1);
		}

		strcpy(buf,"Palencsar Eniko");
		printf("%d: Iras a FIFO-ba: %s\n",getpid(), buf);
		write(fd,buf,strlen(buf));
		close(fd);
		exit(0);
	}
	else{
		printf("%d: a szulo PID-je\n",getpid());
		fd=open("YD11NL",O_RDONLY);
		if (fd == -1) {
	    	perror("Open error");
	    	exit(-1);
		}
		printf("%d: Olvasok... \n%d: ",getpid(), getpid());
		while (read(fd, &buf2, 1) > 0) {
		 	printf("%c",buf2);
	   	}
	   	printf("\n%d: Kiolvasva. \n",getpid());
		close(fd);

		unlink("YD11NL");
		exit(0);
	}

	
}