/*2a. Írjon egy C nyelvű programot, melyben
•	egyik processz létrehozza a szemafort (egyetlen elemi szemafort; inicializálja 1-re, vagy x-re, ha még nem létezik),
•	másik processz használja a szemafort, belépési szakasz (down), a kritikus szakaszban alszik 2-3 sec-et, m pid-et kiír, kilépési szakasz (up), ezt ismételve 2x-3x (és a hallgató egyszerre indítson el 2-3 ilyen processzt),
•	harmadik processzben, ha létezik a szemafor, akkor megszünteti”.
*/
#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/sem.h>
#define SEMKEY 222221L 

int main(){
	int semid, nsems, rtn;
	union semun {
        int              val;    /* Value for SETVAL */
        struct semid_ds *buf;    /* Buffer for IPC_STAT, IPC_SET */
        unsigned short  *array;  /* Array for GETALL, SETALL */
        struct seminfo  *__buf;  /* Buffer for IPC_INFO
                                           (Linux-specific) */
    } arg;

   	nsems=1;
	semid = semget (SEMKEY, nsems, 00666 | IPC_CREAT);
	if (semid < 0 ) {
		perror("Semget hiba\n");
		exit(0);
	} else printf("Semid: %d\n",semid);
	
	arg.val=1;
	rtn = semctl(semid, 0, SETVAL, arg);
	printf("Set  visszateres: %d , szemafor erteke: %d\n", rtn, arg.val);
}
