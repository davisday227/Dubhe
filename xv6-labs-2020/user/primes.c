#include "kernel/types.h"
#include "kernel/stat.h"
#include "user/user.h"

void print_prime(int readPipe)
{
    int buf;
    int pp[2];
    if (pipe(pp) != 0) {
        printf("failed to create pipe \n");
        exit(1);
    }

    // 把readPipe中的元素读入pp中，并且过滤掉第一个，以及第一个的倍数
    int first = 2;
    int count = 0;
    while(read(readPipe, &buf, 1) == 1) // 读取readPipe
    {
        if(count == 0) 
        {
            first = buf;
            printf("prime %d\n", buf);
            count ++;
        }
        else 
        {
            if(buf % first != 0)
            {
                write(pp[1], &buf, 1);
                count ++;
            }
        }
    }

    close(pp[1]);
    close(readPipe);

    if(count <= 1) { // 没有要处理的元素，结束进程
        exit(0);
    }

    int pid = fork();
    if(pid == -1) 
    {
        printf("fail to create child process\n");
        exit(1);
    } 
    else if (pid == 0) // 子进程继续递归处理
    {
        print_prime(pp[0]);
    }
    else // 父进程，等待子进程结束
    {
        wait(0);
    }
}

int
main(int argc, char *argx[])
{
    // 构建2-35数组
    int pp[2];
    if(pipe(pp) != 0) 
    {
        printf("fail to create pipe\n");
        exit(1);
    }

    for(int i = 2; i <= 35; i ++) {
        write(pp[1], &i, 1);
    }

    close(pp[1]);
    print_prime(pp[0]);
    exit(0);
}

