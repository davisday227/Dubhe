#include "kernel/types.h"
#include "kernel/stat.h"
#include "user/user.h"
#include "kernel/fs.h"

char*
fmtname(char *path)
{
    // printf("enter path: %s\n", path);
    static char buf[DIRSIZ+1];
    char *p;

    // Find first character after last slash.
    for(p=path+strlen(path); p >= path && *p != '/'; p--)
        ;
    p++;

    // Return blank-padded name.
    if(strlen(p) >= DIRSIZ)
        return p;
    memmove(buf, p, strlen(p));
    memset(buf+strlen(p), ' ', DIRSIZ-strlen(p));
    buf[strlen(p)] = 0;
    // printf("get buf: %s\n", buf);
    return buf;
}

void find(char *path, char *pattern) {
    char buf[512], *p;
    int fd;
    struct dirent de;
    struct stat st;

    if ((fd = open(path, 0)) < 0) {
        fprintf(2, "ls: cannot open %s\n", path);
        return;
    }

    if (fstat(fd, &st) < 0) {
        fprintf(2, "ls: cannot stat %s\n", path);
        close(fd);
        return;
    }

    strcpy(buf, path);
    p = buf + strlen(buf);
    *p++ = '/';
    while (read(fd, &de, sizeof(de)) == sizeof(de)) {
        if (de.inum == 0 || strcmp(de.name, ".") == 0 || strcmp(de.name, "..") == 0)
            continue;

        memmove(p, de.name, DIRSIZ);
        p[DIRSIZ] = 0;
        if (stat(buf, &st) < 0) {
            printf("find: cannot stat %s\n", buf);
            continue;
        }

        switch(st.type) {
        case T_FILE:
            if (strcmp(pattern, fmtname(buf)) == 0) {
                printf("%s\n", buf);
            }
            break;
        case T_DIR:
            // printf("get dir: %s\n", buf);
            find(buf, pattern);
            break;
        }
    }
    close(fd);
}

int
main(int argc, char *argx[])
{
    if(argc < 3)
    {
        printf("find args can not less than 3\n");
        exit(1);
    }

    if(argc > 3)
    {
        printf("find args can not more than 3\n");
        exit(1);
    }

    char *target_path = argx[1];
    char *target_file = argx[2];
    find(target_path, target_file);
    exit(0);
}
