#include "kernel/types.h"
#include "kernel/stat.h"
#include "user/user.h"

int
main(int argc, char *argx[])
{
    char buf[4];
    char *ping = "ping";
    char *pong = "pong";
    int p[2];
    pipe(p);
    int pid = fork();
    if (pid == -1) {
        fprintf(2, "failed to create child process\n");
        exit(1);
    } else if(pid == 0) { // 父进程 打印pong
        read(p[0], buf, 4);
        printf("%d: received %s\n", getpid(), buf);
        write(p[1], pong, 4);
        exit(0);
    } else { // 子进程 答应ping
        write(p[1], ping, 4);
        wait(0);
        read(p[0], buf, 4);
        printf("%d: received %s\n", getpid(), buf);
        close(p[0]);
        close(p[1]);
        exit(0);
    }

    exit(0);
}