#ifndef	_EXAMPLE_H
#define	_EXAMPLE_H

#include <apue.h>
#include <pwd.h>
#include <sys/wait.h>

// Unix环境高级编程 P257 图 10-2 实例
namespace example10_2 {
    static void sig_user(int); /* one handler for both signals */

    void start() 
    {
        if (signal(SIGUSR1, sig_user) == SIG_ERR) 
        {
            err_sys("can't catch SIGUSR1");
        }
        if (signal(SIGUSR2, sig_user) == SIG_ERR)
        {
            err_sys("can't catch SIGUSR2");
        }
        if (signal(SIGINT, sig_user) == SIG_ERR)
        {
            err_sys("can't catch SIGINT");
        }
        if (signal(SIGQUIT, sig_user) == SIG_ERR)
        {
            err_sys("can't catch SIGQUIT");
        }

        for (;;)
            pause();
    }


    static void sig_user(int signo) /* argument is signal number */
    {
        if (signo == SIGUSR1)
        {
            std::cout << "received SIGUSR1" << std::endl;
        }
        else if (signo == SIGUSR2)
        {
            std::cout << "received SIGUSR2" << std::endl;
        }
        else if (signo == SIGINT) 
        {
            std::cout << "received SIGINT" << std::endl;
        }
        else if (signo == SIGQUIT) 
        {
            std::cout << "received SIGQUIT" << std::endl;
        }
        else 
        {
            err_dump("received signal %d\n", signo);
        }
    }
}

// P263 图 10-5 实例
// Mac OS X 上调用，并没有出现书上说明的情况
// 只打印一次 'in signal handler' 后，就一直阻塞
namespace example10_5 {
    static void my_alarm(int signo)
    {
        struct passwd *rootptr;
        printf("in signal handler\n");
        if ((rootptr = getpwnam("asiafrank")) == nullptr)
        {
            err_sys("getpwnam(asiafrank) error");
        }
        alarm(1);
    }

    void start() 
    {
        struct passwd *ptr;

        signal(SIGALRM, my_alarm);
        alarm(1);
        for (;;)
        {
            if ((ptr = getpwnam("asiafrank")) == nullptr)
            {
                err_sys("getpwnam error");
            }
            if (strcmp(ptr->pw_name, "asiafrank") != 0)
            {
                printf("return value corrupted!, pw_name = %s\n", ptr->pw_name);
            }
        }
    }
}

// P265 图 10-6 实例中 SIGCLD 在 Mac OS X 中没有定义，所以该例子忽略
namespace example10_6 {
}

#endif	/* _EXAMPLE_H */