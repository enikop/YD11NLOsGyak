#include <stdio.h>
#include <stdlib.h> //exit()
#include <unistd.h> //pause(), getpid()
#include <sys/ipc.h> //ftok()
#include <sys/msg.h>
#include <signal.h>
#include <string.h>

/*15. Irjon C nyelvu programokat, ami
 letrehoz egy uzenetsort
 SIGHUP signal hatasara beletesz egy uzenetet ebbe az uzenetsorba
 SIGTERM hatasara szunjon meg az uzenetsor eroforras es lepjen is ki a program
 a masik program pedig:
 SIGHUP signal hatasara kiolvas ebbol egy uzenetet, es kiirja a kepernyore*/


struct msg_buffer {
    long mtype;
    char mtext[512];
} my_message;

void send_msg(int sig);
void delete_msgq(int sig);

int main() {
    key_t key;
    int msg_id;

    printf("PID: %d\n", getpid());

    key = ftok("YD11NL_1.c", 22);
    msg_id = msgget(key, 0666 | IPC_CREAT);
    if(msg_id == -1){
    	perror("Msgget error");
    	exit(-1);
    }
    printf("Az uzenetsor letrejott\n");

    signal(SIGHUP, send_msg);
    signal(SIGTERM, delete_msgq);

    while (1) {
        printf("Uzenet kuldese: SIGHUP, uzenetsor torlese: SIGTERM, varakozas szignalra...\n");
        pause();
    }

    exit(0);
}

void send_msg(int sig) {
	key_t key = ftok("YD11NL_1.c", 22);
	int ret;
    int msg_id = msgget(key, 0666);
    my_message.mtype = 1;
    strcpy(my_message.mtext, "A macska aranyos.\n");
    ret = msgsnd(msg_id, &my_message, strlen(my_message.mtext) + 1, IPC_NOWAIT);
    if(ret == -1){
    	printf("Kuldes sikertelen. \n");
    } else{
    	printf("Uzenet elkuldve: %s", my_message.mtext);
    }
}

void delete_msgq(int sig){
	key_t key = ftok("YD11NL_1.c", 22);
    int msg_id = msgget(key, 0666);
    msgctl(msg_id, IPC_RMID, NULL);
    printf("Az uzenetsor megszunt, kilepes...\n");
    exit(0);
}