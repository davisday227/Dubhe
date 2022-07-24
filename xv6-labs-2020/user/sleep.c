#include "kernel/types.h"
#include "kernel/stat.h"
#include "user/user.h"

int
main(int argc, char *argv[])
{
  // 判定参数数量，因为入参是sleep xxx这样，所以argc实际上是2，必须要满足2才可以正常执行
  if(argc < 2){
    fprintf(2, "Usage: sleep times not found...\n");
    exit(1);
  }

  if(argc > 2){
    fprintf(2, "Usage: sleep times params more than 2...\n");
    exit(1);
  }

  // 因为参数都是字符串，所以要使用atoi来转换成int
  int sleep_second = atoi(argv[1]);
  // sleep来自于user.h，会调用到system call，还不知道这个调用是怎么发生的
  sleep(sleep_second);

  exit(0);
}
