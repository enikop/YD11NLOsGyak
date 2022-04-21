#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/msg.h>
#include <sys/ipc.h>
#include <signal.h>

struct msg_buffer {
    long mtype;
    char mtext[512];
} my_message;

void receive_msg(int sig);

int main() {
    printf("PID: %d\n", getpid());
    signal(SIGHUP, receive_msg);

    while (1) {
        printf("Uzenet fogadasa: SIGHUP, varakozas szignalra...\n");
        pause();
    }
    
    exit(0);
}

void receive_msg(int sig) {
    key_t key = ftok("YD11NL_1.c", 22);
    int msg_id = msgget(key, 0666);
    if(msg_id == -1){
        printf("Az uzenetsor nem letezik.\n");
        return;
    }
	int ret = msgrcv(msg_id, &my_message, 512, 0, IPC_NOWAIT);
    if(ret == -1){
        printf("Nincs uj uzenet, terjen vissza kesobb!\n");
    } else{
        printf("Fogadva %d byte: %s", ret, my_message.mtext);
    }
}

