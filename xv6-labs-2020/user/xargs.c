#include "kernel/types.h"
#include "user/user.h"

int main(int argc, char *argv[]) {
    char c;
    char buf[512];
    uint bi = 0;
    char *xv[128];
    int count = 0;
    for (int i = argc - 2; i < argc; i++) {
        xv[count] = argv[i];
        count++;
    }
    while (read(0, &c, 1) == 1) {
        if(c != ' ' && c != '\n')
        {
            // 读取到的字符不为空格和\n，则读入到buf中
            buf[bi]=c;
            bi ++;
        } else
        {
            // 读取到空格或者\n，则提取刚刚读取到的字符串
            count++;
            char *tmp = malloc(bi + 2);
            memcpy(tmp, buf, bi + 1);
            tmp[bi + 1] = '\0'; // 结束标志
            xv[count-1] = tmp;
            bi = 0;     // 计数器复位
            if(c == '\n') {
                // 如果是\n，则需要执行命令
                int pid = fork();
                if(pid == 0) {
                    exec(argv[1], xv);
                    exit(0);
                }else 
                {
                    wait(&pid);
                }
                bi = 0; // 复位
                count = 0; // 复位
            }
        }
    }
    exit(0);
}
